package mydudesgeo.dto.party;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateVisibilityDto {

    @NotNull
    @Schema(description = "Id мероприятия")
    private Long id;

    @NotBlank
    @Schema(description = "Новая видимость мероприятия")
    private String visibility;
}
