package mydudesgeo.repository.friend;

import mydudesgeo.entity.friends.CloseFriends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloseFriendsRepository extends JpaRepository<CloseFriends, Long> {

    boolean existsByFriendAndPerson(String friend, String person);

    List<CloseFriends> findAllByPerson(String person);

    void deleteByPersonAndFriend(String person, String friend);
}
