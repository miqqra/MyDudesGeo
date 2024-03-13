package mydudesgeo.model;

import lombok.Data;
import mydudesgeo.common.Location;

import java.time.ZonedDateTime;

@Data
public class UserLocationModel {

    private Long id;
    private String name;
    private Location location;
    private ZonedDateTime time;
    private boolean freeze;
}
