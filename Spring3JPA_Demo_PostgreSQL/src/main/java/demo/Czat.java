package demo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "czat")

public class Czat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int czatID;

    //private int uzytkownikID;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    @OneToMany(mappedBy = "czat")
    private List<Wiadomosc> wiadomosci;

    public Czat(){};

    public Czat(Uzytkownik uzytkownikID){
        this.uzytkownik = uzytkownikID;
    }

    public Uzytkownik getUzytkownikID(){return uzytkownik;}
    public void setUzytkownikID(Uzytkownik uzytkownikID){this.uzytkownik = uzytkownikID;}
}
