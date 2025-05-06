package demo.reakcja;

import java.util.List;

import demo.komentarz.Komentarz;
import demo.post.Post;
import demo.uzytkownik.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReakcjaRepository extends JpaRepository<Reakcja, Long> {
    //Szukanie reakcji użytkownika lub przypisanych do posta/komentarza
    // Reakcje użytkownika
    List<Reakcja> findByUzytkownik(Uzytkownik uzytkownik);

    // Reakcje do konkretnego posta
    List<Reakcja> findByPost(Post post);

    // Reakcje do komentarza
    List<Reakcja> findByKomentarz(Komentarz komentarz);

    List<Reakcja> findByPostPostID(Long postID);

    Object countByPostAndReakcja(Post post, int i);
}
