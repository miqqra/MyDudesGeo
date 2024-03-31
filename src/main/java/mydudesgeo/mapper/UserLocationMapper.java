package mydudesgeo.mapper;

import java.time.ZonedDateTime;
import mydudesgeo.common.Location;
import mydudesgeo.dto.user.UserLocationDto;
import mydudesgeo.entity.User;
import mydudesgeo.entity.UserLocation;
import mydudesgeo.model.UserLocationModel;
import mydudesgeo.model.UserModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {
        UserMapper.class
})
public abstract class UserLocationMapper {

    @Mapping(target = "location.latitude", source = "latitude")
    @Mapping(target = "location.longitude", source = "longitude")
    @Mapping(target = "nickname", source = "user.nickname")
    public abstract UserLocationModel toModel(UserLocation source);

    @Mapping(target = "latitude", source = "location.latitude")
    @Mapping(target = "longitude", source = "location.longitude")
    public abstract UserLocation toEntity(UserLocationModel source);

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "nickname", source = "source.nickname")
    @Mapping(target = "location", source = "source.location")
    @Mapping(target = "time", source = "source.time")
    @Mapping(target = "freeze", source = "userModel.freezeLocation")
    public abstract UserLocationDto toDto(UserLocationModel source, UserModel userModel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", ignore = true)
    @Mapping(target = "latitude", source = "location.latitude")
    @Mapping(target = "longitude", source = "location.longitude")
    public abstract UserLocation toEntity(User user, Location location);

    @AfterMapping
    protected void postMap(@MappingTarget UserLocation target, User user, Location location) {
        target.setTime(ZonedDateTime.now());
    }

    @Mapping(target = "latitude", source = "location.latitude")
    @Mapping(target = "longitude", source = "location.longitude")
    public abstract UserLocation updateLocation(@MappingTarget UserLocation user, Location location);
}
