package mydudesgeo.mapper;

import mydudesgeo.dto.party.CreatePartyDto;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyShortInfoDto;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.entity.Party;
import mydudesgeo.model.PartyModel;
import mydudesgeo.model.UserModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Mapper(uses = {
        GeographyMapper.class,
        PartyCategoryMapper.class,
        UserMapper.class
})
public abstract class PartyMapper {

    @Value("${mydudes.config.limit:0}")
    private Integer limits;


    public abstract PartyModel toModel(Party source);

    public abstract Party toEntity(PartyModel source);

    public abstract Party toEntity(@MappingTarget Party target, UpdatePartyDto dto);

    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "category", source = "source.category.category")
    public abstract PartyDto toDto(PartyModel source);

    @AfterMapping
    protected void postMap(@MappingTarget PartyDto target, PartyModel source) {
        target.setParticipants(Optional.of(source)
                .map(PartyModel::getParticipants)
                .stream()
                .flatMap(Collection::stream)
                .map(UserModel::getFullName)
                .toList());
    }

    public abstract PartyShortInfoDto toShortInfoDto(PartyModel source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "limits", ignore = true)
    @Mapping(target = "category.category", source = "category")
    @Mapping(target = "creator.nickname", source = "creator")
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
