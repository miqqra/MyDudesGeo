package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Visibility;
import mydudesgeo.dataservice.FriendsDataService;
import mydudesgeo.dto.friend.FriendsDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.FriendMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendsService {

    private final FriendsDataService dataService;

    private final FriendMapper mapper;

    public FriendsDto getFriends(String visibility) {
        String authUser = UserContextService.getCurrentUser();

        return Optional.of(visibility)
                .map(this::validateVisibility)
                .map(v -> dataService.getFriends(v, authUser))
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Не удалось получить список друзей"));
    }

    public FriendsDto addFriend(Visibility visibility, String friend) {
        String authUser = UserContextService.getCurrentUser();

        validateIfFriendNotExistInFriendList(visibility, authUser, friend);

        return Optional.of(visibility)
                .map(v -> dataService.addFriend(v, authUser, friend))
                .map(mapper::toDto)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Не удалось добавить друга"));
    }

    public void deleteFriend(Visibility visibility, String friend) {
        String authUser = UserContextService.getCurrentUser();

        validateIfFriendExistsInFriendList(visibility, authUser, friend);

        dataService.deleteFriend(visibility, authUser, friend);
    }

    private Visibility validateVisibility(String visibility) {
        return Optional.of(visibility)
                .map(Visibility::valueOf)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST,
                        "Некорректное значение категории друзей"));
    }

    private void validateIfFriendExistsInFriendList(Visibility visibility, String user, String friend) {
        if (!dataService.friendExists(visibility, user, friend)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Такого пользователя нет в категории друзей");
        }
    }

    private void validateIfFriendNotExistInFriendList(Visibility visibility, String user, String friend) {
        if (dataService.friendExists(visibility, user, friend)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Пользователь уже добавлен в категорию друзей");
        }
    }
}
