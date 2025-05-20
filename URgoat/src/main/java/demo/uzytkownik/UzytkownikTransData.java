package demo.uzytkownik;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UzytkownikTransData {
    private int uzytkownikID;
    private byte[] zdjecie;
    private String pseudonim;
    private String imie;
    private String nazwisko;

    public UzytkownikTransData(int uzytkownikID, byte[] zdjecie, String pseudonim) {
        this.uzytkownikID = uzytkownikID;
        this.zdjecie = zdjecie;
        this.pseudonim = pseudonim;
    }

    public UzytkownikTransData() {

    }
}
