package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Point;
import mydudesgeo.mapper.UserMapper;
import mydudesgeo.model.UserModel;
import mydudesgeo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Transactional
    public void createUser(String name, Point location) {
        Optional.of(name)
                .map(nam -> new UserModel()
                        .setName(name)
                        .setLocation(location)
                        .setTime(ZonedDateTime.now())
                        .setFreeze(false))
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toModel);
    }

    @Transactional
    public UserModel updateLocation(String name, Point location) {
        return Optional.of(name)
                .map(repository::getUserByName)
                .map(user -> user.setLocation(location))
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
