package mydudesgeo.mapper;

import mydudesgeo.dto.user.UserDto;
import mydudesgeo.entity.User;
import mydudesgeo.model.UserModel;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserMapper {

    public abstract UserModel toModel(User source);

    public abstract User toEntity(UserModel source);

    public abstract UserDto toDto(UserModel source);

}
