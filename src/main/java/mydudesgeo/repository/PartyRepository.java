package mydudesgeo.repository;

import mydudesgeo.entity.Party;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findAllByCreator(String creator);


    //todo go to postgis
    //    @Query("""
//        select p from Party p where
//        point_distance(
//        point((p.location ->> 'x') || ', ' || (p.location ->> 'y')),
//        point(:#{#location.x} || ', ' || :#{#location.y})
//        ) < :radius * 1000
//        """)

    @Query("""
            select p from Party p where
            SQRT(cast(
                    POW(cast(jsonb_extract_path_text(p.location, 'x') as float) - :#{#location.x}, 2) +
                    POW(cast(jsonb_extract_path_text(p.location, 'x') as float) - :#{#location.y}, 2)
                as float)
            )
            < :radius * 1000
            """)
    List<Party> getPartiesAround(Integer radius, Point location);
}
