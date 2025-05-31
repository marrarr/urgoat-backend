package demo.czat;

import demo.uzytkownik.UzytkownikTransData;
import demo.wiadomosc.WiadomoscTransData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Klasa DTO (Data Transfer Object) przechowująca dane czatu do transferu między warstwami aplikacji.
 * Zawiera informacje o identyfikatorze czatu, uczestnikach oraz wiadomościach.
 */
@Getter
@Setter
public class CzatTransData {

    /**
     * Unikalny identyfikator czatu.
     */
    private int czatID;

    /**
     * Lista użytkowników uczestniczących w czacie, reprezentowanych jako obiekty DTO.
     */
    private List<UzytkownikTransData> uzytkownicy;

    /**
     * Lista wiadomości w czacie, reprezentowanych jako obiekty DTO.
     */
    private List<WiadomoscTransData> wiadomosci;

    /**
     * Domyślny konstruktor wymagany dla serializacji i deserializacji.
     */
    public CzatTransData() {}

    /**
     * Konstruktor inicjalizujący obiekt z identyfikatorem czatu i listą użytkowników.
     *
     * @param czatID identyfikator czatu
     * @param uzytkownicy lista użytkowników uczestniczących w czacie
     */
    public CzatTransData(int czatID, List<UzytkownikTransData> uzytkownicy) {
        this.czatID = czatID;
        this.uzytkownicy = uzytkownicy;
    }

    /**
     * Konstruktor inicjalizujący obiekt z identyfikatorem czatu, listą użytkowników i listą wiadomości.
     *
     * @param czatID identyfikator czatu
     * @param uzytkownicy lista użytkowników uczestniczących w czacie
     * @param wiadomosci lista wiadomości w czacie
     */
    public CzatTransData(int czatID, List<UzytkownikTransData> uzytkownicy, List<WiadomoscTransData> wiadomosci) {
        this.czatID = czatID;
        this.uzytkownicy = uzytkownicy;
        this.wiadomosci = wiadomosci;
    }
}