package demo.post;

import demo.komentarz.KomentarzTransData;
import demo.reakcja.ReakcjaTransData;
import demo.uzytkownik.UzytkownikTransData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostTransData {
    private int postId;
    private String tresc;
    private UzytkownikTransData autor; // Zamiast pełnego obiektu Uzytkownik
    private byte[] zdjecie;  // Base64 lub URL (np. "/images/post/123.jpg")
    private List<KomentarzTransData> komentarze;
    private List<ReakcjaTransData> reakcje;


    public PostTransData() {}

    public PostTransData(int postId, String tresc, UzytkownikTransData autor, byte[] zdjecie, List<KomentarzTransData> komentarze, List<ReakcjaTransData> reakcje) {
        this.postId = postId;
        this.tresc = tresc;
        this.autor = autor;
        this.zdjecie = zdjecie;
        this.komentarze = komentarze;
        this.reakcje = reakcje;
    }
}