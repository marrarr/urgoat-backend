package demo.czat;
import demo.uzytkownik.UzytkownikTransData;
import demo.wiadomosc.WiadomoscTransData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CzatTransData {
    private int czatID;
    private List<UzytkownikTransData> uzytkownicy;
    private List<WiadomoscTransData> wiadomosci;

    public CzatTransData() {}

    public CzatTransData(int czatID, List<UzytkownikTransData> uzytkownicy) {
        this.czatID = czatID;
        this.uzytkownicy = uzytkownicy;
    }

    public CzatTransData(int czatID, List<UzytkownikTransData> uzytkownicy, List<WiadomoscTransData> wiadomosci) {
        this.czatID = czatID;
        this.uzytkownicy = uzytkownicy;
        this.wiadomosci = wiadomosci;
    }
}