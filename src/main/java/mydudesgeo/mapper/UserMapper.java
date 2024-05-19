package mydudesgeo.mapper;

import mydudesgeo.dto.user.UpdateUserInfoDto;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.entity.User;
import mydudesgeo.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper {

    public abstract User toEntity(UserModel source);

    public abstract UserDto toDto(UserModel source);

    public abstract UserModel toModel(User source);

    public abstract UserModel toModel(UpdateUserInfoDto source, String nickname);

    public abstract UserModel toModel(@MappingTarget UserModel target, UpdateUserInfoDto source);
}
