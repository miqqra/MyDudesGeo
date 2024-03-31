package mydudesgeo.dto.party;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Schema(description = "Данные о времени начала и времени конца мероприятия")
@Data
public class PartyTimeInfoDto {

    @Schema(description = "Время начала мероприятия")
    private ZonedDateTime startTime;

    @Schema(description = "Время конца мероприятия")
    private ZonedDateTime endTime;
}
