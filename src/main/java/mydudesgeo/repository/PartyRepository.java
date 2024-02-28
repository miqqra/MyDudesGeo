package mydudesgeo.repository;

import mydudesgeo.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findAllByCreator(String creator);

    @Query("""
            select p from Party p
            where ST_DWithin(p.location, ST_MakePoint(:longitude, :latitude), :radius)
            """)
    List<Party> getPartiesAround(Double latitude, Double longitude, Double radius);
}
