package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.data.FriendRequestStatus;
import mydudesgeo.mapper.FriendRequestMapper;
import mydudesgeo.model.FriendRequestModel;
import mydudesgeo.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRequestDataService {

    private final FriendRequestRepository repository;

    private final FriendRequestMapper mapper;

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
