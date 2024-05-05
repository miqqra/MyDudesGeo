package mydudesgeo.dataservice;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

}
