package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mydudesgeo.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("") //todo
public class UserController {

    private final UserService service;

    @GetMapping
    @Operation(description = "Получение информации пользователя")
    public void getInfo() {

    }

    @PutMapping
    @Operation(description = "Редактирование информации пользователя")
    public void updateInfo() {

    }

    @PutMapping
    @Operation(description = "Изменить фото профиля")
    public void changePhoto() {

    }

    @DeleteMapping
    @Operation(description = "Удалить фото профиля")
    public void deletePhoto() {

    }
}
