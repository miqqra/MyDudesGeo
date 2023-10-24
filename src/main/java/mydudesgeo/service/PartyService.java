package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Visibility;
import mydudesgeo.dataservice.PartyDataService;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyLocationDto;
import mydudesgeo.dto.party.UpdateVisibilityDto;
import mydudesgeo.mapper.PartyMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyDataService dataService;

    private final PartyMapper mapper;

    public List<PartyLocationDto> getPartiesAround(Integer radius, Double coordX, Double coordY) {
        return null;
    }

    public PartyDto updatePartyVisibility(UpdateVisibilityDto dto) {
        return Optional.of(dto)
                .map(UpdateVisibilityDto::getVisibility)
                .map(Visibility::getEnum)
                .map(v -> dataService.updatePartyVisibility(dto.getId(), v))
                .map(mapper::toDto)
                .orElse(null);
    }
}
