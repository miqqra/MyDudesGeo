package mydudesgeo.dataservice;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.mapper.UserMapper;
import mydudesgeo.model.UserModel;
import mydudesgeo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Transactional(readOnly = true)
    public UserModel getInfo(String nickname) {
        return Optional.of(nickname)
                .map(repository::findByNickname)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional
    public UserModel create(UserModel userModel) {
        return Optional.of(userModel)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Boolean getFreezeToggle(String nickname) {
        return Optional.of(nickname)
                .map(repository::findFreezeFlagByNickname)
                .orElse(null);
    }

    @Transactional
    public void changePhoto(byte[] file, String nickname) {
        repository.updatePhoto(file, nickname);
    }

    @Transactional
    public void deletePhoto(String nickname) {
        repository.deletePhotoByNickname(nickname);
    }

    @Transactional
    public void changeFreezeToggle(String name, Boolean freeze) {
        repository.changeFreezeToggle(name, freeze);
    }
}
