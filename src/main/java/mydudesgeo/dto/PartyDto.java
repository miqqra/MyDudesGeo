package mydudesgeo.dto;

import lombok.Data;
import mydudesgeo.data.PointData;
import mydudesgeo.data.Visibility;

import java.util.List;

@Data
public class PartyDto {

    private Long id;
    private String name;
    private String description;
    private String category;
    private PointData location;
    private String creator;
    private List<String> participants;
    private Integer limits;
    private Visibility visibility;
}
