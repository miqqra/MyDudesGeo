package mydudesgeo.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Location {

    @Schema(description = "Широта")
    private Double latitude;
    @Schema(description = "Долгота")
    private Double longitude;
}
