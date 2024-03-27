package mydudesgeo.repository.security;

import jakarta.validation.constraints.Size;
import mydudesgeo.entity.security.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    UserCredentials findByUsername(@Size(min = 2, message = "Не меньше 5 знаков") String username);

    boolean existsByUsername(@Size(min = 2, message = "Не меньше 5 знаков") String username);
}
