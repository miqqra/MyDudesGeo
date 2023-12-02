package mydudesgeo.dto.user;

import lombok.Data;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String name;
    private Point location;
    private LocalDateTime time;
    private boolean freeze;
}
