package demo.post;

import demo.uzytkownik.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repozytorium JPA dla encji Post.
 * Udostępnia metody do wyszukiwania postów na podstawie użytkownika, treści lub identyfikatora.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Wyszukuje wszystkie posty utworzone przez danego użytkownika.
     *
     * @param uzytkownik użytkownik, którego posty mają być wyszukane
     * @return lista postów utworzonych przez podanego użytkownika
     */
    List<Post> findByUzytkownik(Uzytkownik uzytkownik);

    /**
     * Wyszukuje posty zawierające podaną frazę w treści, ignorując wielkość liter.
     *
     * @param tresc fraza do wyszukania w treści postów
     * @return lista postów zawierających podaną frazę
     */
    List<Post> findByTrescContainingIgnoreCase(String tresc);

    /**
     * Wyszukuje post o podanym identyfikatorze.
     *
     * @param postID identyfikator posta
     * @return post o podanym identyfikatorze lub null, jeśli nie istnieje
     */
    Post findByPostID(int postID);
}