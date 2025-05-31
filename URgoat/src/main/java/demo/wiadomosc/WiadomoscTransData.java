package demo.wiadomosc;

import lombok.Getter;
import lombok.Setter;

/**
 * Klasa DTO przechowująca dane wiadomości do transferu, takie jak identyfikator, treść, zdjęcie, reakcja i identyfikator czatu.
 */
@Getter
@Setter
public class WiadomoscTransData {
    private int wiadomoscID;
    private String tresc;
    private byte[] zdjecie;
    private int reakcja;
    private int czatID; // tylko ID czatu, żeby nie zagnieżdżać całej encji

    /**
     * Domyślny konstruktor tworzący pusty obiekt.
     */
    public WiadomoscTransData() {}

    /**
     * Konstruktor tworzący obiekt z pełnym zestawem danych wiadomości.
     *
     * @param wiadomoscID Identyfikator wiadomości
     * @param tresc       Treść wiadomości
     * @param zdjecie     Zdjęcie dołączone do wiadomości w formacie bajtów
     * @param reakcja     Kod reakcji na wiadomość
     * @param czatID      Identyfikator czatu, do którego należy wiadomość
     */
    public WiadomoscTransData(int wiadomoscID, String tresc, byte[] zdjecie, int reakcja, int czatID) {
        this.wiadomoscID = wiadomoscID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.reakcja = reakcja;
        this.czatID = czatID;
    }
}