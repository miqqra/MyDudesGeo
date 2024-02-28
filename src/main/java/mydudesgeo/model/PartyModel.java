package mydudesgeo.model;

import lombok.Data;
import mydudesgeo.common.Location;
import mydudesgeo.data.Visibility;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class PartyModel {

    private Long id;
    private String name;
    private String description;
    private String category;
    private Location location;
    private String creator;
    private List<String> participants;
    private Integer limits;
    private Visibility visibility;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

}
