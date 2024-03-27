package mydudesgeo.mapper;

import mydudesgeo.common.Location;
import mydudesgeo.dto.user.UserLocationDto;
import mydudesgeo.entity.UserLocation;
import mydudesgeo.model.UserLocationModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.ZonedDateTime;

@Mapper(uses = {GeographyMapper.class})
public abstract class UserLocationMapper {

    public abstract UserLocationModel toModel(UserLocation source);

    public abstract UserLocation toEntity(UserLocationModel source);

    public abstract UserLocationDto toDto(UserLocationModel source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", ignore = true)
    public abstract UserLocation toEntity(String name, Location location);

    @AfterMapping
    protected void postMap(@MappingTarget UserLocation target, String name, Location location) {
        target.setTime(ZonedDateTime.now());
    }

    public abstract UserLocation updateLocation(@MappingTarget UserLocation user, Location location);

}
