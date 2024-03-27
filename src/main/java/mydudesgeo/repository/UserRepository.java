package mydudesgeo.repository;

import mydudesgeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByNickname(String nickname);

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

}
