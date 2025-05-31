package demo.uzytkownik;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Klasa DTO przechowująca dane użytkownika do transferu, takie jak identyfikator, zdjęcie, pseudonim, imię i nazwisko.
 */
@Getter
@Setter
@AllArgsConstructor
public class UzytkownikTransData {
    private int uzytkownikID;
    private byte[] zdjecie;
    private String pseudonim;
    private String imie;
    private String nazwisko;

    /**
     * Konstruktor tworzący obiekt z podstawowymi danymi użytkownika (bez imienia i nazwiska).
     *
     * @param uzytkownikID Identyfikator użytkownika
     * @param zdjecie      Zdjęcie profilowe w formacie bajtów
     * @param pseudonim    Pseudonim użytkownika
     */
    public UzytkownikTransData(int uzytkownikID, byte[] zdjecie, String pseudonim) {
        this.uzytkownikID = uzytkownikID;
        this.zdjecie = zdjecie;
        this.pseudonim = pseudonim;
    }

    /**
     * Domyślny konstruktor tworzący pusty obiekt.
     */
    public UzytkownikTransData() {
    }
}