package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.user.UpdateUserInfoDto;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/mydudes/geo/user")
public class UserController {

    private final UserService service;

    @GetMapping("/{nickname}")
    @Operation(description = "Получение информации пользователя")
    public UserDto getInfo(@Parameter(description = "Ник пользователя") @PathVariable String nickname) {
        return service.getInfo(nickname);
    }

    @PostMapping
    @Operation(description = "Создание профиля пользователя")
    public UserDto createInfo(@Validated @RequestBody UpdateUserInfoDto dto) {
        return service.createInfo(dto);
    }

    @PutMapping
    @Operation(description = "Редактирование информации пользователя")
    public UserDto updateInfo(@Validated @RequestBody UpdateUserInfoDto dto) {
        return service.updateInfo(dto);
    }

    @PutMapping("/photo")
    @Operation(description = "Изменить фото профиля")
    public void changePhoto(@RequestParam MultipartFile photo) {
        service.changePhoto(photo);
    }

    @DeleteMapping("/photo")
    @Operation(description = "Удалить фото профиля")
    public void deletePhoto() {
        service.deletePhoto();
    }
}
