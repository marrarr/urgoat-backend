package demo.post;

import demo.komentarz.KomentarzTransData;
import demo.uzytkownik.UzytkownikTransData;

import java.util.List;

public class PostTransData {
    private int postId;
    private String tresc;
    private UzytkownikTransData autor; // Zamiast pełnego obiektu Uzytkownik
    private byte[] zdjecie;  // Base64 lub URL (np. "/images/post/123.jpg")
    private List<KomentarzTransData> komentarze;

    public PostTransData() {}

    public PostTransData(int postId, String tresc, UzytkownikTransData autor, byte[] zdjecie, List<KomentarzTransData> komentarze) {
        this.postId = postId;
        this.tresc = tresc;
        this.autor = autor;
        this.zdjecie = zdjecie;
        this.komentarze = komentarze;
    }

    // Gettery i settery
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public String getTresc() { return tresc; }
    public void setTresc(String tresc) { this.tresc = tresc; }
    public UzytkownikTransData getAutor() { return autor; }
    public void setAutor(UzytkownikTransData autor) { this.autor = autor; }
    public byte[] getZdjecie() { return zdjecie; }
    public void setZdjecie(byte[] zdjecie) { this.zdjecie = zdjecie; }
    public List<KomentarzTransData> getKomentarze() { return komentarze; }
    public void setKomentarze(List<KomentarzTransData> komentarze) { this.komentarze = komentarze; }
}
