package demo.komentarz;

import demo.reakcja.ReakcjaTransData;
import demo.uzytkownik.UzytkownikTransData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KomentarzTransData {
    private int komentarzID;
    private String tresc;
    private byte[] zdjecie;
    private int postID;
    private UzytkownikTransData uzytkownik;
    private List<ReakcjaTransData> reakcje;


    public KomentarzTransData(int komentarzID, String tresc, byte[] zdjecie, int postID, UzytkownikTransData uzytkownik, List<ReakcjaTransData> reakcje) {
        this.komentarzID = komentarzID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.postID = postID;
        this.uzytkownik = uzytkownik;
        this.reakcje = reakcje;
    }

    public KomentarzTransData() {
        this.tresc = "";
        this.zdjecie = null;
        this.postID = 0;
    }

    public KomentarzTransData(int komentarzID, String tresc, byte[] zdjecie, int postID) {
        this.komentarzID = komentarzID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.postID = postID;
    }

    public KomentarzTransData(int komentarzID, String tresc, byte[] zdjecie, int postID, UzytkownikTransData uzytkownik) {
        this.komentarzID = komentarzID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.postID = postID;
        this.uzytkownik = uzytkownik;
    }
}