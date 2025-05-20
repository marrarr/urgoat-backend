package demo.reakcja;

import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikTransData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReakcjaTransData {
    private Integer postID;
    private Integer komentarzID;
    private int reakcja;
    private UzytkownikTransData uzytkownik;

    public ReakcjaTransData() {
        this.postID = null;
        this.komentarzID = null;
        this.reakcja = 0; // domyślnie brak reakcji
    }

    public ReakcjaTransData(Integer postID, Integer komentarzID, int reakcja, UzytkownikTransData uzytkownik) {
        this.postID = postID;
        this.komentarzID = komentarzID;
        this.reakcja = reakcja;
        this.uzytkownik = uzytkownik;
    }
}
