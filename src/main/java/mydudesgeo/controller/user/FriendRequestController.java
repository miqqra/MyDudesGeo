package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.friend.FriendRequestDto;
import mydudesgeo.service.FriendRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/mydudes/geo/friends/requests")
public class FriendRequestController {

    private final FriendRequestService service;

    @Operation(summary = "Отправить запрос дружбы")
    @PostMapping("/send/{to}")
    public void sendRequest(
            @Parameter(description = "Пользователь, которому отправляется запрос") @PathVariable String to) {
        service.sendRequest(to);
    }

    @Operation(summary = "Принять запрос дружбы")
    @PostMapping("/accept/{from}")
    public void acceptRequest(
            @Parameter(description = "Пользователь, которому отправляется запрос") @PathVariable String from) {
        service.acceptRequest(from);
    }

    @Operation(summary = "Отклонить запрос дружбы")
    @PostMapping("/reject/{from}")
    public void rejectRequest(
            @Parameter(description = "Пользователь, которому отправляется запрос") @PathVariable String from) {
        service.rejectRequest(from);
    }

    @Operation(summary = "Посмотреть запросы дружбы")
    @GetMapping
    public List<FriendRequestDto> getRequests() {
        return service.getRequests();
    }
}
