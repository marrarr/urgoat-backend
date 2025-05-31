package demo.wiadomosc;

import demo.czat.Czat;
import demo.uzytkownik.Uzytkownik;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

/**
 * Encja reprezentująca wiadomość w systemie, powiązaną z czatem i użytkownikiem.
 */
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

    /**
     * Domyślny konstruktor encji Wiadomosc.
     */
    public Wiadomosc() {
    }

    /**
     * Konstruktor tworzący wiadomość z treścią i powiązaniem z czatem.
     *
     * @param czat  Czat, do którego należy wiadomość
     * @param tresc Treść wiadomości
     */
    public Wiadomosc(Czat czat, String tresc) {
        this.czat = czat;
        this.tresc = tresc;
    }

    /**
     * Konstruktor tworzący wiadomość z reakcją i powiązaniem z czatem.
     *
     * @param czat    Czat, do którego należy wiadomość
     * @param reakcja Kod reakcji
     */
    public Wiadomosc(Czat czat, int reakcja) {
        this.czat = czat;
        this.reakcja = reakcja;
    }

    /**
     * Konstruktor tworzący wiadomość z treścią, zdjęciem i powiązaniem z czatem.
     *
     * @param czat    Czat, do którego należy wiadomość
     * @param tresc   Treść wiadomości
     * @param zdjecie Zdjęcie w formacie bajtów
     */
    public Wiadomosc(Czat czat, String tresc, byte[] zdjecie) {
        this.czat = czat;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
    }

    /**
     * Konstruktor tworzący wiadomość z pełnym zestawem danych (treść, zdjęcie, reakcja) i powiązaniem z czatem.
     *
     * @param czat    Czat, do którego należy wiadomość
     * @param tresc   Treść wiadomości
     * @param zdjecie Zdjęcie w formacie bajtów
     * @param reakcja Kod reakcji
     */
    public Wiadomosc(Czat czat, String tresc, byte[] zdjecie, int reakcja) {
        this.czat = czat;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.reakcja = reakcja;
    }

    /**
     * Konstruktor tworzący wiadomość z treścią, powiązaniem z czatem i użytkownikiem.
     *
     * @param czat       Czat, do którego należy wiadomość
     * @param tresc      Treść wiadomości
     * @param uzytkownik Użytkownik wysyłający wiadomość
     */
    public Wiadomosc(Czat czat, String tresc, Uzytkownik uzytkownik) {
        this.czat = czat;
        this.tresc = tresc;
        this.uzytkownik = uzytkownik;
    }

    /**
     * Konstruktor tworzący wiadomość z treścią, zdjęciem, powiązaniem z czatem i użytkownikiem.
     *
     * @param czat       Czat, do którego należy wiadomość
     * @param tresc      Treść wiadomości
     * @param zdjecie    Zdjęcie w formacie bajtów
     * @param uzytkownik Użytkownik wysyłający wiadomość
     */
    public Wiadomosc(Czat czat, String tresc, byte[] zdjecie, Uzytkownik uzytkownik) {
        this.czat = czat;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.uzytkownik = uzytkownik;
    }

    /**
     * Konstruktor tworzący wiadomość z pełnym zestawem danych (treść, zdjęcie, reakcja) oraz powiązaniem z czatem i użytkownikiem.
     *
     * @param czat       Czat, do którego należy wiadomość
     * @param tresc      Treść wiadomości
     * @param zdjecie    Zdjęcie w formacie bajtów
     * @param reakcja    Kod reakcji
     * @param uzytkownik Użytkownik wysyłający wiadomość
     */
    public Wiadomosc(Czat czat, String tresc, byte[] zdjecie, int reakcja, Uzytkownik uzytkownik) {
        this.czat = czat;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.reakcja = reakcja;
        this.uzytkownik = uzytkownik;
    }

    /**
     * Zwraca identyfikator wiadomości.
     *
     * @return Identyfikator wiadomości
     */
    public int getWiadomoscID() {
        return wiadomoscID;
    }

    /**
     * Ustawia identyfikator wiadomości.
     *
     * @param wiadomoscID Identyfikator wiadomości
     */
    public void setWiadomoscID(int wiadomoscID) {
        this.wiadomoscID = wiadomoscID;
    }

    /**
     * Zwraca treść wiadomości.
     *
     * @return Treść wiadomości
     */
    public String getTresc() {
        return tresc;
    }

    /**
     * Ustawia treść wiadomości.
     *
     * @param tresc Treść wiadomości
     */
    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    /**
     * Zwraca zdjęcie dołączone do wiadomości w formacie bajtów.
     *
     * @return Tablica bajtów reprezentująca zdjęcie
     */
    public byte[] getZdjecie() {
        return zdjecie;
    }

    /**
     * Ustawia zdjęcie dołączone do wiadomości w formacie bajtów.
     *
     * @param zdjecie Tablica bajtów reprezentująca zdjęcie
     */
    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }

    /**
     * Zwraca kod reakcji na wiadomość.
     *
     * @return Kod reakcji
     */
    public int getReakcja() {
        return reakcja;
    }

    /**
     * Ustawia kod reakcji na wiadomość.
     *
     * @param reakcja Kod reakcji
     */
    public void setReakcja(int reakcja) {
        this.reakcja = reakcja;
    }

    /**
     * Zwraca czat, do którego należy wiadomość.
     *
     * @return Obiekt Czat
     */
    public Czat getCzat() {
        return czat;
    }

    /**
     * Ustawia czat, do którego należy wiadomość.
     *
     * @param czat Obiekt Czat
     */
    public void setCzat(Czat czat) {
        this.czat = czat;
    }

    /**
     * Zwraca użytkownika, który wysłał wiadomość.
     *
     * @return Obiekt Uzytkownik
     */
    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    /**
     * Ustawia użytkownika, który wysłał wiadomość.
     *
     * @param uzytkownik Obiekt Uzytkownik
     */
    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
}