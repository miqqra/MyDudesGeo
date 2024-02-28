package mydudesgeo.mapper;

import mydudesgeo.dto.party.CreatePartyDto;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyLocationDto;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.entity.Party;
import mydudesgeo.model.PartyModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;

@Mapper(uses = {GeographyMapper.class})
public abstract class PartyMapper {

    @Value("${mydudes.config.limit:0}")
    private Integer limits;

    public abstract PartyModel toModel(Party source);

    public abstract Party toEntity(PartyModel source);

    public abstract Party toEntity(@MappingTarget Party target, UpdatePartyDto dto);

    public abstract PartyDto toDto(PartyModel source);

    public abstract PartyLocationDto toLocationDto(PartyModel source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "limits", ignore = true)
    public abstract PartyModel toModel(CreatePartyDto source);

    @AfterMapping
    protected void postMap(@MappingTarget PartyModel target, CreatePartyDto source) {
        if (source == null) {
            return;
        }

        target.setParticipants(Collections.emptyList());
        target.setLimits(source.getLimits() == null ? limits : source.getLimits());
    }
}
