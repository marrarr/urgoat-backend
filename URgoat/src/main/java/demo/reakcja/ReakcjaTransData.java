package demo.reakcja;

import demo.uzytkownik.UzytkownikTransData;
import lombok.Getter;
import lombok.Setter;

/**
 * Klasa DTO przechowująca dane reakcji użytkownika na post lub komentarz.
 */
@Getter
@Setter
public class ReakcjaTransData {
    private Integer postID;
    private Integer komentarzID;
    private int reakcja;
    private UzytkownikTransData uzytkownik;

    /**
     * Domyślny konstruktor inicjalizujący pola wartościami domyślnymi.
     * Ustawia postID i komentarzID na null, reakcja na 0 (brak reakcji).
     */
    public ReakcjaTransData() {
        this.postID = null;
        this.komentarzID = null;
        this.reakcja = 0; // domyślnie brak reakcji
    }

    /**
     * Konstruktor z parametrami do tworzenia obiektu z określonymi wartościami.
     *
     * @param postID       Identyfikator postu (może być null, jeśli reakcja dotyczy komentarza)
     * @param komentarzID  Identyfikator komentarza (może być null, jeśli reakcja dotyczy postu)
     * @param reakcja      Kod reakcji
     * @param uzytkownik   Obiekt UzytkownikTransData reprezentujący dane użytkownika
     */
    public ReakcjaTransData(Integer postID, Integer komentarzID, int reakcja, UzytkownikTransData uzytkownik) {
        this.postID = postID;
        this.komentarzID = komentarzID;
        this.reakcja = reakcja;
        this.uzytkownik = uzytkownik;
    }
}