package mydudesgeo.repository;

import mydudesgeo.entity.PartyVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyVisitRepository extends JpaRepository<PartyVisit, Long> {

    boolean existsByUser_IdAndParty_Id(Long user_id, Long party_id);

    void deleteByUser_IdAndParty_Id(Long user_id, Long party_id);
}
