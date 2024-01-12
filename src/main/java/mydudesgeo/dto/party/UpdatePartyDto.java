package mydudesgeo.dto.party;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mydudesgeo.data.Point;

@Data
@Schema(description = "Запрос на редактирование мероприятия")
public class UpdatePartyDto {

    @Schema(description = "Название мероприятия")
    private String name;
    @Schema(description = "Описание мероприятия")
    private String description;
    @Schema(description = "Локация мероприятия")
    private Point location;
    @Schema(description = "Ограничение по людям")
    private Integer limits;
}
