package demo.uzytkownik;
import demo.czat.Czat;
import demo.komentarz.Komentarz;
import demo.post.Post;
import demo.reakcja.Reakcja;
import demo.security.model.User;
import demo.znajomy.Znajomy;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "uzytkownik")
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

    @ManyToMany(mappedBy = "uzytkownicy") // 🔹 Lista czatów, w których uczestniczy użytkownik
    private List<Czat> czaty;

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

    public List<Czat> getCzaty() { return czaty;}
    public void setCzaty(List<Czat> czaty) {this.czaty = czaty;}

    public int getUzytkownikID(){return uzytkownikID;}
    public void setUzytkownikID(int uzytkownikID){this.uzytkownikID = uzytkownikID;}

    public int getCzatID(){return czatID;}
    public void setCzatID(int czatID){this.czatID = czatID;}

    public String getImie(){return imie;}
    public void setImie(String imie){this.imie = imie;}

    public String getNazwisko(){return nazwisko;}
    public void setNazwisko(String nazwisko){this.nazwisko = nazwisko;}

    public byte[] getZdjecie() {return zdjecie;}
    public void setZdjecie(byte[] zdjecie) {this.zdjecie = zdjecie;}

    public String getPseudonim(){return pseudonim;}
    public void setPseudonim(String pseudonim){this.pseudonim = pseudonim;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public int getPermisje(){return permisje;}
    public void setPermisje(int permisje){this.permisje = permisje;}

    //getery i setery służace do powiazanie przez obiekt klas Users - Uzytkownik
    public User getUserAccount() {return userAccount;}
    public void setUserAccount(User userAccount) {this.userAccount = userAccount;}

}
