package mydudesgeo.mapper;

import mydudesgeo.dto.partyrole.CreatePartyRoleDto;
import mydudesgeo.dto.partyrole.PartyRoleDto;
import mydudesgeo.entity.PartyRole;
import mydudesgeo.model.PartyModel;
import mydudesgeo.model.PartyRoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = {
        PartyMapper.class
})
public abstract class PartyRoleMapper {

    public abstract PartyRoleModel toModel(PartyRole source);
    //todo check mappings

    public abstract List<PartyRoleModel> toModel(List<PartyRole> source);

    public abstract PartyRole toEntity(PartyRoleModel source);

    public abstract PartyRoleModel toModel(PartyRoleDto source);

    public abstract PartyRoleDto toDto(PartyRoleModel source);

    public abstract List<PartyRoleDto> toDto(List<PartyRoleModel> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "description", source = "source.description")
    @Mapping(target = "party", source = "party")
    public abstract PartyRoleModel toModel(CreatePartyRoleDto source, PartyModel party);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "party", ignore = true)
    public abstract PartyRoleModel toModel(@MappingTarget PartyRoleModel target, PartyRoleDto dto);
}
