package demo.security.model;

import jakarta.persistence.*;

/**
 * Encja reprezentująca użytkownika oczekującego na weryfikację konta.
 */
@Entity
@Table(name = "pending_users")
public class PendingUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "verification_code", nullable = false)
    private String verificationCode;

    private String imie;
    private String nazwisko;
    private byte[] image;

    /**
     * Zwraca identyfikator użytkownika oczekującego.
     *
     * @return Identyfikator użytkownika
     */
    public Long getId() {
        return id;
    }

    /**
     * Ustawia identyfikator użytkownika oczekującego.
     *
     * @param id Identyfikator użytkownika
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Zwraca nazwę użytkownika.
     *
     * @return Nazwa użytkownika
     */
    public String getUsername() {
        return username;
    }

    /**
     * Ustawia nazwę użytkownika.
     *
     * @param username Nazwa użytkownika
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Zwraca adres e-mail użytkownika.
     *
     * @return Adres e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia adres e-mail użytkownika.
     *
     * @param email Adres e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Zwraca hasło użytkownika.
     *
     * @return Hasło użytkownika
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ustawia hasło użytkownika.
     *
     * @param password Hasło użytkownika
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Zwraca kod weryfikacyjny użytkownika.
     *
     * @return Kod weryfikacyjny
     */
    public String getVerificationCode() {
        return verificationCode;
    }

    /**
     * Ustawia kod weryfikacyjny użytkownika.
     *
     * @param verificationCode Kod weryfikacyjny
     */
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
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
    public byte[] getImage() {
        return image;
    }

    /**
     * Ustawia zdjęcie profilowe użytkownika w formacie bajtów.
     *
     * @param image Tablica bajtów reprezentująca zdjęcie
     */
    public void setImage(byte[] image) {
        this.image = image;
    }
}