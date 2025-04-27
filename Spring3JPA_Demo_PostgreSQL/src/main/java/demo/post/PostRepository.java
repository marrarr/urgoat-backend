package demo.post;

import java.util.List;

import demo.uzytkownik.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
    //Szukanie postów użytkownika lub po treści
    // Wszystkie posty użytkownika
    List<Post> findByUzytkownik(Uzytkownik uzytkownik);

    // Posty zawierające frazę
    List<Post> findByTrescContainingIgnoreCase(String tresc);
}
