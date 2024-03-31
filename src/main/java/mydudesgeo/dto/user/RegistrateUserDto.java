package mydudesgeo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrateUserDto {

    @NotBlank
    @Schema(description = "Ник пользователя")
    private String nickname;

    @NotBlank
    @Schema(description = "Имя пользователя")
    private String firstName;

    @NotBlank
    @Schema(description = "Фамилия пользователя")
    private String lastName;

    @NotBlank
    @Schema(description = "Пароль")
    private String password;

    @NotBlank
    @Schema(description = "Пароль введенный повторно")
    private String matchingPassword;
}
