package demo.post;

import demo.komentarz.KomentarzTransData;
import demo.reakcja.ReakcjaTransData;
import demo.uzytkownik.UzytkownikTransData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Klasa DTO (Data Transfer Object) przechowująca dane posta do transferu między warstwami aplikacji.
 * Zawiera informacje o identyfikatorze posta, treści, autorze, zdjęciu, komentarzach i reakcjach.
 */
@Getter
@Setter
public class PostTransData {

    /**
     * Unikalny identyfikator posta.
     */
    private int postId;

    /**
     * Treść posta.
     */
    private String tresc;

    /**
     * Dane autora posta w formacie DTO.
     */
    private UzytkownikTransData autor;

    /**
     * Zdjęcie dołączone do posta, przechowywane jako tablica bajtów.
     */
    private byte[] zdjecie;

    /**
     * Lista komentarzy powiązanych z postem w formacie DTO.
     */
    private List<KomentarzTransData> komentarze;

    /**
     * Lista reakcji powiązanych z postem w formacie DTO.
     */
    private List<ReakcjaTransData> reakcje;

    /**
     * Domyślny konstruktor wymagany dla serializacji i deserializacji.
     */
    public PostTransData() {}

    /**
     * Konstruktor inicjalizujący wszystkie pola obiektu.
     *
     * @param postId identyfikator posta
     * @param tresc treść posta
     * @param autor dane autora w formacie DTO
     * @param zdjecie zdjęcie dołączone do posta jako tablica bajtów
     * @param komentarze lista komentarzy w formacie DTO
     * @param reakcje lista reakcji w formacie DTO
     */
    public PostTransData(int postId, String tresc, UzytkownikTransData autor, byte[] zdjecie,
                         List<KomentarzTransData> komentarze, List<ReakcjaTransData> reakcje) {
        this.postId = postId;
        this.tresc = tresc;
        this.autor = autor;
        this.zdjecie = zdjecie;
        this.komentarze = komentarze;
        this.reakcje = reakcje;
    }
}