package demo.znajomy;

import demo.uzytkownik.UzytkownikTransData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}