package mydudesgeo.repository;

import mydudesgeo.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findAllByCreator(String creator);

    //todo after fk add index and modify request instead of p.creator write p.creator.name
    @Query("""
            select p from Party p
            where upper(p.name) like concat('%', upper(:search), '%') or
            upper(p.creator) like concat('%', upper(:search), '%')
            """)
    List<Party> searchPartiesByCreatorOrName(String search);

    @Query("""
            select p from Party p
            where ST_DWithin(p.location, ST_MakePoint(:longitude, :latitude), :radius)
            """)
    List<Party> getPartiesAround(Double latitude, Double longitude, Double radius);
}
