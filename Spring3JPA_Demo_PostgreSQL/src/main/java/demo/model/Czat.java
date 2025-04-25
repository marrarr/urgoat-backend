package demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "czat")

public class Czat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int czatID;

    //private int uzytkownikID;

    @ManyToMany
    @JoinTable(
            name = "czat_uzytkownik",
            joinColumns = @JoinColumn(name = "czatID"),
            inverseJoinColumns = @JoinColumn(name = "uzytkownikID")
    )
    private List<Uzytkownik> uzytkownicy; // Lista użytkowników w czacie

    @OneToMany(mappedBy = "czat")
    private List<Wiadomosc> wiadomosci;

    public Czat(){};

    public Czat(List<Uzytkownik> uzytkownicy) {
        this.uzytkownicy = uzytkownicy;
    }

    public List<Uzytkownik> getUzytkownicy(){return uzytkownicy;}
    public void setUzytkownicy(List<Uzytkownik> uzytkownicy) {this.uzytkownicy = uzytkownicy;}

    public List<Wiadomosc> getWiadomosci() {return wiadomosci;}
    public void setWiadomosci(List<Wiadomosc> wiadomosci) {this.wiadomosci = wiadomosci;}
}
