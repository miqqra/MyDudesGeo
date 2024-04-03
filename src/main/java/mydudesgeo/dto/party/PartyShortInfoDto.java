package mydudesgeo.dto.party;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mydudesgeo.common.Location;

@Data
public class PartyShortInfoDto {

    @Schema(description = "Идентификатор мероприятия")
    private Long id;

    @Schema(description = "Название мероприятия")
    private String name;

    @Schema(description = "Никнейм организатора")
    private String creator;

    @Schema(description = "Время начала и конца мероприятия")
    private PartyTimeInfoDto time;

    @Schema(description = "Локация мероприятия")
    private Location location;
}
