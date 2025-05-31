package demo.czat;

import demo.uzytkownik.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repozytorium JPA dla encji Czat.
 * Udostępnia metody do wyszukiwania czatów na podstawie użytkowników uczestniczących w czacie.
 */
public interface CzatRepository extends JpaRepository<Czat, Long> {

    /**
     * Wyszukuje wszystkie czaty, w których uczestniczy podany użytkownik.
     *
     * @param uzytkownik użytkownik, którego czaty mają być wyszukane
     * @return lista czatów, w których uczestniczy podany użytkownik
     */
    List<Czat> findByUzytkownicyContains(Uzytkownik uzytkownik);

    /**
     * Wyszukuje czat, w którym uczestniczą obaj podani użytkownicy.
     *
     * @param u1 pierwszy użytkownik
     * @param u2 drugi użytkownik
     * @return obiekt Optional zawierający czat, jeśli istnieje, lub pusty, jeśli nie znaleziono
     */
    @Query("SELECT c FROM Czat c WHERE :u1 MEMBER OF c.uzytkownicy AND :u2 MEMBER OF c.uzytkownicy")
    Optional<Czat> findByUzytkownicyContainsBoth(@Param("u1") Uzytkownik u1, @Param("u2") Uzytkownik u2);
}