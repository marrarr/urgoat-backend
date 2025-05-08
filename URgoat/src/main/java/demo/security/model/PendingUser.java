package demo.security.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pending_users")
public class PendingUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imie;

    @Column(nullable = false)
    private String nazwisko;

    @Column(nullable = false)
    private String username;

    @Lob
    private byte[] zdjecie;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "verification_code", nullable = false)
    private String verificationCode;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {return imie;}
    public void setImie(String imie) {this.imie = imie;}

    public String getNazwisko() {return nazwisko;}
    public void setNazwisko(String nazwisko) {this.nazwisko = nazwisko;}

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getZdjecie() {return zdjecie;}
    public void setZdjecie(byte[] zdjecie) {this.zdjecie = zdjecie;}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}