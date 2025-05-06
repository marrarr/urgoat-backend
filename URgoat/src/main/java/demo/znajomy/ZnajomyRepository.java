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
    @Query("SELECT z FROM Znajomy z WHERE z.uzytkownik = :user")
    List<Znajomy> findAllByUser(@Param("user") Uzytkownik user);

    @Query("SELECT COUNT(*) FROM Znajomy z WHERE uzytkownik = :user AND uzytkownik2 = :user2")
    int sprawdz_znajomych(@Param("user") Uzytkownik user,@Param("user2") Uzytkownik user2);


}
