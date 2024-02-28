package mydudesgeo.dto.user;

import lombok.Data;
import mydudesgeo.common.Location;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String name;
    private Location location;
    private LocalDateTime time;
    private boolean freeze;
}
