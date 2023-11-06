package mydudesgeo.dto.party;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mydudesgeo.data.Visibility;
import org.springframework.data.geo.Point;

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
    private Point location;
    @Schema(description = "id организатора")
    private String creator;
    @Schema(description = "список участников")
    private List<String> participants;
    @Schema(description = "Ограничение по количеству человек")
    private Integer limits;
    @Schema(description = "Видимость мероприятия")
    private Visibility visibility;
}
