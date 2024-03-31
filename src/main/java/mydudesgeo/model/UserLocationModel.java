package mydudesgeo.model;

import java.time.ZonedDateTime;
import lombok.Data;
import mydudesgeo.common.Location;

@Data
public class UserLocationModel {

    private Long id;
    private String nickname;
    private Location location;
    private ZonedDateTime time;
    private boolean freeze;
}
