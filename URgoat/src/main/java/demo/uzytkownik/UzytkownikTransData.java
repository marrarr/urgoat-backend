package demo.uzytkownik;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UzytkownikTransData {
    private int uzytkownikID;
    private byte[] zdjecie;
    private String pseudonim;

    public UzytkownikTransData(int uzytkownikID, byte[] zdjecie, String pseudonim) {
        this.uzytkownikID = uzytkownikID;
        this.zdjecie = zdjecie;
        this.pseudonim = pseudonim;
    }

    public UzytkownikTransData() {

    }
}
