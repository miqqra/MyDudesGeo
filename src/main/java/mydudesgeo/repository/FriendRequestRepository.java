package mydudesgeo.repository;

import mydudesgeo.data.FriendRequestStatus;
import mydudesgeo.entity.FriendRequest;
import mydudesgeo.service.FriendRequestService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    void deleteFriendRequestByRequestFromAndRequestTo(String requestFrom, String requestTo);

    @Query("""
            update FriendRequest set status = :status where requestFrom = :requestFrom and requestTo = :requestTo
            """)
    void setStatus(String requestFrom, String requestTo, FriendRequestStatus status);
}
