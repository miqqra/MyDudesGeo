package mydudesgeo.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.common.TgNotifies;
import mydudesgeo.data.Visibility;
import mydudesgeo.dataservice.FriendsDataService;
import mydudesgeo.dataservice.PartyCategoryDataService;
import mydudesgeo.dataservice.PartyDataService;
import mydudesgeo.dataservice.UserDataService;
import mydudesgeo.dto.party.CreatePartyDto;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyShortInfoDto;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.dto.party.UpdateVisibilityDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyMapper;
import mydudesgeo.model.PartyCategoryModel;
import mydudesgeo.model.PartyModel;
import mydudesgeo.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyDataService dataService;
    private final FriendsDataService friendsDataService;
    private final UserDataService userDataService;
    private final PartyCategoryDataService partyCategoryDataService;
    private final TgNotifyService tgNotifyService;

    private final PartyMapper mapper;

    public List<PartyShortInfoDto> getPartiesAround(Double radius, Location location) {
        String authUser = UserCredentialsService.getCurrentUser();

        return Optional.of(radius)
                .map(r -> dataService.getPartiesAround(location, r))
                .stream()
                .flatMap(Collection::stream)
                .filter(v -> friendsDataService.filterUsers(v, authUser))
                .map(mapper::toShortInfoDto)
                .toList();
    }

    public PartyDto getParty(Long id) {
        String authUser = UserCredentialsService.getCurrentUser();

        return Optional.ofNullable(id)
                .map(dataService::getParty)
                .filter(v -> friendsDataService.filterUsers(v, authUser))
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));
    }

    public List<PartyDto> getUserParties(String user) {
        String authUser = UserCredentialsService.getCurrentUser();

        return Optional.ofNullable(user)
                .map(dataService::getUserParties)
                .stream()
                .flatMap(Collection::stream)
                .filter(v -> friendsDataService.filterUsers(v, authUser))
                .map(mapper::toDto)
                .toList();
    }

    public PartyDto joinParty(Long id) {
        UserModel currentUser = Optional.of(UserCredentialsService.getCurrentUser())
                .map(userDataService::getInfo)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        PartyModel partyModel = Optional.of(id)
                .map(dataService::getParty)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));

        if (partyModel.getParticipants().contains(currentUser)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Вы уже являетесь учатсником мероприятия");
        }

        return Optional.of(partyModel)
                .map(v -> {
                    v.getParticipants().add(currentUser);
                    return v;
                })
                .map(dataService::createParty)
                .map(v -> {
                    tgNotifyService.notify(
                            TgNotifies.NEW_PARTICIPANT.getMessage().formatted(v.getName(), currentUser.getNickname()), v);
                    return v;
                })
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));
    }

    public PartyDto leaveParty(Long id) {
        UserModel currentUser = Optional.of(UserCredentialsService.getCurrentUser())
                .map(userDataService::getInfo)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        PartyModel partyModel = Optional.of(id)
                .map(dataService::getParty)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));

        if (partyModel.getCreator().getNickname().equals(currentUser.getNickname())) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Вы являетесь организатором этого мероприятия и не можете покинуть его");
        }

        if (!partyModel.getParticipants().contains(currentUser)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Вы не являетесь участником мероприятия");
        }

        return Optional.of(partyModel)
                .map(v -> {
                    v.getParticipants().remove(currentUser);
                    return v;
                })
                .map(dataService::createParty)
                .map(v -> {
                    tgNotifyService.notify(
                            TgNotifies.PARTICIPANT_LEAVED.getMessage().formatted(currentUser.getNickname(), v.getName()), v);
                    return v;
                })
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));
    }

    public PartyDto kickUserFromParty(String username, Long id) {
        UserModel currentUser = Optional.of(UserCredentialsService.getCurrentUser())
                .map(userDataService::getInfo)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        UserModel userToBeKicked = Optional.of(username)
                .map(userDataService::getInfo)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        PartyModel partyModel = Optional.of(id)
                .map(dataService::getParty)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));

        if (!partyModel.getCreator().getNickname().equals(currentUser.getNickname())) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Вы не являетесь организатором этого мероприятия и не можете удалять участников");
        }

        if (!partyModel.getParticipants().contains(userToBeKicked)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Пользователь не является участником мероприятия");
        }

        return Optional.of(partyModel)
                .map(v -> {
                    v.getParticipants().remove(userToBeKicked);
                    return v;
                })
                .map(dataService::createParty)
                .map(v -> {
                    tgNotifyService.notify(
                            TgNotifies.PARTICIPANT_KICKED.getMessage().formatted(userToBeKicked.getNickname(), v.getName()), v);
                    return v;
                })
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));
    }

    public List<PartyShortInfoDto> findParties(String search) {
        //todo add filter

        return Optional.of(search)
                .map(dataService::searchParties)
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toShortInfoDto)
                .toList();
    }

    public PartyDto createParty(CreatePartyDto dto) {
        UserModel creator = Optional.of(UserCredentialsService.getCurrentUser())
                .map(userDataService::getInfo)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        PartyCategoryModel partyCategory = Optional.of(dto)
                .map(CreatePartyDto::getCategory)
                .map(partyCategoryDataService::getCategoryByName)
                .orElse(null);

        //todo checkVisibility
        return Optional.of(dto)
                .map(v -> mapper.toModel(v, creator, partyCategory))
                .map(dataService::createParty)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST, "Не удалось создать мероприятие"));
    }

    public PartyDto updateParty(Long id, UpdatePartyDto dto) {
        validateCurrentUserIsCreator(id);

        return Optional.of(id)
                .map(v -> dataService.updateParty(id, dto))
                .map(v -> {
                    tgNotifyService.notify(TgNotifies.PARTY_UPDATE.getMessage().formatted(v.getName()), v);
                    return v;
                })
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST, "Ошибка изменения мероприятия"));
    }

    public PartyDto updatePartyVisibility(UpdateVisibilityDto dto) {
        validateCurrentUserIsCreator(dto.getId());

        return Optional.of(dto)
                .map(UpdateVisibilityDto::getVisibility)
                .map(Visibility::valueOf)
                .map(v -> dataService.updatePartyVisibility(dto.getId(), v))
                .map(mapper::toDto)
                .orElse(null);
    }

    public void deleteParty(Long id) {
        validateCurrentUserIsCreator(id);

        if (!dataService.existsById(id)) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено");
        }

        dataService.deleteParty(id);
    }

    public byte[] getPhoto(Long id) {
        return Optional.of(id)
                .map(dataService::getParty)
                .map(PartyModel::getPhoto)
                .orElse(null);
    }

    public void changePhoto(MultipartFile file, Long id) {
        PartyModel partyModel = Optional.of(id)
                .map(dataService::getParty)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));

        validateCurrentUserIsCreator(partyModel);

        try {
            byte[] content = file.getBytes();
            dataService.changePhoto(content, id);
            tgNotifyService.notify(
                    TgNotifies.CHANGE_PHOTO.getMessage().formatted(partyModel.getName()), partyModel);
        } catch (IOException e) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Ошибка при обработке фото");
        }
    }

    public void deletePhoto(Long id) {
        validateCurrentUserIsCreator(id);

        dataService.deletePhoto(id);
    }

    private void validateCurrentUserIsCreator(PartyModel partyModel) {
        String currentUser = UserCredentialsService.getCurrentUser();

        if (!partyModel.getCreator().getNickname().equals(currentUser)) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Вы не можете выполнить это действие, не будучи организатором");
        }
    }

    private void validateCurrentUserIsCreator(Long partyId) {
        PartyModel partyModel = Optional.of(partyId)
                .map(dataService::getParty)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));

        validateCurrentUserIsCreator(partyModel);
    }
}
