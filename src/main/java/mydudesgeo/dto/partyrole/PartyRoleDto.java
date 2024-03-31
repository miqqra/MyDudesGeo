package mydudesgeo.dto.partyrole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PartyRoleDto {

    @Schema(description = "Id роли")
    private Long id;
    @Schema(description = "Название роли")
    private String name;
    @Schema(description = "Описание роли")
    private String description;
}
