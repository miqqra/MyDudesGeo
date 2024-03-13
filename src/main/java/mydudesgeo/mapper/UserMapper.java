package mydudesgeo.mapper;

import mydudesgeo.dto.user.UpdateUserInfoDto;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.entity.User;
import mydudesgeo.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class UserMapper {

    public abstract User toEntity(UserModel source);

    public abstract UserDto toDto(UserModel source);

    public abstract UserModel toModel(User source);

    @Mapping(target = "id", ignore = true)
    public abstract UserModel toModel(UpdateUserInfoDto source, String nickname);
}
