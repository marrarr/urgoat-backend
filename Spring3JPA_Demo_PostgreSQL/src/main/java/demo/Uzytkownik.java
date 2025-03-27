package demo;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "uzytkownik")
class Uzytkownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uzytkownikID;

    private int czatID;

    @Column(length = 64)
    private String imie;

    @Column(length = 64)
    private String nazwisko;

    @Lob
    private byte[] zdjecie;

    @Column(length = 64)
    private String pseudonim;

    @Column(length = 64)
    private String email;

    private int permisje;


    //konstruktor użytkownika bez czatID
    public Uzytkownik(String imie, String nazwisko, byte[] zdjecie, String pseudonim, String email, int permisje){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.zdjecie = zdjecie;
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
    private  List<Czat> czaty;

    @OneToMany(mappedBy = "uzytkownik")
    private  List<Znajomy> znajomi1;

    @OneToMany(mappedBy = "uzytkownik2")
    private  List<Znajomy> znajomi2;


    public int getUzytkownikID(){return uzytkownikID;}
    public void setUzytkownikID(int uzytkownikID){this.uzytkownikID = uzytkownikID;}

    public int getCzatID(){return czatID;}
    public void setCzatID(int czatID){this.czatID = czatID;}

    public String getImie(){return imie;}
    public void setImie(String czatID){this.imie = imie;}

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

}
