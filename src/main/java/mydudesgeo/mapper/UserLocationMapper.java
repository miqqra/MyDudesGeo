package mydudesgeo.mapper;

import mydudesgeo.common.Location;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.entity.UserLocation;
import mydudesgeo.model.UserModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.ZonedDateTime;

@Mapper(uses = {GeographyMapper.class})
public abstract class UserLocationMapper {

    public abstract UserModel toModel(UserLocation source);

    public abstract UserLocation toEntity(UserModel source);

    public abstract UserDto toDto(UserModel source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", ignore = true)
    @Mapping(target = "freeze", constant = "false")
    public abstract UserLocation toEntity(String name, Location location);

    @AfterMapping
    protected void postMap(@MappingTarget UserLocation target, String name, Location location) {
        target.setTime(ZonedDateTime.now());
    }

    public abstract UserLocation updateLocation(@MappingTarget UserLocation user, Location location);

}