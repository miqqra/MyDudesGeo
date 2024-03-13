package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.dataservice.UserDataService;
import mydudesgeo.dto.user.UpdateUserInfoDto;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDataService dataService;

    private final UserMapper mapper;

    public UserDto getInfo(String nickname) {
        return Optional.of(nickname)
                .map(dataService::getInfo)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Пользователь не найден"));
    }

    public UserDto createInfo(UpdateUserInfoDto dto) {
        String nickname = UserContextService.getCurrentUser();

        return Optional.of(dto)
                .map(v -> mapper.toModel(v, nickname))
                .map(dataService::create)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Не удалось создать профиль пользоваетеля"));
    }

    public UserDto updateInfo(UpdateUserInfoDto dto) {
        String nickname = UserContextService.getCurrentUser();

        return Optional.of(dto)
                .map(v -> mapper.toModel(v, nickname))
                .map(dataService::create)
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Не удалось обновить профиль пользоввателя"));
    }

    public void changePhoto(MultipartFile file) {
        try {
            byte[] content = file.getBytes();
            String nickname = UserContextService.getCurrentUser();
            dataService.changePhoto(content, nickname);
        } catch (IOException e) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Ошибка при обработке фото");
        }
    }

    public void deletePhoto() {
        String nickname = UserContextService.getCurrentUser();

        dataService.deletePhoto(nickname);
    }
}
