package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.mapper.UserLocationMapper;
import mydudesgeo.model.UserModel;
import mydudesgeo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLocationDataService {

    private final UserRepository repository;

    private final UserLocationMapper mapper;

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Transactional
    public void createUser(String name, Location location) {
        Optional.of(name)
                .map(n -> mapper.toEntity(n, location))
                .map(repository::save)
                .map(mapper::toModel);
    }

    @Transactional
    public UserModel updateLocation(String name, Location location) {
        return Optional.of(name)
                .map(repository::getUserByName)
                .map(user -> mapper.updateLocation(user, location))
                .map(repository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public UserModel getLocation(String user) {
        return Optional.of(user)
                .map(repository::getUserByName)
                .map(mapper::toModel)
                .orElse(null);

        //todo checkvisibility
    }

    @Transactional
    public void changeFreezeToggle(String name, Boolean freeze) {
        repository.changeFreezeToggle(name, freeze);
    }
}
