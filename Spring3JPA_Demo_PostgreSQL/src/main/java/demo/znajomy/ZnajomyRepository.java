package demo.znajomy;

import java.util.List;

import demo.uzytkownik.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ZnajomyRepository extends JpaRepository<Znajomy, Long>{
    //Szukanie znajomych danego użytkownika
    // Znajomi, których użytkownik dodał
    List<Znajomy> findByUzytkownik(Uzytkownik uzytkownik);

    // Znajomi, którzy dodali użytkownika (druga strona)
    List<Znajomy> findByUzytkownik2(Uzytkownik uzytkownik);

    // Wszystkie znajomości, w których użytkownik uczestniczy
    @Query("SELECT z FROM Znajomy z WHERE z.uzytkownik = :user OR z.uzytkownik2 = :user")
    List<Znajomy> findAllByUser(@Param("user") Uzytkownik user);

}
