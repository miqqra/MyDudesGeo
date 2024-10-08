package mydudesgeo.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.user.LoginUserDto;
import mydudesgeo.dto.user.RegistrateUserDto;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.service.UserCredentialsService;
import mydudesgeo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class RegistrationController {

    private final HttpServletRequest httpServletRequest;
    private final UserCredentialsService service;
    private final UserService userService;

    @GetMapping("/self")
    @Operation(summary = "Получить данные о юзере")
    public UserDto getCurrentUser() {
        return userService.getCurrentUserInfo();
    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public boolean register(@RequestBody @Valid RegistrateUserDto dto) {
        return service.registerUser(dto, httpServletRequest);
    }

    @Operation(summary = "Логин пользователя")
    @PostMapping("/login")
    public boolean login(@RequestBody @Valid LoginUserDto dto) {
        return service.loginUser(dto, httpServletRequest);
    }

    @Operation(summary = "Логаут пользователя")
    @PostMapping("/logout")
    public void logout() {
        service.logoutUser(httpServletRequest);
    }
}
