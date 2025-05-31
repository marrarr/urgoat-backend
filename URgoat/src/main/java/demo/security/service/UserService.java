package demo.security.service;

import demo.SerwisAplikacji;
import demo.security.model.PendingUser;
import demo.security.model.User;
import demo.security.repository.PendingUserRepository;
import demo.security.repository.UserRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Serwis odpowiedzialny za zarządzanie użytkownikami, w tym rejestrację, weryfikację oraz usuwanie użytkowników.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PendingUserRepository pendingUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final SerwisAplikacji serwisAplikacji;
    private final UzytkownikRepository uzytkownikRepository;
    private final UzytkownikService uzytkownikService;

    /**
     * Konstruktor serwisu UserService.
     *
     * @param userRepository          Repozytorium użytkowników
     * @param pendingUserRepository   Repozytorium użytkowników oczekujących
     * @param passwordEncoder         Koder haseł
     * @param serwisAplikacji         Serwis aplikacji
     * @param uzytkownikRepository    Repozytorium profili użytkowników
     * @param uzytkownikService       Serwis profili użytkowników
     */
    public UserService(
            UserRepository userRepository,
            PendingUserRepository pendingUserRepository,
            PasswordEncoder passwordEncoder,
            SerwisAplikacji serwisAplikacji,
            UzytkownikRepository uzytkownikRepository,
            UzytkownikService uzytkownikService) {
        this.userRepository = userRepository;
        this.pendingUserRepository = pendingUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.serwisAplikacji = serwisAplikacji;
        this.uzytkownikRepository = uzytkownikRepository;
        this.uzytkownikService = uzytkownikService;
    }

    /**
     * Rejestruje nowego użytkownika oczekującego na weryfikację.
     *
     * @param username Nazwa użytkownika
     * @param email    Adres e-mail użytkownika
     * @param password Hasło użytkownika
     * @param imie     Imię użytkownika
     * @param nazwisko Nazwisko użytkownika
     * @param image    Zdjęcie profilowe w formacie bajtów
     * @throws RuntimeException Jeśli użytkownik lub e-mail już istnieje
     */
    public void registerUser(String username, String email, String password, String imie, String nazwisko, byte[] image) {
        if (userRepository.findByUsername(username).isPresent() ||
                pendingUserRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Uzytkownik lub email juz istnieje");
        }

        PendingUser pendingUser = new PendingUser();
        pendingUser.setUsername(username);
        pendingUser.setEmail(email);
        pendingUser.setPassword(passwordEncoder.encode(password));
        pendingUser.setImie(imie);
        pendingUser.setNazwisko(nazwisko);
        pendingUser.setImage(image);

        String code = String.format("%06d", new Random().nextInt(999999));
        pendingUser.setVerificationCode(code);
        pendingUserRepository.save(pendingUser);

        System.out.println("Kod weryfikacyjny dla " + email + ": " + code);
    }

    /**
     * Weryfikuje użytkownika na podstawie e-maila i kodu weryfikacyjnego, tworząc konto użytkownika po powodzeniu.
     *
     * @param email Adres e-mail użytkownika
     * @param code  Kod weryfikacyjny
     * @return true, jeśli weryfikacja się powiodła, w przeciwnym razie false
     * @throws IOException W przypadku błędów wejścia/wyjścia podczas weryfikacji
     */
    @Transactional
    public boolean verifyUser(String email, String code) throws IOException {
        Optional<PendingUser> pendingUserOpt = pendingUserRepository.findByEmail(email);
        if (pendingUserOpt.isPresent()) {
            PendingUser pendingUser = pendingUserOpt.get();

            if (code.equals(pendingUser.getVerificationCode())) {
                User user = new User();
                user.setUsername(pendingUser.getUsername());
                user.setEmail(pendingUser.getEmail());
                user.setPassword(pendingUser.getPassword());
                user.setRole("ROLE_USER");
                user.setVerified(true);
                userRepository.save(user);

                uzytkownikService.dodajUzytkownika(
                        user.getEmail(),
                        pendingUser.getImie(),
                        pendingUser.getNazwisko(),
                        pendingUser.getImage()
                );

                pendingUserRepository.delete(pendingUser);
                return true;
            }
        }
        return false;
    }

    /**
     * Pobiera listę wszystkich zarejestrowanych użytkowników.
     *
     * @return Lista obiektów User
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Usuwa użytkownika na podstawie identyfikatora.
     *
     * @param id Identyfikator użytkownika
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}