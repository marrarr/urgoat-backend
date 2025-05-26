package demo.reakcja;

import java.util.List;

import demo.komentarz.Komentarz;
import demo.post.Post;
import demo.uzytkownik.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT r FROM Reakcja r " +
            "LEFT JOIN FETCH r.post " +
            "WHERE r.uzytkownik = :uzytkownik " +
            "AND r.post IS NOT NULL " +
            "AND r.komentarz IS NULL")
    List<Reakcja> findReakcjeNaPostyByUzytkownik(@Param("uzytkownik") Uzytkownik uzytkownik);

    @Query("SELECT r FROM Reakcja r " +
            "LEFT JOIN FETCH r.komentarz " +
            "WHERE r.uzytkownik = :uzytkownik " +
            "AND r.komentarz IS NOT NULL " +
            "AND r.post IS NULL")
    List<Reakcja> findReakcjeNaKomentarzeByUzytkownik(@Param("uzytkownik") Uzytkownik uzytkownik);
}
