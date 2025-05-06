package demo.czat;
import demo.uzytkownik.UzytkownikTransData;
import demo.wiadomosc.WiadomoscTransData;
import java.util.List;


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

    public int getCzatID() {
        return czatID;
    }

    public void setCzatID(int czatID) {
        this.czatID = czatID;
    }

    public List<UzytkownikTransData> getUzytkownicy() {
        return uzytkownicy;
    }

    public void setUzytkownicy(List<UzytkownikTransData> uzytkownicy) {
        this.uzytkownicy = uzytkownicy;
    }

    public List<WiadomoscTransData> getWiadomosci() {
        return wiadomosci;
    }

    public void setWiadomosci(List<WiadomoscTransData> wiadomosci) {
        this.wiadomosci = wiadomosci;
    }
}