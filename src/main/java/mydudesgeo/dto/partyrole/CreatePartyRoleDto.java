package mydudesgeo.dto.partyrole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreatePartyRoleDto {

    @Schema(description = "Название роли")
    private String name;
    @Schema(description = "Описание роли")
    private String description;
    @Schema(description = "Id мероприятия")
    private Long partyId;
}
