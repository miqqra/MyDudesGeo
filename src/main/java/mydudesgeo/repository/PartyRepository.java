package mydudesgeo.repository;

import java.util.List;
import mydudesgeo.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findByCreatorNickname(String creator);

    @Query("""
            select p from Party p
            where upper(p.name) like concat('%', upper(:search), '%') or
            upper(p.creator.nickname) like concat('%', upper(:search), '%')
            """)
    List<Party> searchPartiesByCreatorOrName(String search);

    @Query("""
            select p from Party p
            where ST_DWithin(
            ST_MakePoint(p.latitude, p.longitude),
            ST_MakePoint(:latitude, :longitude),
            :radius * 1000, true) = true
            """)
    List<Party> getPartiesAround(Float latitude, Float longitude, Double radius);

    boolean existsByLinkDobro(String linkDobro);

    @Modifying
    @Query("""
            update Party set photo = null where id = :id
            """)
    void deletePhotoByNickname(Long id);

    @Modifying
    @Query("""
            update User set photo = :photo where id = :id
            """)
    void updatePhoto(byte[] photo, Long id);
}
