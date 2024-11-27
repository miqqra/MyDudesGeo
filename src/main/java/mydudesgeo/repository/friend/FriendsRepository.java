package mydudesgeo.repository.friend;

import mydudesgeo.entity.friends.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    @Query("""
            select f from Friends f
            join PartyVisit pv on pv.party.id = :partyId and pv.user.id = f.friend.id
            where f.person.id = :userId
            """)
    List<Friends> getFriendsWhoVisitedTheParty(Long userId, Long partyId);

    boolean existsByFriendNicknameAndPersonNickname(String friend, String person);

    List<Friends> findByPersonNickname(String person);

    void deleteByPersonNicknameAndFriendNickname(String person, String friend);
}
