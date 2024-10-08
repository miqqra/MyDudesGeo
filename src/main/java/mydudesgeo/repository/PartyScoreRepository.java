package mydudesgeo.repository;

import mydudesgeo.entity.PartyScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyScoreRepository extends JpaRepository<PartyScore, Long> {
}
