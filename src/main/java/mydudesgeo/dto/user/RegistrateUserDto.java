package mydudesgeo.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrateUserDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String password;
    private String matchingPassword;
}
