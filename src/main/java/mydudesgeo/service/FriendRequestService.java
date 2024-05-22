package mydudesgeo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Visibility;
import mydudesgeo.dataservice.FriendRequestDataService;
import mydudesgeo.dataservice.FriendsDataService;
import mydudesgeo.dto.friend.FriendRequestDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.FriendRequestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestDataService friendRequestDataService;
    private final FriendsDataService friendsDataService;

    private final FriendRequestMapper mapper;

    public List<FriendRequestDto> getRequests() {
        String currentUser = UserCredentialsService.getCurrentUser();

        return Optional.of(currentUser)
                .map(friendRequestDataService::getRequests)
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toDto)
                .toList();
    }

    public void sendRequest(String to) {
        String currentUser = UserCredentialsService.getCurrentUser();

        Optional.ofNullable(to)
                .map(v -> mapper.toModel(currentUser, v))
                .map(friendRequestDataService::sendRequest)
                .orElseThrow(() -> ClientException.of(HttpStatus.BAD_REQUEST,
                        "Не удалось отправить запрос дружбы"));
    }

    public void acceptRequest(String from) {
        String to = UserCredentialsService.getCurrentUser();

        friendRequestDataService.acceptRequest(from, to);
        friendsDataService.addFriend(Visibility.FRIENDS, from, to);
        //todo добавление в друзья должно быть с обоих сторон, с близкими друзьями не так
    }

    public void rejectRequest(String from) {
        String to = UserCredentialsService.getCurrentUser();

        friendRequestDataService.rejectRequest(from, to);
    }
}
