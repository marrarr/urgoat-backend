package demo.security.repository;

import demo.security.model.PendingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repozytorium JPA dla encji PendingUser, obsługujące operacje na użytkownikach oczekujących na weryfikację.
 */
public interface PendingUserRepository extends JpaRepository<PendingUser, Long> {

    /**
     * Wyszukuje użytkownika oczekującego na podstawie adresu e-mail.
     *
     * @param email Adres e-mail użytkownika
     * @return Obiekt Optional zawierający znalezionego użytkownika lub pusty, jeśli nie znaleziono
     */
    Optional<PendingUser> findByEmail(String email);
}