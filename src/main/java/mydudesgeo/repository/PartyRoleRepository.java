package mydudesgeo.repository;

import mydudesgeo.entity.PartyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRoleRepository extends JpaRepository<PartyRole, Long> {

    @Query("""
            select p from PartyRole p where p.party.id = :partyId
            """)
    List<PartyRole> findByParty(Long partyId);
}
