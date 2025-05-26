package demo.wiadomosc;

import java.util.List;

import demo.czat.Czat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WiadomoscRepository extends JpaRepository<Wiadomosc, Long>{
    //Szukanie wiadomości w konkretnym czacie
    // Wszystkie wiadomości z danego czatu
    List<Wiadomosc> findByCzat(Czat czat);

    // Wiadomości zawierające określony fragment tekstu
    List<Wiadomosc> findByTrescContainingIgnoreCase(String tresc);

    @EntityGraph(attributePaths = {"uzytkownik"}) // This ensures sender is loaded
    List<Wiadomosc> findByCzatOrderByWiadomoscIDAsc(Czat czat);

}
