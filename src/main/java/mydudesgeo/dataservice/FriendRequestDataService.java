package mydudesgeo.dataservice;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.data.FriendRequestStatus;
import mydudesgeo.mapper.FriendRequestMapper;
import mydudesgeo.model.FriendRequestModel;
import mydudesgeo.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRequestDataService {

    private final FriendRequestRepository repository;

    private final FriendRequestMapper mapper;

    public List<FriendRequestModel> getRequests(String currentUser) {
        return Optional.of(currentUser)
                .map(v -> repository.getFriendRequestByRequestTo(v, FriendRequestStatus.NOT_VIEWED))
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toModel)
                .toList();
    }

    public FriendRequestModel sendRequest(FriendRequestModel model) {
        return Optional.of(model)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    public void acceptRequest(String from, String to) {
        repository.setStatus(from, to, FriendRequestStatus.ACCEPTED);
    }

    public void rejectRequest(String from, String to) {

        repository.setStatus(from, to, FriendRequestStatus.REJECTED);
    }
}
