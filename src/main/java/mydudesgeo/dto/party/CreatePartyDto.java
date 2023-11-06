package mydudesgeo.dto.party;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mydudesgeo.data.Visibility;
import org.springframework.data.geo.Point;

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
    private Point location;

    @NotBlank
    @Schema(description = "Id организатора")
    private String creator;

    @Schema(description = "Ограничение по количеству человек")
    private Integer limits;

    @NotNull
    @Schema(description = "Видимость мероприятия")
    private Visibility visibility;

}
