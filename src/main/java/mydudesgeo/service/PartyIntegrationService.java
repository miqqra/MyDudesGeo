package mydudesgeo.service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.DateUtils;
import mydudesgeo.common.Location;
import mydudesgeo.data.Visibility;
import mydudesgeo.dataservice.CityToLocationDataService;
import mydudesgeo.dataservice.PartyDataService;
import mydudesgeo.dataservice.UserDataService;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyMapper;
import mydudesgeo.model.CityToLocationModel;
import mydudesgeo.model.PartyModel;
import mydudesgeo.model.UserModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyIntegrationService {

    private final static String PARTY_CREATED = "Мероприятие создано в MyDudes. Пройдите в приложение и дополните нужную вам информацию";
    private final static String PARTY_INVITE = "Мероприятие \"%s\" перенесено из MyDudes. Присоединяйся: %s";
    private final static String PARTY_NAME = "Название мероприятия: %s";
    private final static String PARTY_DESCRIPTION = "Описание мероприятия: %s";
    private final static String PARTY_INFO = "Мероприятие \"%s\" перенесено из MyDudes. Кратко о мите:";


    @Value("mydudes.config.dobro.nickname")
    private String dobroNickname;

    private final TgBotService tgBotService; //todo two different services?

    private final PartyDataService partyDataService;
    private final UserDataService userDataService;
    private final CityToLocationDataService cityToLocationDataService;

    private final PartyMapper partyMapper;


    public PartyDto createPartyFromDobroRu(String url) {
        validateIfPartyExists(url);

        try {
            Document doc = Jsoup.connect(url).get();

            String city = findCity(doc);
            String partyName = findPartyName(doc);
            String partyDescription = findPartyDescription(doc);
            ZonedDateTime[] dates = findStartTimeAndEndTime(doc);
            ZonedDateTime startTime = dates[0];
            ZonedDateTime endTime = dates[1];

            UserModel creator = userDataService.getInfo(dobroNickname);
            CityToLocationModel cityToLocationModel = cityToLocationDataService.findByCity(city);

            var party = partyMapper.toModel(partyName, partyDescription, creator, Visibility.ALL, startTime,
                    endTime, cityToLocationModel.getLatitude(), cityToLocationModel.getLongitude(), url);

            return Optional.of(party)
                    .map(partyDataService::createParty)
                    .map(partyMapper::toDto)
                    .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND,
                            "Не удалось мигрировать мероприятие"));
        } catch (IOException e) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Не удалось мигрировать мероприятие");
        }
    }

    private void validateIfPartyExists(String link) {
        if (partyDataService.existsByLink(link)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Мероприятие уже было мигрировано");
        }
    }

    private String findPartyName(Document document) {
        return document.select("title").getFirst().text();
    }

    private String findPartyDescription(Document document) {
        return document.getElementsByAttributeValue("name", "description")
                .getFirst().attribute("content").getValue();
    }

    private ZonedDateTime[] findStartTimeAndEndTime(Document document) {
        return Optional.of(document)
                .map(v -> v.getElementsByAttributeValueMatching("class", "CardTypes_card-time__title.*")
                        .getFirst().text())
                .map(DateUtils::parseTimestampFromRussian)
                .orElse(new ZonedDateTime[0]);
    }

    private String findCity(Document document) {
        return document.getElementsByAttributeValueMatching("class", "CardTypes_card-location__title.*")
                .getFirst().text();
    }

    public void migratePartyFromTelegram(Long chatId) {
        List<String> telegramNicknames = tgBotService.getChatMembers(chatId);
        List<UserModel> myDudesUsers = userDataService.getUsersFromTelegram(telegramNicknames);

        UserModel creator = Optional.of(UserCredentialsService.getCurrentUser())
                .map(userDataService::getInfo)
                .orElse(null);

        String name = tgBotService.getChatName(chatId);
        String description = tgBotService.getChatDescription(chatId);
        Location location = tgBotService.getChatLocation(chatId);

        PartyModel party = new PartyModel()
                .setCreator(creator)
                .setDescription(description)
                .setName(name)
                .setLocation(location)
                .setChatIdTelegram(chatId)
                .setParticipants(myDudesUsers)
                .setVisibility(Visibility.ALL);

        partyDataService.createParty(party);
        tgBotService.sendMessageToChat(chatId, PARTY_CREATED);
    }

    public void createPartyInTelegram(Long partyId, Long chatId) {
        PartyModel partyModel = partyDataService.getParty(partyId);

        partyModel.getParticipants()
                .stream()
                .map(UserModel::getTelegramChatId)
                .filter(Objects::nonNull)
                .forEach(userTgId -> tgBotService.sendInviteToChat(chatId, userTgId, PARTY_INVITE, partyModel.getName()));

        tgBotService.sendMessageToChat(chatId, PARTY_INFO.formatted(partyModel.getId()));
        tgBotService.sendMessageToChat(chatId, PARTY_NAME.formatted(partyModel.getName()));
        tgBotService.sendMessageToChat(chatId, PARTY_DESCRIPTION.formatted(partyModel.getDescription()));
        tgBotService.sendLocationToChat(chatId, partyModel.getLocation());
    }

    public void addUserChatId(String nickname, Long chatId) {
        Optional.of(chatId)
                .ifPresent(v -> userDataService.setChatId(nickname, v));
    }

}
