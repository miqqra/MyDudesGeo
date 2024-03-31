package mydudesgeo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import mydudesgeo.common.Location;

@Data
public class UserLocationDto {

    @Schema(description = "Id пользователя")
    private Long id;
    @Schema(description = "Никнейм пользователя")
    private String nickname;
    @Schema(description = "Локация пользователя")
    private Location location;
    @Schema(description = "Время чтения локации")
    private LocalDateTime time;
    @Schema(description = "Флаг заморозки локации")
    private boolean freeze;
}
