package mydudesgeo.repository.friend;

import mydudesgeo.entity.friends.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    boolean existsByFriendNicknameAndPersonNickname(String friend, String person);

    List<Friends> findByPersonNickname(String person);

    void deleteByPersonNicknameAndFriendNickname(String person, String friend);
}
