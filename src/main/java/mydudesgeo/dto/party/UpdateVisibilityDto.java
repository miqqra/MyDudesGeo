package mydudesgeo.dto.party;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateVisibilityDto {

    @NotNull
    private Long id;

    @NotBlank
    private String visibility;
}
