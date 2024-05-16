package mydudesgeo.repository;

import java.util.Collection;
import java.util.List;
import mydudesgeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByNickname(String nickname);

    List<User> findAllByTelegramNickIn(Collection<String> telegramNick);

    @Query("""
            select u.freezeLocation from User u where u.nickname = :nickname
            """)
    Boolean findFreezeFlagByNickname(String nickname);

    @Modifying
    @Query("""
            update User set photo = null where nickname = :nickname
            """)
    void deletePhotoByNickname(String nickname);

    @Modifying
    @Query("""
            update User set photo = :photo where nickname = :nickname
            """)
    void updatePhoto(byte[] photo, String nickname);

    @Modifying
    @Query("""
                update User set freezeLocation = :freezeLocation where nickname = :nickname
            """)
    void changeFreezeToggle(String nickname, Boolean freezeLocation);

    @Modifying
    @Query("""
            update User set tgChatId = :tgChatId where nickname = :nickname
            """)
    void setTgChatId(String nickname, Long tgChatId);

}
