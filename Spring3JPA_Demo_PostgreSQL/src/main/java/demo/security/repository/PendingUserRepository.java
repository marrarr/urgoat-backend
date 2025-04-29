package demo.security.repository;

import demo.security.model.PendingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PendingUserRepository extends JpaRepository<PendingUser, Long> {
    Optional<PendingUser> findByEmail(String email);
}