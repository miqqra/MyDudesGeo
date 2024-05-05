package mydudesgeo.service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.DateUtils;
import mydudesgeo.data.Visibility;
import mydudesgeo.dataservice.CityToLocationDataService;
import mydudesgeo.dataservice.PartyDataService;
import mydudesgeo.dataservice.UserDataService;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyMapper;
import mydudesgeo.model.CityToLocationModel;
import mydudesgeo.model.UserModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyIntegrationService {

    @Value("mydudes.config.dobro.nickname")
    private String dobroNickname;

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

    public PartyDto migratePartyFromVk() {
        return null;
    }

    public PartyDto createPartyInVk() {
        return null;
    }

}
