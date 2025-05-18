package demo.czat;

import demo.uzytkownik.Uzytkownik;
import demo.wiadomosc.Wiadomosc;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "czat")
@Getter
@Setter
public class Czat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int czatID;

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
}
