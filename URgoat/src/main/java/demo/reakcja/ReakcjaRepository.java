package demo.reakcja;

import demo.komentarz.Komentarz;
import demo.post.Post;
import demo.uzytkownik.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repozytorium JPA dla encji Reakcja.
 * Udostępnia metody do wyszukiwania reakcji na podstawie użytkownika, posta, komentarza lub ich identyfikatorów.
 */
public interface ReakcjaRepository extends JpaRepository<Reakcja, Long> {

    /**
     * Wyszukuje wszystkie reakcje utworzone przez danego użytkownika.
     *
     * @param uzytkownik użytkownik, którego reakcje mają być wyszukane
     * @return lista reakcji utworzonych przez podanego użytkownika
     */
    List<Reakcja> findByUzytkownik(Uzytkownik uzytkownik);

    /**
     * Wyszukuje wszystkie reakcje powiązane z danym postem.
     *
     * @param post post, którego reakcje mają być wyszukane
     * @return lista reakcji powiązanych z podanym postem
     */
    List<Reakcja> findByPost(Post post);

    /**
     * Wyszukuje wszystkie reakcje powiązane z danym komentarzem.
     *
     * @param komentarz komentarz, którego reakcje mają być wyszukane
     * @return lista reakcji powiązanych z podanym komentarzem
     */
    List<Reakcja> findByKomentarz(Komentarz komentarz);

    /**
     * Wyszukuje wszystkie reakcje powiązane z postem o podanym identyfikatorze.
     *
     * @param postID identyfikator posta
     * @return lista reakcji powiązanych z postem o podanym identyfikatorze
     */
    List<Reakcja> findByPostPostID(Long postID);

    /**
     * Zlicza reakcje określonego typu dla danego posta.
     *
     * @param post post, dla którego zliczane są reakcje
     * @param i typ reakcji (np. 1 dla polubienia)
     * @return liczba reakcji danego typu dla podanego posta
     */
    Object countByPostAndReakcja(Post post, int i);

    /**
     * Wyszukuje reakcje na posty utworzone przez danego użytkownika.
     * Zwraca tylko reakcje, które dotyczą postów (bez komentarzy).
     *
     * @param uzytkownik użytkownik, którego reakcje mają być wyszukane
     * @return lista reakcji na posty utworzonych przez podanego użytkownika
     */
    @Query("SELECT r FROM Reakcja r " +
            "LEFT JOIN FETCH r.post " +
            "WHERE r.uzytkownik = :uzytkownik " +
            "AND r.post IS NOT NULL " +
            "AND r.komentarz IS NULL")
    List<Reakcja> findReakcjeNaPostyByUzytkownik(@Param("uzytkownik") Uzytkownik uzytkownik);

    /**
     * Wyszukuje reakcje na komentarze utworzone przez danego użytkownika.
     * Zwraca tylko reakcje, które dotyczą komentarzy (bez postów).
     *
     * @param uzytkownik użytkownik, którego reakcje mają być wyszukane
     * @return lista reakcji na komentarze utworzonych przez podanego użytkownika
     */
    @Query("SELECT r FROM Reakcja r " +
            "LEFT JOIN FETCH r.komentarz " +
            "WHERE r.uzytkownik = :uzytkownik " +
            "AND r.komentarz IS NOT NULL " +
            "AND r.post IS NULL")
    List<Reakcja> findReakcjeNaKomentarzeByUzytkownik(@Param("uzytkownik") Uzytkownik uzytkownik);

    /**
     * Sprawdza, czy użytkownik już zareagował na dany post.
     *
     * @param uzytkownikID identyfikator użytkownika
     * @param postID identyfikator posta
     * @return obiekt Optional zawierający reakcję, jeśli istnieje, lub pusty, jeśli nie
     */
    Optional<Reakcja> findByUzytkownik_UzytkownikIDAndPost_PostID(int uzytkownikID, int postID);

    /**
     * Sprawdza, czy użytkownik już zareagował na dany komentarz.
     *
     * @param uzytkownikID identyfikator użytkownika
     * @param komentarzID identyfikator komentarza
     * @return obiekt Optional zawierający reakcję, jeśli istnieje, lub pusty, jeśli nie
     */
    Optional<Reakcja> findByUzytkownik_UzytkownikIDAndKomentarz_KomentarzID(int uzytkownikID, int komentarzID);
}