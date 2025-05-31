package demo.reakcja;

import demo.komentarz.Komentarz;
import demo.post.Post;
import demo.uzytkownik.Uzytkownik;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Encja reprezentująca reakcję w systemie.
 * Przechowuje informacje o użytkowniku, poście lub komentarzu, na który jest reakcja, oraz typie reakcji.
 */
@Entity
@Table(name = "reakcja")
@Getter
@Setter
public class Reakcja {

    /**
     * Unikalny identyfikator reakcji, generowany automatycznie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reakcjaID;

    /**
     * Typ reakcji, reprezentowany jako liczba całkowita (np. 1 dla polubienia, 2 dla innej reakcji).
     */
    private int reakcja;

    /**
     * Użytkownik, który wykonał reakcję.
     * Relacja wiele-do-jednego z encją Uzytkownik.
     */
    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    /**
     * Post, na który jest reakcja (może być null, jeśli reakcja dotyczy komentarza).
     * Relacja wiele-do-jednego z encją Post.
     */
    @ManyToOne
    @JoinColumn(name = "postID", nullable = true)
    private Post post;

    /**
     * Komentarz, na który jest reakcja (może być null, jeśli reakcja dotyczy posta).
     * Relacja wiele-do-jednego z encją Komentarz.
     */
    @ManyToOne
    @JoinColumn(name = "komentarzID", nullable = true)
    private Komentarz komentarz;

    /**
     * Domyślny konstruktor wymagany przez JPA.
     */
    public Reakcja() {}

    /**
     * Konstruktor inicjalizujący reakcję z użytkownikiem, komentarzem, postem i typem reakcji.
     *
     * @param uzytkownik użytkownik, który wykonał reakcję
     * @param komentarz komentarz, na który jest reakcja (może być null)
     * @param post post, na który jest reakcja (może być null)
     * @param reakcja typ reakcji jako liczba całkowita
     */
    public Reakcja(Uzytkownik uzytkownik, Komentarz komentarz, Post post, int reakcja) {
        this.uzytkownik = uzytkownik;
        this.komentarz = komentarz;
        this.post = post;
        this.reakcja = reakcja;
    }
}