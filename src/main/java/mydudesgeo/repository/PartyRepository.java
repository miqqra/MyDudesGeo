package mydudesgeo.repository;

import java.util.List;
import mydudesgeo.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
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
    List<Party> getPartiesAround(Double latitude, Double longitude, Double radius);

    boolean existsByLink(String link);
}
