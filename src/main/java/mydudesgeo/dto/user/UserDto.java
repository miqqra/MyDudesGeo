package mydudesgeo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mydudesgeo.entity.Hobby;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "Профиль пользователя")
public class UserDto {

    @Schema(description = "Id пользователя")
    private Long id;

    @Schema(description = "Никнейм")
    private String nickname;

    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Фамилия")
    private String lastName;

    @Schema(description = "Дата рождения")
    private LocalDate birthDate;

    @Schema(description = "Пол")
    private String sex;

    @Schema(description = "О себе")
    private String info;

    @Schema(description = "Фото")
    private byte[] photo;

    @Schema(description = "Флаг заморозки чтения местоположения пользователя")
    private boolean freezeLocation;

    @Schema(description = "Увлечения")
    private List<Hobby> hobbies;
}
