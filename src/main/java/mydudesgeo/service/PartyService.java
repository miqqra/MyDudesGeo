package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.data.Visibility;
import mydudesgeo.dataservice.FriendsDataService;
import mydudesgeo.dataservice.PartyDataService;
import mydudesgeo.dto.party.CreatePartyDto;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyLocationDto;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.dto.party.UpdateVisibilityDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyDataService dataService;
    private final FriendsDataService friendsDataService;


    private final PartyMapper mapper;

    public List<PartyLocationDto> getPartiesAround(Double radius, Location location) {
        String authUser = UserContextService.getCurrentUser();

        return Optional.of(radius)
                .map(r -> dataService.getPartiesAround(location, r))
                .stream()
                .flatMap(Collection::stream)
                .filter(v -> friendsDataService.filterUsers(v, authUser))
                .map(mapper::toLocationDto)
                .toList();
    }

    public PartyDto getParty(Long id) {
        String authUser = UserContextService.getCurrentUser();

        return Optional.ofNullable(id)
                .map(dataService::getParty)
                .filter(v -> friendsDataService.filterUsers(v, authUser))
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));
    }

    public List<PartyDto> getUserParties(String user) {
        String authUser = UserContextService.getCurrentUser();

        return Optional.ofNullable(user)
                .map(dataService::getUserParties)
                .stream()
                .flatMap(Collection::stream)
                .filter(v -> friendsDataService.filterUsers(v, authUser))
                .map(mapper::toDto)
                .toList();
    }

    public PartyDto createParty(CreatePartyDto dto) {
        //todo checkVisibility
        return Optional.of(dto)
                .map(mapper::toModel)
                .map(dataService::createParty)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST, "Не удалось создать мероприятие"));
    }

    public PartyDto updateParty(Long id, UpdatePartyDto dto) {
        //todo checkVisibility
        if (!dataService.existsById(id)) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено");
        }

        return Optional.of(id)
                .map(v -> dataService.updateParty(id, dto))
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST, "Ошибка изменения мероприятия"));
    }

    public PartyDto updatePartyVisibility(UpdateVisibilityDto dto) {
        //todo checkVisibility
        return Optional.of(dto)
                .map(UpdateVisibilityDto::getVisibility)
                .map(Visibility::valueOf)
                .map(v -> dataService.updatePartyVisibility(dto.getId(), v))
                .map(mapper::toDto)
                .orElse(null);
    }

    public void deleteParty(Long id) {
        //todo check visivility
        if (!dataService.existsById(id)) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено");
        }

        dataService.deleteParty(id);
    }
}
