package mydudesgeo.dto.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mydudesgeo.data.Visibility;
import mydudesgeo.dto.user.UserDto;

import java.util.List;

@Data
@Schema(description = "Список друзей пользователя")
public class FriendsDto {

    @Schema(description = "Id пользователя")
    private UserDto person;

    @Schema(description = "Категория друзей")
    private Visibility visibility;

    @Schema(description = "Список друзей пользователя")
    private List<UserDto> friends;
}
