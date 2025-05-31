package demo.security.model;

import demo.uzytkownik.Uzytkownik;
import jakarta.persistence.*;

/**
 * Encja reprezentująca konto użytkownika w systemie.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean verified;

    @OneToOne(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private Uzytkownik profil;

    /**
     * Zwraca identyfikator użytkownika.
     *
     * @return Identyfikator użytkownika
     */
    public Long getId() {
        return id;
    }

    /**
     * Ustawia identyfikator użytkownika.
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
     * Zwraca rolę użytkownika.
     *
     * @return Rola użytkownika
     */
    public String getRole() {
        return role;
    }

    /**
     * Ustawia rolę użytkownika.
     *
     * @param role Rola użytkownika
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Sprawdza, czy konto użytkownika jest zweryfikowane.
     *
     * @return true, jeśli konto jest zweryfikowane, w przeciwnym razie false
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Ustawia status weryfikacji konta użytkownika.
     *
     * @param verified Status weryfikacji (true dla zweryfikowanego, false dla niezweryfikowanego)
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * Zwraca profil użytkownika powiązany z kontem.
     *
     * @return Obiekt Uzytkownik reprezentujący profil
     */
    public Uzytkownik getProfil() {
        return profil;
    }

    /**
     * Ustawia profil użytkownika powiązany z kontem.
     *
     * @param profil Obiekt Uzytkownik reprezentujący profil
     */
    public void setProfil(Uzytkownik profil) {
        this.profil = profil;
    }
}