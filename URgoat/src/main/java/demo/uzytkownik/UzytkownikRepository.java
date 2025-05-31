package demo.uzytkownik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repozytorium JPA dla encji Uzytkownik, obsługujące operacje na profilach użytkowników.
 */
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long> {

    /**
     * Wyszukuje użytkowników na podstawie imienia.
     *
     * @param imie Imię użytkownika
     * @return Lista użytkowników o podanym imieniu
     */
    List<Uzytkownik> findByImie(String imie);

    /**
     * Wyszukuje użytkowników na podstawie nazwiska.
     *
     * @param nazwisko Nazwisko użytkownika
     * @return Lista użytkowników o podanym nazwisku
     */
    List<Uzytkownik> findByNazwisko(String nazwisko);

    /**
     * Wyszukuje użytkowników na podstawie imienia i nazwiska.
     *
     * @param imie     Imię użytkownika
     * @param nazwisko Nazwisko użytkownika
     * @return Lista użytkowników o podanym imieniu i nazwisku
     */
    List<Uzytkownik> findByImieAndNazwisko(String imie, String nazwisko);

    /**
     * Wyszukuje użytkowników na podstawie pseudonimu.
     *
     * @param pseudonim Pseudonim użytkownika
     * @return Lista użytkowników o podanym pseudonimie
     */
    List<Uzytkownik> findByPseudonim(String pseudonim);

    /**
     * Wyszukuje pierwszego użytkownika na podstawie pseudonimu.
     *
     * @param pseudonim Pseudonim użytkownika
     * @return Pierwszy znaleziony użytkownik o podanym pseudonimie
     */
    Uzytkownik findFirstByPseudonim(String pseudonim);

    /**
     * Wyszukuje pierwszego użytkownika na podstawie identyfikatora.
     *
     * @param uzytkownikIDLink Identyfikator użytkownika
     * @return Pierwszy znaleziony użytkownik o podanym identyfikatorze
     */
    Uzytkownik findFirstByUzytkownikID(Long uzytkownikIDLink);

    /**
     * Wyszukuje wszystkich użytkowników z wyjątkiem użytkownika o podanym identyfikatorze.
     *
     * @param id Identyfikator użytkownika do wykluczenia
     * @return Lista użytkowników z wyjątkiem użytkownika o podanym identyfikatorze
     */
    @Query("SELECT u FROM Uzytkownik u WHERE u.uzytkownikID <> :id")
    List<Uzytkownik> findAllExceptById(@Param("id") int id);

    /**
     * Wyszukuje użytkownika na podstawie nazwy użytkownika powiązanego konta.
     *
     * @param username Nazwa użytkownika konta
     * @return Obiekt Optional zawierający znalezionego użytkownika lub pusty, jeśli nie znaleziono
     */
    Optional<Uzytkownik> findByUserAccount_Username(String username);

    /**
     * Wyszukuje użytkownika na podstawie adresu e-mail.
     *
     * @param email Adres e-mail użytkownika
     * @return Znaleziony użytkownik lub null, jeśli nie znaleziono
     */
    Uzytkownik findByEmail(String email);
}