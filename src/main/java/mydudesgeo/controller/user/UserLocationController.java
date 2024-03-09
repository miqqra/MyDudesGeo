package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.dto.user.UserDto;
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
@RequestMapping("api/mydudes/geo/{user}/location")
public class UserLocationController {

    private final UserLocationService service;

    @PutMapping
    @Operation(description = "Обновление локации пользователя")
    public void updateLocation(@Parameter(description = "Id пользователя") @PathVariable String user,
                               @RequestBody Location location) {
        service.updateLocation(user, location);
    }

    @GetMapping
    @Operation(description = "Получение локации пользователя")
    public UserDto getLocation(@Parameter(description = "Id пользователя") @PathVariable String user) {
        return service.getLocation(user);
        //todo checkvisibility
        //todo filter by freeze
    }

    @PutMapping("/freeze")
    @Operation(description = "Изменение состояния заморозки пользователя")
    public void changeFreezeToggle(@Parameter(description = "Id пользователя") @PathVariable String user,
                                   @Parameter(description = "Новое состояние") @RequestParam Boolean freeze) {
        service.changeFreezeToggle(user, freeze);
    }
}