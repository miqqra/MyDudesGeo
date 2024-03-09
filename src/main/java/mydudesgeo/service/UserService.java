package mydudesgeo.service;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Service
@RequiredArgsConstructor
public class UserService {

    @GetMapping
    @Operation(description = "Получение информации пользователя")
    public void getInfo() {

    }

    @PutMapping
    @Operation(description = "Редактирование информации пользователя")
    public void updateInfo() {

    }
}
