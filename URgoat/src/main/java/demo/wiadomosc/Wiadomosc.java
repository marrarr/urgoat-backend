package demo.wiadomosc;

import demo.czat.Czat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}