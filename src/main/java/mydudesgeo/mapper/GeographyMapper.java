package mydudesgeo.mapper;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import mydudesgeo.common.Location;
import org.mapstruct.Mapper;

@Mapper
public class GeographyMapper {

    public Point getPoint(Double latitude, Double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(latitude, longitude);
        return geometryFactory.createPoint(coordinate);
    }

    public Point getPoint(Location location) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(location.getLatitude(), location.getLongitude());
        return geometryFactory.createPoint(coordinate);
    }
}
