package mydudesgeo.mapper;

import mydudesgeo.entity.CityToLocation;
import mydudesgeo.model.CityToLocationModel;
import org.mapstruct.Mapper;

@Mapper
public abstract class CityToLocationMapper {

    public abstract CityToLocationModel toModel(CityToLocation source);

}
