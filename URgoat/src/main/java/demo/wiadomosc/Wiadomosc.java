package demo.wiadomosc;

import demo.czat.Czat;
import demo.uzytkownik.Uzytkownik;
import jakarta.persistence.*;

@Entity
@Table(name = "wiadomosc")
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
    @JoinColumn(name = "uzytkownikID") // kolumna klucza obcego w tabeli "wiadomosc"
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

    public Wiadomosc(String tresc, byte[] zdjecie, int reakcja, Czat czat, Uzytkownik uzytkownik) {
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.reakcja = reakcja;
        this.czat = czat;
        this.uzytkownik = uzytkownik;
    }


    public Czat getCzatID(){return czat;}
    public void setCzatID(Czat czatID){this.czat = czatID;}

    public String getTresc(){return tresc;}
    public void setTresc(String tresc){this.tresc = tresc;}

    public byte[] getZdjecie() {return zdjecie;}
    public void setZdjecie(byte[] zdjecie) {this.zdjecie = zdjecie;}

    public int getReakcja(){return reakcja;}
    public void setReakcja(int reakcja){this.reakcja = reakcja;}

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }


}
