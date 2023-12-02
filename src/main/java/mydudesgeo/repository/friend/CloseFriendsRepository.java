package mydudesgeo.repository.friend;

import org.springframework.stereotype.Repository;

@Repository
public interface CloseFriendsRepository extends FriendTemplateRepository {

    boolean existsByPersonAndFriend(String person, String friend);
}
