package mydudesgeo.mapper;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import mydudesgeo.data.Visibility;
import mydudesgeo.dto.party.CreatePartyDto;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyShortInfoDto;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.entity.Party;
import mydudesgeo.model.PartyCategoryModel;
import mydudesgeo.model.PartyModel;
import mydudesgeo.model.UserModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

@Mapper(uses = {
        PartyCategoryMapper.class,
        UserMapper.class
})
public abstract class PartyMapper {

    @Value("${mydudes.config.limit:0}")
    private Integer limits;

    @Mapping(target = "location.latitude", source = "latitude")
    @Mapping(target = "location.longitude", source = "longitude")
    public abstract PartyModel toModel(Party source);

    @Mapping(target = "latitude", source = "location.latitude")
    @Mapping(target = "longitude", source = "location.longitude")
    public abstract Party toEntity(PartyModel source);

    @Mapping(target = "latitude", source = "dto.location.latitude")
    @Mapping(target = "longitude", source = "dto.location.longitude")
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

    @Mapping(target = "time.startTime", source = "startTime")
    @Mapping(target = "time.endTime", source = "endTime")
    @Mapping(target = "creator", source = "creator.nickname")
    public abstract PartyShortInfoDto toShortInfoDto(PartyModel source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "description", source = "source.description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "location", source = "source.location")
    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "participants", expression = "java(emptyParticipantsList())")
    @Mapping(target = "limits", source = "source", qualifiedByName = "limit")
    @Mapping(target = "visibility", source = "source.visibility")
    @Mapping(target = "startTime", source = "source.startTime")
    @Mapping(target = "endTime", source = "source.endTime")
    @Mapping(target = "link", ignore = true)
    public abstract PartyModel toModel(CreatePartyDto source, UserModel creator, PartyCategoryModel category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "location.latitude", source = "latitude")
    @Mapping(target = "location.longitude", source = "longitude")
    @Mapping(target = "participants", expression = "java(emptyParticipantsList())")
    @Mapping(target = "limits", expression = "java(defaultLimit())")
    public abstract PartyModel toModel(String name,
                                       String description,
                                       UserModel creator,
                                       Visibility visibility,
                                       ZonedDateTime startTime,
                                       ZonedDateTime endTime,
                                       Double latitude,
                                       Double longitude,
                                       String link);

    public List<UserModel> emptyParticipantsList() {
        return Collections.emptyList();
    }

    public Integer defaultLimit() {
        return limits;
    }

    @Named("limit")
    public Integer limit(CreatePartyDto source) {
        return source.getLimits() == null ? limits : source.getLimits();
    }
}
