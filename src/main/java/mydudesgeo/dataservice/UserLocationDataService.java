package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.mapper.UserLocationMapper;
import mydudesgeo.model.UserLocationModel;
import mydudesgeo.repository.UserLocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLocationDataService {

    private final UserLocationRepository repository;

    private final UserLocationMapper mapper;

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return repository.existsByUserNickname(name);
    }

    @Transactional
    public void createUser(String name, Location location) {
        Optional.of(name)
                .map(n -> mapper.toEntity(n, location))
                .map(repository::save)
                .map(mapper::toModel);
    }

    @Transactional
    public UserLocationModel updateLocation(String name, Location location) {
        return Optional.of(name)
                .map(repository::findByUserNickname)
                .map(user -> mapper.updateLocation(user, location))
                .map(repository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public UserLocationModel getLocation(String user) {
        return Optional.of(user)
                .map(repository::findByUserNickname)
                .map(mapper::toModel)
                .orElse(null);

        //todo checkvisibility
    }
}
