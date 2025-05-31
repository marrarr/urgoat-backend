package demo.security.repository;

import demo.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repozytorium JPA dla encji User, obsługujące operacje na użytkownikach zarejestrowanych w systemie.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Wyszukuje użytkownika na podstawie adresu e-mail.
     *
     * @param email Adres e-mail użytkownika
     * @return Obiekt Optional zawierający znalezionego użytkownika lub pusty, jeśli nie znaleziono
     */
    Optional<User> findByEmail(String email);

    /**
     * Wyszukuje użytkownika na podstawie nazwy użytkownika.
     *
     * @param username Nazwa użytkownika
     * @return Obiekt Optional zawierający znalezionego użytkownika lub pusty, jeśli nie znaleziono
     */
    Optional<User> findByUsername(String username);
}