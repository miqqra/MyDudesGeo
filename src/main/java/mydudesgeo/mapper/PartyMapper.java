package mydudesgeo.mapper;

import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.entity.Party;
import mydudesgeo.model.PartyModel;
import org.mapstruct.Mapper;

@Mapper
public abstract class PartyMapper {

    public abstract PartyModel toModel(Party source);

    public abstract Party toEntity(PartyModel source);

    public abstract PartyDto toDto(PartyModel source);

    public abstract PartyModel toModel(PartyDto source);
}
