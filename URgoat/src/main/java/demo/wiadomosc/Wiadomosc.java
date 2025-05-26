package demo.wiadomosc;

import demo.czat.Czat;
import demo.uzytkownik.Uzytkownik;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "wiadomosc")
@Getter
@Setter
public class Wiadomosc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wiadomoscID;

    @Column(columnDefinition = "TEXT")
    private String tresc;

    @Lob
    private byte[] zdjecie;

    private int reakcja;

    @ManyToOne
    @JoinColumn(name = "czatID")
    private Czat czat;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    public Wiadomosc(){}

    //sama wiadomość
    public Wiadomosc(Czat czat, String tresc){
        this.czat = czat;
        this.tresc = tresc;
    }

    //sama reakcja
    public Wiadomosc(Czat czat, int reakcja){
        this.czat = czat;
        this.reakcja = reakcja;
    }

    //wiadomość i zdjęcie
    public Wiadomosc(Czat czat, String tresc, byte[] zdjecie){
        this.czat = czat;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
    }

    //pełny konstruktor
    public Wiadomosc(Czat czat, String tresc, byte[] zdjecie, int reakcja){
        this.czat = czat;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.reakcja = reakcja;
    }

    public Wiadomosc(Czat czat, String tresc, Uzytkownik uzytkownik) {
        this.czat = czat;
        this.tresc = tresc;
        this.uzytkownik = uzytkownik;
    }

    public Wiadomosc(Czat czat, String tresc, byte[] zdjecie, Uzytkownik uzytkownik) {
        this.czat = czat;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.uzytkownik = uzytkownik;
    }

    public Wiadomosc(Czat czat, String tresc, byte[] zdjecie, int reakcja, Uzytkownik uzytkownik) {
        this.czat = czat;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.reakcja = reakcja;
        this.uzytkownik = uzytkownik;
    }
}