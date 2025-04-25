package demo.repository;

import java.util.List;

import demo.model.Czat;
import demo.model.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CzatRepository extends JpaRepository<Czat, Long>{
    // Szukanie czatów danego użytkownika
    //Wszystkie czaty, w których użytkownik uczestniczy
    List<Czat> findByUzytkownicyContains(Uzytkownik uzytkownik);
}
