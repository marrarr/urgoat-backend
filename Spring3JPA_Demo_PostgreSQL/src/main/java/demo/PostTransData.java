package demo;

public class PostTransData {

    private String tresc;
    private byte[] zdjecie;
    private Uzytkownik uzytkownik;

    public PostTransData()
    {
        this.tresc = "";
        this.zdjecie = null;
        uzytkownik = null;
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

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }


}
