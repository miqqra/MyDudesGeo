package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/mydudes/geo/friends/requests")
public class FriendRequestController {

    @Operation(summary = "Отправить запрос дружбы")
    @PostMapping("/send/{to}")
    public void sendRequest(
            @Parameter(description = "Пользователь, которому отправляется запрос") @PathVariable String to) {
        //todo
    }

    @Operation(summary = "Принять запрос дружбы")
    @PostMapping("/accept/{to}")
    public void acceptRequest(
            @Parameter(description = "Пользователь, которому отправляется запрос") @PathVariable String to) {
        //todo
    }

    @Operation(summary = "Отклонить запрос дружбы")
    @PostMapping("/reject/{to}")
    public void rejectRequest(
            @Parameter(description = "Пользователь, которому отправляется запрос") @PathVariable String to) {
        //todo
    }
}
