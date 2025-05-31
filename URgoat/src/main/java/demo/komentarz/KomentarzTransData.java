package demo.komentarz;

import demo.reakcja.ReakcjaTransData;
import demo.uzytkownik.UzytkownikTransData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Klasa DTO (Data Transfer Object) przechowująca dane komentarza do transferu między warstwami aplikacji.
 * Zawiera informacje o identyfikatorze komentarza, treści, zdjęciu, poście, użytkowniku i reakcjach.
 */
@Getter
@Setter
public class KomentarzTransData {

    /**
     * Unikalny identyfikator komentarza.
     */
    private int komentarzID;

    /**
     * Treść komentarza.
     */
    private String tresc;

    /**
     * Zdjęcie dołączone do komentarza, przechowywane jako tablica bajtów.
     */
    private byte[] zdjecie;

    /**
     * Identyfikator posta, do którego należy komentarz.
     */
    private int postID;

    /**
     * Dane użytkownika, który utworzył komentarz, w formacie DTO.
     */
    private UzytkownikTransData uzytkownik;

    /**
     * Lista reakcji na komentarz, w formacie DTO.
     */
    private List<ReakcjaTransData> reakcje;

    /**
     * Konstruktor inicjalizujący wszystkie pola obiektu.
     *
     * @param komentarzID identyfikator komentarza
     * @param tresc treść komentarza
     * @param zdjecie zdjęcie dołączone do komentarza jako tablica bajtów
     * @param postID identyfikator posta
     * @param uzytkownik dane użytkownika w formacie DTO
     * @param reakcje lista reakcji w formacie DTO
     */
    public KomentarzTransData(int komentarzID, String tresc, byte[] zdjecie, int postID,
                              UzytkownikTransData uzytkownik, List<ReakcjaTransData> reakcje) {
        this.komentarzID = komentarzID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.postID = postID;
        this.uzytkownik = uzytkownik;
        this.reakcje = reakcje;
    }

    /**
     * Domyślny konstruktor inicjalizujący pola domyślnymi wartościami.
     * Ustawia pustą treść, brak zdjęcia i zerowy identyfikator posta.
     */
    public KomentarzTransData() {
        this.tresc = "";
        this.zdjecie = null;
        this.postID = 0;
    }
}