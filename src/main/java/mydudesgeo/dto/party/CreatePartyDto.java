package mydudesgeo.dto.party;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import lombok.Data;
import mydudesgeo.common.Location;
import mydudesgeo.data.Visibility;

@Data
@Schema(description = "Запрос на создание мероприятия")
public class CreatePartyDto {

    @NotBlank
    @Schema(description = "Название мероприятия")
    private String name;

    @NotBlank
    @Schema(description = "Описание мероприятия")
    private String description;

    @NotBlank
    @Schema(description = "Категория мероприятия")
    private String category;

    @NotNull
    @Schema(description = "Локация мероприятия")
    private Location location;

    @Schema(description = "Ограничение по количеству человек")
    private Integer limits;

    @NotNull
    @Schema(description = "Видимость мероприятия")
    private Visibility visibility;

    @NotNull
    @Schema(description = "Время начала мероприятия")
    private ZonedDateTime startTime;
    @Schema(description = "Время конца мероприятия")
    private ZonedDateTime endTime;

}
