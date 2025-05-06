package demo.wiadomosc;

public class WiadomoscTransData {

    private int wiadomoscID;
    private String tresc;
    private byte[] zdjecie;
    private int reakcja;
    private int czatID; // tylko ID czatu, żeby nie zagnieżdżać całej encji
    private String uzytkownik;

    public WiadomoscTransData() {}

    public WiadomoscTransData(int wiadomoscID, String tresc, byte[] zdjecie, int reakcja, int czatID) {
        this.wiadomoscID = wiadomoscID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.reakcja = reakcja;
        this.czatID = czatID;
    }

    public WiadomoscTransData(int wiadomoscID, String tresc, byte[] zdjecie, int reakcja, int czatID, String uzytkownik) {
        this.wiadomoscID = wiadomoscID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
        this.reakcja = reakcja;
        this.czatID = czatID;
        this.uzytkownik = uzytkownik;
    }

    public int getWiadomoscID() {
        return wiadomoscID;
    }

    public void setWiadomoscID(int wiadomoscID) {
        this.wiadomoscID = wiadomoscID;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public byte[] getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }

    public int getReakcja() {
        return reakcja;
    }

    public void setReakcja(int reakcja) {
        this.reakcja = reakcja;
    }

    public int getCzatID() {
        return czatID;
    }

    public void setCzatID(int czatID) {
        this.czatID = czatID;
    }
}