package mydudesgeo.repository;

import mydudesgeo.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    UserLocation findByUserNickname(String user_nickname);

    boolean existsByUserNickname(String user_nickname);

}
