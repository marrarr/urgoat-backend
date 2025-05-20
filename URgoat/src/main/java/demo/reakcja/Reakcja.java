package demo.reakcja;

import demo.komentarz.Komentarz;
import demo.post.Post;
import demo.uzytkownik.Uzytkownik;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reakcja")
@Getter
@Setter
public class Reakcja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reakcjaID;

    private int reakcja;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "postID", nullable = true)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "komentarzID", nullable = true)
    private Komentarz komentarz;

    public Reakcja(){}

    public Reakcja(Uzytkownik uzytkownik, Komentarz komentarz, Post post, int reakcja){
        this.uzytkownik = uzytkownik;
        this.komentarz = komentarz;
        this.post = post;
        this.reakcja = reakcja;
    }
}