package demo.repository;

import java.util.List;

import demo.model.Czat;
import demo.model.Wiadomosc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WiadomoscRepository extends JpaRepository<Wiadomosc, Long>{
    //Szukanie wiadomości w konkretnym czacie
    // Wszystkie wiadomości z danego czatu
    List<Wiadomosc> findByCzat(Czat czat);

    // Wiadomości zawierające określony fragment tekstu
    List<Wiadomosc> findByTrescContainingIgnoreCase(String tresc);
}
