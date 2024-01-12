package mydudesgeo.model;

import lombok.Data;
import mydudesgeo.data.Point;

import java.time.ZonedDateTime;

@Data
public class UserModel {

    private Long id;
    private String name;
    private Point location;
    private ZonedDateTime time;
    private boolean freeze;
}
