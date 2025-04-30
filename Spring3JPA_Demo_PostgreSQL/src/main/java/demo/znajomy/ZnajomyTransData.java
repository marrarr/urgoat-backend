package demo.znajomy;

import demo.uzytkownik.UzytkownikTransData;

public class ZnajomyTransData {

    private int znajomyID;
    private UzytkownikTransData uzytkownik;
    private UzytkownikTransData uzytkownik2;

    public ZnajomyTransData() {}

    public ZnajomyTransData(int znajomyID, UzytkownikTransData uzytkownik, UzytkownikTransData uzytkownik2) {
        this.znajomyID = znajomyID;
        this.uzytkownik = uzytkownik;
        this.uzytkownik2 = uzytkownik2;
    }

    public int getZnajomyID() {
        return znajomyID;
    }

    public void setZnajomyID(int znajomyID) {
        this.znajomyID = znajomyID;
    }

    public UzytkownikTransData getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(UzytkownikTransData uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public UzytkownikTransData getUzytkownik2() {
        return uzytkownik2;
    }

    public void setUzytkownik2(UzytkownikTransData uzytkownik2) {
        this.uzytkownik2 = uzytkownik2;
    }
}