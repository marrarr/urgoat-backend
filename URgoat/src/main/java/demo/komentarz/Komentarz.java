package demo.komentarz;

import demo.post.Post;
import demo.reakcja.Reakcja;
import demo.uzytkownik.Uzytkownik;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "komentarz")
@Getter
@Setter
@NoArgsConstructor
public class Komentarz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int komentarzID;

    @Column(columnDefinition = "TEXT")
    private String tresc;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] zdjecie;

    @ManyToOne
    @JoinColumn(name = "postID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    @OneToMany(mappedBy = "komentarz",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reakcja> reakcje;

    //Komentarz bez zdjęcia
    public Komentarz(Post postID, Uzytkownik uzytkownikID, String tresc){
        this.post = postID;
        this.uzytkownik = uzytkownikID;
        this.tresc = tresc;
    }

    public Komentarz(Post postID, Uzytkownik uzytkownikID, String tresc, byte[] zdjecie){
        this.post = postID;
        this.uzytkownik = uzytkownikID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
    }
}