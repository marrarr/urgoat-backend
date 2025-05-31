package demo.uzytkownik;

import demo.czat.Czat;
import demo.komentarz.Komentarz;
import demo.post.Post;
import demo.reakcja.Reakcja;
import demo.security.model.User;
import demo.wiadomosc.Wiadomosc;
import demo.znajomy.Znajomy;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Encja reprezentująca profil użytkownika w systemie.
 */
@Entity
@Table(name = "uzytkownik")
@Getter
@Setter
public class Uzytkownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uzytkownikID;

    @Column(length = 64)
    private String imie;

    @Column(length = 64)
    private String nazwisko;

    @Basic(fetch = FetchType.LAZY)
    private byte[] zdjecie;

    @Column(length = 64)
    private String pseudonim;

    @Column(length = 64)
    private String email;

    private int permisje;

    @OneToOne
    @JoinColumn(
            name = "email",              // kolumna w tabeli uzytkownik
            referencedColumnName = "email", // kolumna w tabeli users
            insertable = false,
            updatable = false
    )
    private User userAccount;

    @ManyToMany(mappedBy = "uzytkownicy")
    private List<Czat> czaty;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Post> posty;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Komentarz> komentarze;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Reakcja> reakcje;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Wiadomosc> wiadomosci;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Znajomy> znajomi1;

    @OneToMany(mappedBy = "uzytkownik2")
    private List<Znajomy> znajomi2;

    /**
     * Domyślny konstruktor encji Uzytkownik.
     */
    public Uzytkownik() {
    }

    /**
     * Konstruktor tworzący użytkownika bez zdjęcia i powiązania z czatami.
     *
     * @param imie      Imię użytkownika
     * @param nazwisko  Nazwisko użytkownika
     * @param pseudonim Pseudonim użytkownika
     * @param email     Adres e-mail użytkownika
     * @param permisje  Poziom uprawnień użytkownika
     */
    public Uzytkownik(String imie, String nazwisko, String pseudonim, String email, int permisje) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pseudonim = pseudonim;
        this.email = email;
        this.permisje = permisje;
    }

    /**
     * Konstruktor tworzący użytkownika z zdjęciem, ale bez powiązania z czatami.
     *
     * @param imie      Imię użytkownika
     * @param nazwisko  Nazwisko użytkownika
     * @param zdjecie   Zdjęcie profilowe w formacie bajtów
     * @param pseudonim Pseudonim użytkownika
     * @param email     Adres e-mail użytkownika
     * @param permisje  Poziom uprawnień użytkownika
     */
    public Uzytkownik(String imie, String nazwisko, byte[] zdjecie, String pseudonim, String email, int permisje) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.zdjecie = zdjecie;
        this.pseudonim = pseudonim;
        this.email = email;
        this.permisje = permisje;
    }

    /**
     * Konstruktor tworzący użytkownika z pełnym zestawem danych, w tym powiązaniem z kontem User.
     *
     * @param user      Konto użytkownika (encja User)
     * @param email     Adres e-mail użytkownika
     * @param pseudonim Pseudonim użytkownika
     * @param imie      Imię użytkownika
     * @param nazwisko  Nazwisko użytkownika
     * @param zdjecie   Zdjęcie profilowe w formacie bajtów
     * @param permisje  Poziom uprawnień użytkownika
     */
    public Uzytkownik(User user, String email, String pseudonim, String imie, String nazwisko, byte[] zdjecie, int permisje) {
        userAccount = user;
        this.email = email;
        this.pseudonim = pseudonim;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.zdjecie = zdjecie;
        this.permisje = permisje;
    }

    /**
     * Zwraca identyfikator użytkownika.
     *
     * @return Identyfikator użytkownika
     */
    public int getUzytkownikID() {
        return uzytkownikID;
    }

    /**
     * Ustawia identyfikator użytkownika.
     *
     * @param uzytkownikID Identyfikator użytkownika
     */
    public void setUzytkownikID(int uzytkownikID) {
        this.uzytkownikID = uzytkownikID;
    }

    /**
     * Zwraca imię użytkownika.
     *
     * @return Imię użytkownika
     */
    public String getImie() {
        return imie;
    }

    /**
     * Ustawia imię użytkownika.
     *
     * @param imie Imię użytkownika
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * Zwraca nazwisko użytkownika.
     *
     * @return Nazwisko użytkownika
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * Ustawia nazwisko użytkownika.
     *
     * @param nazwisko Nazwisko użytkownika
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * Zwraca zdjęcie profilowe użytkownika w formacie bajtów.
     *
     * @return Tablica bajtów reprezentująca zdjęcie
     */
    public byte[] getZdjecie() {
        return zdjecie;
    }

    /**
     * Ustawia zdjęcie profilowe użytkownika w formacie bajtów.
     *
     * @param zdjecie Tablica bajtów reprezentująca zdjęcie
     */
    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }

    /**
     * Zwraca pseudonim użytkownika.
     *
     * @return Pseudonim użytkownika
     */
    public String getPseudonim() {
        return pseudonim;
    }

    /**
     * Ustawia pseudonim użytkownika.
     *
     * @param pseudonim Pseudonim użytkownika
     */
    public void setPseudonim(String pseudonim) {
        this.pseudonim = pseudonim;
    }

    /**
     * Zwraca adres e-mail użytkownika.
     *
     * @return Adres e-mail użytkownika
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia adres e-mail użytkownika.
     *
     * @param email Adres e-mail użytkownika
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Zwraca poziom uprawnień użytkownika.
     *
     * @return Poziom uprawnień
     */
    public int getPermisje() {
        return permisje;
    }

    /**
     * Ustawia poziom uprawnień użytkownika.
     *
     * @param permisje Poziom uprawnień
     */
    public void setPermisje(int permisje) {
        this.permisje = permisje;
    }

    /**
     * Zwraca konto użytkownika powiązane z profilem.
     *
     * @return Obiekt User reprezentujący konto użytkownika
     */
    public User getUserAccount() {
        return userAccount;
    }

    /**
     * Ustawia konto użytkownika powiązane z profilem.
     *
     * @param userAccount Obiekt User reprezentujący konto użytkownika
     */
    public void setUserAccount(User userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * Zwraca listę czatów, w których uczestniczy użytkownik.
     *
     * @return Lista obiektów Czat
     */
    public List<Czat> getCzaty() {
        return czaty;
    }

    /**
     * Ustawia listę czatów, w których uczestniczy użytkownik.
     *
     * @param czaty Lista obiektów Czat
     */
    public void setCzaty(List<Czat> czaty) {
        this.czaty = czaty;
    }

    /**
     * Zwraca listę postów utworzonych przez użytkownika.
     *
     * @return Lista obiektów Post
     */
    public List<Post> getPosty() {
        return posty;
    }

    /**
     * Ustawia listę postów utworzonych przez użytkownika.
     *
     * @param posty Lista obiektów Post
     */
    public void setPosty(List<Post> posty) {
        this.posty = posty;
    }

    /**
     * Zwraca listę komentarzy utworzonych przez użytkownika.
     *
     * @return Lista obiektów Komentarz
     */
    public List<Komentarz> getKomentarze() {
        return komentarze;
    }

    /**
     * Ustawia listę komentarzy utworzonych przez użytkownika.
     *
     * @param komentarze Lista obiektów Komentarz
     */
    public void setKomentarze(List<Komentarz> komentarze) {
        this.komentarze = komentarze;
    }

    /**
     * Zwraca listę reakcji dodanych przez użytkownika.
     *
     * @return Lista obiektów Reakcja
     */
    public List<Reakcja> getReakcje() {
        return reakcje;
    }

    /**
     * Ustawia listę reakcji dodanych przez użytkownika.
     *
     * @param reakcje Lista obiektów Reakcja
     */
    public void setReakcje(List<Reakcja> reakcje) {
        this.reakcje = reakcje;
    }

    /**
     * Zwraca listę wiadomości wysłanych przez użytkownika.
     *
     * @return Lista obiektów Wiadomosc
     */
    public List<Wiadomosc> getWiadomosci() {
        return wiadomosci;
    }

    /**
     * Ustawia listę wiadomości wysłanych przez użytkownika.
     *
     * @param wiadomosci Lista obiektów Wiadomosc
     */
    public void setWiadomosci(List<Wiadomosc> wiadomosci) {
        this.wiadomosci = wiadomosci;
    }

    /**
     * Zwraca listę znajomości użytkownika jako inicjatora relacji.
     *
     * @return Lista obiektów Znajomy
     */
    public List<Znajomy> getZnajomi1() {
        return znajomi1;
    }

    /**
     * Ustawia listę znajomości użytkownika jako inicjatora relacji.
     *
     * @param znajomi1 Lista obiektów Znajomy
     */
    public void setZnajomi1(List<Znajomy> znajomi1) {
        this.znajomi1 = znajomi1;
    }

    /**
     * Zwraca listę znajomości użytkownika jako odbiorcy relacji.
     *
     * @return Lista obiektów Znajomy
     */
    public List<Znajomy> getZnajomi2() {
        return znajomi2;
    }

    /**
     * Ustawia listę znajomości użytkownika jako odbiorcy relacji.
     *
     * @param znajomi2 Lista obiektów Znajomy
     */
    public void setZnajomi2(List<Znajomy> znajomi2) {
        this.znajomi2 = znajomi2;
    }
}