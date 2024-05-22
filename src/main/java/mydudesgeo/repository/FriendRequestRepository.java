package mydudesgeo.repository;

import java.util.List;
import mydudesgeo.data.FriendRequestStatus;
import mydudesgeo.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Modifying
    @Query("""
            update FriendRequest set status = :status where 
            requestFrom.nickname = :requestFrom and requestTo.nickname = :requestTo
            """)
    void setStatus(String requestFrom, String requestTo, FriendRequestStatus status);

    @Query("""
            select f from FriendRequest f
            where f.requestTo.nickname = :requestTo and f.status = :status
            """)
    List<FriendRequest> getFriendRequestByRequestTo(String requestTo, FriendRequestStatus status);
}
