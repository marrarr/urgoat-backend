package demo.uzytkownik;

public class UzytkownikTransData {
    private int uzytkownikID;
    private byte[] zdjecie;
    private String pseudonim;

    public UzytkownikTransData(int uzytkownikID, byte[] zdjecie, String pseudonim) {
        this.uzytkownikID = uzytkownikID;
        this.zdjecie = zdjecie;
        this.pseudonim = pseudonim;
    }

    public int getUzytkownikID() {
        return uzytkownikID;
    }

    public byte[] getZdjecie() {
        return zdjecie;
    }

    public String getPseudonim() {
        return pseudonim;
    }
}
