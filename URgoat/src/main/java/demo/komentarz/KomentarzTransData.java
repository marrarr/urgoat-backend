package demo.komentarz;

import demo.reakcja.ReakcjaTransData;
import demo.uzytkownik.UzytkownikTransData;

import java.util.List;

public class KomentarzTransData {

    private int komentarzID;
    private String tresc;
    private byte[] zdjecie;
    private int postID;
    private UzytkownikTransData uzytkownik;

    public KomentarzTransData(int komentarzID, String tresc, byte[] zdjecie, int postID, UzytkownikTransData uzytkownik, List<ReakcjaTransData> reakcje) {
        this.komentarzID = komentarzID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.postID = postID;
        this.uzytkownik = uzytkownik;
        this.reakcje = reakcje;
    }

    public List<ReakcjaTransData> getReakcje() {
        return reakcje;
    }

    public void setReakcje(List<ReakcjaTransData> reakcje) {
        this.reakcje = reakcje;
    }

    public void setUzytkownik(UzytkownikTransData uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public void setKomentarzID(int komentarzID) {
        this.komentarzID = komentarzID;
    }

    private List<ReakcjaTransData> reakcje;


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

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public byte[] getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getKomentarzID() {
        return komentarzID;
    }

    public UzytkownikTransData getUzytkownik() {
        return uzytkownik;
    }
}
