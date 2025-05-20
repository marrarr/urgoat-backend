package demo.wiadomosc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WiadomoscTransData {
    private int wiadomoscID;
    private String tresc;
    private byte[] zdjecie;
    private int reakcja;
    private int czatID; // tylko ID czatu, żeby nie zagnieżdżać całej encji

    public WiadomoscTransData() {}

    public WiadomoscTransData(int wiadomoscID, String tresc, byte[] zdjecie, int reakcja, int czatID) {
        this.wiadomoscID = wiadomoscID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.reakcja = reakcja;
        this.czatID = czatID;
    }
}