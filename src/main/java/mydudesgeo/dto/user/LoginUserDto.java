package mydudesgeo.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
