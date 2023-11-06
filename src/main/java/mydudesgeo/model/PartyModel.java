package mydudesgeo.model;

import lombok.Data;
import mydudesgeo.data.Visibility;
import org.springframework.data.geo.Point;

import java.util.List;

@Data
public class PartyModel {

    private Long id;
    private String name;
    private String description;
    private String category;
    private Point location;
    private String creator;
    private List<String> participants;
    private Integer limits;
    private Visibility visibility;

}
