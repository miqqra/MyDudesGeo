package mydudesgeo.repository;

import mydudesgeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);

    User getUserByName(String name);

    @Modifying
    @Query("""
                update User set freeze = :freeze where name = :name
            """)
    void changeFreezeToggle(String name, Boolean freeze);

}
