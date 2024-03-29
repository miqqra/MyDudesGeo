package mydudesgeo.dto.party;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mydudesgeo.data.Point;

@Data
public class PartyLocationDto {

    @Schema(description = "Идентификатор мероприятия")
    private Long id;
    @Schema(description = "Локация мероприятия")
    private Point location;
}
