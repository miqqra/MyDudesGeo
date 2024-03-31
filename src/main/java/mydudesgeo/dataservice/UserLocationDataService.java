package mydudesgeo.dataservice;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.mapper.UserLocationMapper;
import mydudesgeo.mapper.UserMapper;
import mydudesgeo.model.UserLocationModel;
import mydudesgeo.model.UserModel;
import mydudesgeo.repository.UserLocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLocationDataService {
    //don't user repo::save, use repo::create, repo::update instead

    private final UserLocationRepository repository;

    private final UserLocationMapper mapper;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return repository.existsByUserNickname(name);
    }

    @Transactional
    public void createUser(UserModel userModel, Location location) {
        Optional.of(userModel)
                .map(userMapper::toEntity)
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
