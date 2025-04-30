package demo.czat;

import java.util.List;
import java.util.Optional;


import demo.uzytkownik.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CzatRepository extends JpaRepository<Czat, Long>{
    // Szukanie czatów danego użytkownika
    //Wszystkie czaty, w których użytkownik uczestniczy
    List<Czat> findByUzytkownicyContains(Uzytkownik uzytkownik);

    @Query("SELECT c FROM Czat c WHERE :u1 MEMBER OF c.uzytkownicy AND :u2 MEMBER OF c.uzytkownicy")
    Optional<Czat> findByUzytkownicyContainsBoth(@Param("u1") Uzytkownik u1, @Param("u2") Uzytkownik u2);
}
