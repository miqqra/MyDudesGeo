package mydudesgeo.repository.friend;

import mydudesgeo.entity.friends.FriendTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendTemplateRepository extends JpaRepository<FriendTemplate, Long> {

    boolean existsByFriendAndPerson(String friend, String person);

    List<FriendTemplate> findAllByPerson(String person);

    void deleteByPersonAndFriend(String person, String friend);
}
