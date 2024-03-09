package mydudesgeo.repository;

import mydudesgeo.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserLocation, Long> {

    //todo user location repository
    boolean existsByName(String name);

    UserLocation getUserByName(String name);

    @Modifying
    @Query("""
                update UserLocation set freeze = :freeze where name = :name
            """)
    void changeFreezeToggle(String name, Boolean freeze);

}
