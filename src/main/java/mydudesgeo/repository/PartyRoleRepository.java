package mydudesgeo.repository;

import mydudesgeo.entity.PartyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRoleRepository extends JpaRepository<PartyRole, Long> {

    List<PartyRole> findByPartyId(Long partyId);
}
