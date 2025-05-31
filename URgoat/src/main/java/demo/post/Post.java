package demo.post;

import demo.komentarz.Komentarz;
import demo.uzytkownik.Uzytkownik;
import demo.reakcja.Reakcja;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Encja reprezentująca post w systemie.
 * Przechowuje informacje o treści posta, użytkowniku, zdjęciu, komentarzach i reakcjach.
 */
@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {

    /**
     * Unikalny identyfikator posta, generowany automatycznie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;

    /**
     * Treść posta, przechowywana jako tekst.
     */
    @Column(columnDefinition = "TEXT")
    private String tresc;

    /**
     * Zdjęcie dołączone do posta, przechowywane jako tablica bajtów.
     * Wczytywane leniwie (lazy loading).
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] zdjecie;

    /**
     * Użytkownik, który utworzył post.
     * Relacja wiele-do-jednego z encją Uzytkownik.
     */
    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    /**
     * Lista komentarzy powiązanych z postem.
     * Relacja jeden-do-wielu z encją Komentarz, z kaskadowym usuwaniem.
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Komentarz> komentarze;

    /**
     * Lista reakcji powiązanych z postem.
     * Relacja jeden-do-wielu z encją Reakcja, z kaskadowym usuwaniem.
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reakcja> reakcje;

    /**
     * Domyślny konstruktor wymagany przez JPA.
     */
    public Post() {}

    /**
     * Konstruktor tworzący post bez zdjęcia.
     *
     * @param uzytkownik użytkownik, który utworzył post
     * @param tresc treść posta
     */
    public Post(Uzytkownik uzytkownik, String tresc) {
        this.uzytkownik = uzytkownik;
        this.tresc = tresc;
    }

    /**
     * Konstruktor tworzący post ze zdjęciem.
     *
     * @param uzytkownik użytkownik, który utworzył post
     * @param tresc treść posta
     * @param zdjecie zdjęcie dołączone do posta jako tablica bajtów
     */
    public Post(Uzytkownik uzytkownik, String tresc, byte[] zdjecie) {
        this.uzytkownik = uzytkownik;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
    }
}