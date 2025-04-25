package demo.transdata;

public class PostTransData {

    private String tresc;
    private byte[] zdjecie;

    public PostTransData()
    {
        this.tresc = "";
        this.zdjecie = null;
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



}
