package demo.uzytkownik;
import demo.czat.Czat;
import demo.komentarz.Komentarz;
import demo.post.Post;
import demo.reakcja.Reakcja;
import demo.security.model.User;
import demo.znajomy.Znajomy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "uzytkownik")
@Getter
@Setter
public class Uzytkownik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uzytkownikID;

    private int czatID;

    @Column(length = 64)
    private String imie;

    @Column(length = 64)
    private String nazwisko;

   // @Lob
    private byte[] zdjecie;

    @Column(length = 64)
    private String pseudonim;

    @Column(length = 64)
    private String email;

    private int permisje;

    @OneToOne
    @JoinColumn(
            name = "email",              // kolumna w tabeli uzytkownik
            referencedColumnName = "email", // kolumna w tabeli users
            insertable = false,
            updatable = false
    )
    private User userAccount;

    @ManyToMany(mappedBy = "uzytkownicy")
    private List<Czat> czaty;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Post> posty;

    @OneToMany(mappedBy = "uzytkownik")
    private  List<Komentarz> komentarze;

    @OneToMany(mappedBy = "uzytkownik")
    private  List<Reakcja> reakcje;

    @OneToMany(mappedBy = "uzytkownik")
    private  List<Znajomy> znajomi1;

    @OneToMany(mappedBy = "uzytkownik2")
    private  List<Znajomy> znajomi2;


    public Uzytkownik() {
    }

    //konstruktor użytkownika bez czatID i zdjecia
    public Uzytkownik(String imie, String nazwisko, String pseudonim, String email, int permisje){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pseudonim = pseudonim;
        this.email = email;
        this.permisje = permisje;
    }

    //konstruktor użytkownika bez czatID
    public Uzytkownik(String imie, String nazwisko, byte[] zdjecie, String pseudonim, String email, int permisje){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.zdjecie = zdjecie;
        this.pseudonim = pseudonim;
        this.email = email;
        this.permisje = permisje;
    }

    //konstruktor użytkownika bez zdjęcia
    public Uzytkownik(int czatID, String imie, String nazwisko, String pseudonim, String email, int permisje){
        this.czatID = czatID;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pseudonim = pseudonim;
        this.email = email;
        this.permisje = permisje;
    }

    //konstruktor pełny
    public Uzytkownik(int czatID,String imie, String nazwisko, byte[] zdjecie, String pseudonim, String email, int permisje){
        this.czatID = czatID;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.zdjecie = zdjecie;
        this.pseudonim = pseudonim;
        this.email = email;
        this.permisje = permisje;
    }
}
