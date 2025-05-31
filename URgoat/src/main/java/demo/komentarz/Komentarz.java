package demo.komentarz;

import demo.post.Post;
import demo.reakcja.Reakcja;
import demo.uzytkownik.Uzytkownik;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Encja reprezentująca komentarz w systemie.
 * Przechowuje informacje o treści komentarza, powiązanym poście, autorze, zdjęciu i reakcjach.
 */
@Entity
@Table(name = "komentarz")
@Getter
@Setter
@NoArgsConstructor
public class Komentarz {

    /**
     * Unikalny identyfikator komentarza, generowany automatycznie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int komentarzID;

    /**
     * Treść komentarza, przechowywana jako tekst.
     */
    @Column(columnDefinition = "TEXT")
    private String tresc;

    /**
     * Zdjęcie dołączone do komentarza, przechowywane jako tablica bajtów.
     * Wczytywane leniwie (lazy loading).
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] zdjecie;

    /**
     * Post, do którego należy komentarz.
     * Relacja wiele-do-jednego z encją Post.
     */
    @ManyToOne
    @JoinColumn(name = "postID")
    private Post post;

    /**
     * Użytkownik, który utworzył komentarz.
     * Relacja wiele-do-jednego z encją Uzytkownik.
     */
    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    /**
     * Lista reakcji na komentarz.
     * Relacja jeden-do-wielu z encją Reakcja, z kaskadowym usuwaniem.
     */
    @OneToMany(mappedBy = "komentarz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reakcja> reakcje;

    /**
     * Konstruktor tworzący komentarz bez zdjęcia.
     *
     * @param post post, do którego należy komentarz
     * @param uzytkownik użytkownik, który utworzył komentarz
     * @param tresc treść komentarza
     */
    public Komentarz(Post post, Uzytkownik uzytkownik, String tresc) {
        this.post = post;
        this.uzytkownik = uzytkownik;
        this.tresc = tresc;
    }

    /**
     * Konstruktor tworzący komentarz ze zdjęciem.
     *
     * @param post post, do którego należy komentarz
     * @param uzytkownik użytkownik, który utworzył komentarz
     * @param tresc treść komentarza
     * @param zdjecie zdjęcie dołączone do komentarza jako tablica bajtów
     */
    public Komentarz(Post post, Uzytkownik uzytkownik, String tresc, byte[] zdjecie) {
        this.post = post;
        this.uzytkownik = uzytkownik;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
    }
}