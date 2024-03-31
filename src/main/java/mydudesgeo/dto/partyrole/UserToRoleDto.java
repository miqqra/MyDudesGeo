package mydudesgeo.dto.partyrole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserToRoleDto {

    @NotNull
    private Long roleId;

    @NotBlank
    private String username;
}
