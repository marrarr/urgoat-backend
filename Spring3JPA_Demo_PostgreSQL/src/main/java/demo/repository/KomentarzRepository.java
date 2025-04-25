package demo.repository;

import java.util.List;

import demo.model.Komentarz;
import demo.model.Post;
import demo.model.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KomentarzRepository extends JpaRepository<Komentarz, Long>{
    //Szukanie komentarzy do danego posta
    // Wszystkie komentarze do konkretnego posta
    List<Komentarz> findByPost(Post post);

    // Komentarze użytkownika
    List<Komentarz> findByUzytkownik(Uzytkownik uzytkownik);

    List<Komentarz> findByPostPostID(Long aLong);
}
