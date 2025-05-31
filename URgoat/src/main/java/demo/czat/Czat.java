package demo.czat;

import demo.uzytkownik.Uzytkownik;
import demo.wiadomosc.Wiadomosc;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Encja reprezentująca czat w systemie.
 * Przechowuje informacje o użytkownikach uczestniczących w czacie oraz powiązanych wiadomościach.
 */
@Entity
@Table(name = "czat")
@Getter
@Setter
public class Czat {

    /**
     * Unikalny identyfikator czatu, generowany automatycznie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int czatID;

    /**
     * Lista użytkowników uczestniczących w czacie.
     * Relacja wiele-do-wielu z encją Uzytkownik, mapowana przez tabelę pośredniczącą czat_uzytkownik.
     */
    @ManyToMany
    @JoinTable(
            name = "czat_uzytkownik",
            joinColumns = @JoinColumn(name = "czatID"),
            inverseJoinColumns = @JoinColumn(name = "uzytkownikID")
    )
    private List<Uzytkownik> uzytkownicy;

    /**
     * Lista wiadomości należących do czatu.
     * Relacja jeden-do-wielu z encją Wiadomosc.
     */
    @OneToMany(mappedBy = "czat")
    private List<Wiadomosc> wiadomosci;

    /**
     * Domyślny konstruktor wymagany przez JPA.
     */
    public Czat() {
    }

    /**
     * Konstruktor inicjalizujący czat z listą użytkowników.
     *
     * @param uzytkownicy lista użytkowników, którzy uczestniczą w czacie
     */
    public Czat(List<Uzytkownik> uzytkownicy) {
        this.uzytkownicy = uzytkownicy;
    }
}