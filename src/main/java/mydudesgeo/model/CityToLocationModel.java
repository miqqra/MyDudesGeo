package mydudesgeo.model;

import lombok.Data;

@Data
public class CityToLocationModel {

    private Long id;
    private String city;
    private Float latitude;
    private Float longitude;

}
