package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.friend.FriendsDto;
import mydudesgeo.service.FriendsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/mydudes/geo/friends")
public class FriendsController {

    /**
     * Категория видимости берется из enum Visibility, исключая оттуда ALL и NOTHING
     */

    private final FriendsService service;

    @Operation(description = "Получения списка друзей из определенной категории видимости")
    @GetMapping("/{visibility}")
    public FriendsDto getFriends(
            @Parameter(description = "Категория видимости") @PathVariable String visibility,
            @Parameter(description = "id авторизованного пользователя") @RequestParam String authUser
    ) {
        return service.getFriends(visibility, authUser);
    }


    @Operation(description = "Добавление нового друга в категорию друзей")
    @PostMapping("/{visibility}")
    public FriendsDto addFriend(
            @Parameter(description = "Категория видимости") @PathVariable String visibility,
            @Parameter(description = "id авторизованного пользователя") @RequestParam String authUser,
            @Parameter(description = "id пользователя, которого нужно добавить в категорию друзей") @RequestParam String friend
    ) {
        return service.addFriend(visibility, authUser, friend);
    }

    @Operation(description = "Удаление человека из категории друзей")
    @DeleteMapping("/{visibility}")
    public void deleteFriend(
            @Parameter(description = "Категория видимости") @PathVariable String visibility,
            @Parameter(description = "id авторизованного пользователя") @RequestParam String authUser,
            @Parameter(description = "id пользователя, которого нужно добавить в категорию друзей") @RequestParam String friend
    ) {
        service.deleteFriend(visibility, authUser, friend);
    }


}
