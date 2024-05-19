package mydudesgeo.dataservice;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.mapper.CityToLocationMapper;
import mydudesgeo.model.CityToLocationModel;
import mydudesgeo.repository.CityToLocationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityToLocationDataService {

    private final CityToLocationRepository repository;

    private final CityToLocationMapper mapper;

    public CityToLocationModel findByCity(String city) {
        return Optional.of(city)
                .map(repository::findByCityIgnoreCase)
                .map(mapper::toModel)
                .orElse(null);
    }

    public Location findAnyLocation() {
        return Optional.of(repository.findAll())
                .map(List::getFirst)
                .map(mapper::toModel)
                .map(v -> new Location()
                        .setLatitude(v.getLatitude())
                        .setLongitude(v.getLongitude())
                )
                .orElse(null);
    }

    public CityToLocationModel findAnyLocationModel() {
        return Optional.of(repository.findAll())
                .map(List::getFirst)
                .map(mapper::toModel)
                .orElse(null);
    }

}
