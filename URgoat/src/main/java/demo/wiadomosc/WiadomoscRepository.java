package demo.wiadomosc;

import demo.czat.Czat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repozytorium JPA dla encji Wiadomosc, obsługujące operacje na wiadomościach w systemie.
 */
public interface WiadomoscRepository extends JpaRepository<Wiadomosc, Long> {

    /**
     * Wyszukuje wszystkie wiadomości powiązane z określonym czatem.
     *
     * @param czat Czat, dla którego wyszukiwane są wiadomości
     * @return Lista wiadomości powiązanych z podanym czatem
     */
    List<Wiadomosc> findByCzat(Czat czat);

    /**
     * Wyszukuje wiadomości zawierające określony fragment tekstu, ignorując wielkość liter.
     *
     * @param tresc Fragment tekstu do wyszukania
     * @return Lista wiadomości zawierających podany fragment tekstu
     */
    List<Wiadomosc> findByTrescContainingIgnoreCase(String tresc);

    /**
     * Wyszukuje wszystkie wiadomości powiązane z określonym czatem, posortowane rosnąco według identyfikatora wiadomości, z załadowanym użytkownikiem.
     *
     * @param czat Czat, dla którego wyszukiwane są wiadomości
     * @return Lista wiadomości powiązanych z podanym czatem, posortowana rosnąco według identyfikatora
     */
    @EntityGraph(attributePaths = {"uzytkownik"})
    List<Wiadomosc> findByCzatOrderByWiadomoscIDAsc(Czat czat);
}