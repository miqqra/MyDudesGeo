package mydudesgeo.model;

import lombok.Data;

@Data
public class CityToLocationModel {

    private Long id;
    private String city;
    private Double latitude;
    private Double longitude;

}
