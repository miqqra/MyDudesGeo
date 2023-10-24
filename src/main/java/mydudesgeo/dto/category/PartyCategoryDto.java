package mydudesgeo.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartyCategoryDto {

    @NotNull
    @Schema(description = "Идентификатор категории")
    private Long id;

    @Schema(description = "Имя категории")
    @NotBlank
    private String category;
}
