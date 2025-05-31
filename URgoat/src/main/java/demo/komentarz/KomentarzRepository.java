package demo.komentarz;

import demo.post.Post;
import demo.uzytkownik.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repozytorium JPA dla encji Komentarz.
 * Udostępnia metody do wyszukiwania komentarzy na podstawie posta, użytkownika lub identyfikatora.
 */
public interface KomentarzRepository extends JpaRepository<Komentarz, Long> {

    /**
     * Wyszukuje wszystkie komentarze powiązane z danym postem.
     *
     * @param post post, którego komentarze mają być wyszukane
     * @return lista komentarzy powiązanych z podanym postem
     */
    List<Komentarz> findByPost(Post post);

    /**
     * Wyszukuje wszystkie komentarze utworzone przez danego użytkownika.
     *
     * @param uzytkownik użytkownik, którego komentarze mają być wyszukane
     * @return lista komentarzy utworzonych przez podanego użytkownika
     */
    List<Komentarz> findByUzytkownik(Uzytkownik uzytkownik);

    /**
     * Wyszukuje wszystkie komentarze powiązane z postem o podanym identyfikatorze.
     *
     * @param aLong identyfikator posta
     * @return lista komentarzy powiązanych z postem o podanym identyfikatorze
     */
    List<Komentarz> findByPostPostID(Long aLong);

    /**
     * Wyszukuje komentarz o podanym identyfikatorze.
     *
     * @param komentarzID identyfikator komentarza
     * @return komentarz o podanym identyfikatorze lub null, jeśli nie istnieje
     */
    Komentarz findByKomentarzID(Long komentarzID);
}