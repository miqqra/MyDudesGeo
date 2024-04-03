package mydudesgeo.repository;

import mydudesgeo.entity.PartyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyCategoryRepository extends JpaRepository<PartyCategory, Long> {

    boolean existsById(Long id);

    boolean existsByCategory(String category);

    PartyCategory findByCategory(String category);
}
