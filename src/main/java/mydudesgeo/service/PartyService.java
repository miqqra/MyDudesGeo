package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Visibility;
import mydudesgeo.dataservice.PartyDataService;
import mydudesgeo.dto.party.CreatePartyDto;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyLocationDto;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.dto.party.UpdateVisibilityDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyMapper;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyDataService dataService;

    private final PartyMapper mapper;

    public List<PartyLocationDto> getPartiesAround(Integer radius, Point location) {
        //todo filter by visibility

        return Optional.of(radius)
                .map(r -> dataService.getPartiesAround(r, location))
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toLocationDto)
                .toList();
    }

    public PartyDto getParty(Long id) {
        //todo check visibility

        return Optional.ofNullable(id)
                .map(dataService::getParty)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));
    }

    public List<PartyDto> getUserParties(String user) {
        return Optional.ofNullable(user)
                .map(dataService::getUserParties)
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toDto)
                .toList();
    }

    public PartyDto createParty(CreatePartyDto dto) {
        return Optional.of(dto)
                .map(mapper::toModel)
                .map(dataService::createParty)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST, "Не удалось создать мероприятие"));
    }

    public PartyDto updateParty(Long id, UpdatePartyDto dto) {
        if (!dataService.existsById(id)) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено");
        }

        return Optional.of(id)
                .map(v -> dataService.updateParty(id, dto))
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST, "Ошибка изменения мероприятия"));
    }

    public PartyDto updatePartyVisibility(UpdateVisibilityDto dto) {
        return Optional.of(dto)
                .map(UpdateVisibilityDto::getVisibility)
                .map(Visibility::getEnum)
                .map(v -> dataService.updatePartyVisibility(dto.getId(), v))
                .map(mapper::toDto)
                .orElse(null);
    }

    public void deleteParty(Long id) {
        if (!dataService.existsById(id)) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено");
        }

        dataService.deleteParty(id);
    }
}
