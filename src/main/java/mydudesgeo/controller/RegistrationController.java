package mydudesgeo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.user.LoginUserDto;
import mydudesgeo.dto.user.RegistrateUserDto;
import mydudesgeo.service.UserCredentialsService;
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

    @PostMapping("/register")
    public boolean register(@RequestBody @Valid RegistrateUserDto dto) {
        return service.registerUser(dto, httpServletRequest);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody @Valid LoginUserDto dto) {
        return service.loginUser(dto, httpServletRequest);
    }

    @PostMapping("/logout")
    public void logout() {
        service.logoutUser(httpServletRequest);
    }
}
