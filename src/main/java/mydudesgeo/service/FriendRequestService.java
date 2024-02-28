package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Visibility;
import mydudesgeo.dataservice.FriendRequestDataService;
import mydudesgeo.dataservice.FriendsDataService;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.FriendRequestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestDataService friendRequestDataService;
    private final FriendsDataService friendsDataService;

    private final FriendRequestMapper mapper;

    public void sendRequest(String to) {
        Optional.ofNullable(to)
                .map(mapper::toModel)
                .map(friendRequestDataService::sendRequest)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST,
                        "Не удалось отправить запрос дружбы"));
    }

    public void acceptRequest(String from) {
        String to = "changeIt"; //todo change for current operator

        //todo validate
        friendRequestDataService.acceptRequest(from, to);
        friendsDataService.addFriend(Visibility.FRIENDS, from, to);
        //todo добавление в друзья должно быть с обоих сторон, с близкими друзьями не так
    }

    public void rejectRequest(String from) {
        String to = "changeIt"; //todo change for current operator

        //todo validate
        friendRequestDataService.rejectRequest(from, to);
    }
}
