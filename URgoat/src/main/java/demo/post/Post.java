package demo.post;

import demo.komentarz.Komentarz;
import demo.uzytkownik.Uzytkownik;
import demo.reakcja.Reakcja;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;

    @Column(columnDefinition = "TEXT")
    private String tresc;

    @Lob
    private byte[] zdjecie;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    @OneToMany(mappedBy = "post")
    private List<Komentarz> komentarze;

    @OneToMany(mappedBy = "post")
    private List<Reakcja> reakcje;


    public Post(){}

    //Post bez zdjęcia
    public Post(Uzytkownik uzytkownikID, String tresc){
        this.uzytkownik = uzytkownikID;
        this.tresc = tresc;
    }

    public Post(Uzytkownik uzytkownikID, String tresc, byte[] zdjecie){
        this.uzytkownik = uzytkownikID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
    }
}