package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.dto.user.UserLocationDto;
import mydudesgeo.service.UserLocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mydudes/geo/location")
public class UserLocationController {

    private final UserLocationService service;

    @PutMapping
    @Operation(summary = "Обновление локации пользователя")
    public void updateLocation(@RequestBody Location location) {
        service.updateLocation(location);
    }

    @GetMapping("/{user}")
    @Operation(summary = "Получение локации пользователя")
    public UserLocationDto getLocation(@Parameter(description = "Никнейм пользователя") @PathVariable String user) {
        return service.getLocation(user);
        //todo checkvisibility
        //todo filter by freeze
    }

    @PutMapping("/freeze")
    @Operation(summary = "Изменение состояния заморозки пользователя")
    public void changeFreezeToggle(@Parameter(description = "Новое состояние") @RequestParam Boolean freeze) {
        service.changeFreezeToggle(freeze);
    }
}
