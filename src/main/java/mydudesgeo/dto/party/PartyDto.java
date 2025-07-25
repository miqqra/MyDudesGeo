package mydudesgeo.dto.party;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mydudesgeo.common.Location;
import mydudesgeo.data.Visibility;
import mydudesgeo.dto.user.UserDto;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class PartyDto {

    @Schema(description = "Id мероприятия")
    private Long id;
    @Schema(description = "Название мероприятия")
    private String name;
    @Schema(description = "описание мероприятия")
    private String description;
    @Schema(description = "Категория мероприятия")
    private String category;
    @Schema(description = "локация мероприятия")
    private Location location;
    @Schema(description = "id организатора")
    private UserDto creator;
    @Schema(description = "список участников")
    private List<String> participants;
    @Schema(description = "Ограничение по количеству человек")
    private Integer limits;
    @Schema(description = "Видимость мероприятия")
    private Visibility visibility;
    @Schema(description = "Время начала мероприятия")
    private ZonedDateTime startTime;
    @Schema(description = "Время конца мероприятия")
    private ZonedDateTime endTime;
}
