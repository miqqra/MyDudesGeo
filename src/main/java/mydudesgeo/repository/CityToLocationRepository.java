package mydudesgeo.repository;

import mydudesgeo.entity.CityToLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityToLocationRepository extends JpaRepository<CityToLocation, Long> {

    CityToLocation findByCityIgnoreCase(String city);
}
