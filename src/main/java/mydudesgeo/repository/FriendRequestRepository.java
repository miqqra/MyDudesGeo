package mydudesgeo.repository;

import mydudesgeo.data.FriendRequestStatus;
import mydudesgeo.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    void deleteFriendRequestByRequestFromNicknameAndRequestToNickname(String requestFrom, String requestTo);

    @Modifying
    @Query("""
            update FriendRequest set status = :status where requestFrom = :requestFrom and requestTo = :requestTo
            """)
    void setStatus(String requestFrom, String requestTo, FriendRequestStatus status);
}
