package demo.znajomy;

import demo.uzytkownik.Uzytkownik;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "znajomy")
@Getter
@Setter
public class Znajomy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int znajomyID;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "uzytkownik2ID")
    private  Uzytkownik uzytkownik2;

    public Znajomy(){}

    public Znajomy(Uzytkownik uzytkownik, Uzytkownik uzytkownik2){
        this.uzytkownik = uzytkownik;
        this.uzytkownik2 = uzytkownik2;
    }
}