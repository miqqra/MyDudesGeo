package mydudesgeo.dataservice;

import java.util.Collection;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<UserModel> getUsersFromTelegram(List<String> tgNicknames) {
        return Optional.of(tgNicknames)
                .map(repository::findAllByTelegramNickIn)
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toModel)
                .toList();
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
    public void setChatId(String nickname, Long chatId) {
        repository.setTelegramChatIdChatId(nickname, chatId);
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
