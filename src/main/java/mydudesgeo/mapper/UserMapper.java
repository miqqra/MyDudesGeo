package mydudesgeo.mapper;

import mydudesgeo.common.Location;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.entity.User;
import mydudesgeo.model.UserModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.ZonedDateTime;

@Mapper(uses = {GeographyMapper.class})
public abstract class UserMapper {

    public abstract UserModel toModel(User source);

    public abstract User toEntity(UserModel source);

    public abstract UserDto toDto(UserModel source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", ignore = true)
    @Mapping(target = "freeze", constant = "false")
    public abstract User toEntity(String name, Location location);

    @AfterMapping
    protected void postMap(@MappingTarget User target, String name, Location location) {
        target.setTime(ZonedDateTime.now());
    }

    public abstract User updateLocation(@MappingTarget User user, Location location);

}
