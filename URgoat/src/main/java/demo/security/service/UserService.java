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

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PendingUserRepository pendingUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final SerwisAplikacji serwisAplikacji;
    private final UzytkownikRepository uzytkownikRepository;
    private final UzytkownikService uzytkownikService;
    public UserService(
            UserRepository userRepository,
            PendingUserRepository pendingUserRepository,
            PasswordEncoder passwordEncoder,
            SerwisAplikacji serwisAplikacji,
            UzytkownikRepository uzytkownikRepository, UzytkownikService uzytkownikService) {
        this.userRepository = userRepository;
        this.pendingUserRepository = pendingUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.serwisAplikacji = serwisAplikacji;
        this.uzytkownikRepository = uzytkownikRepository;
        this.uzytkownikService = uzytkownikService;
    }

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

//TODO sprawdzanie wielkosci zdjecia
//
//                try {
//                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(pendingUser.getImage()));
//
//                    if (image == null) {
//                        throw new IllegalArgumentException("Nieprawidłowy format obrazu");
//                    }
//
//                    if (image.getWidth() != 500 || image.getHeight() != 500) {
//                        throw new IllegalArgumentException("Obraz musi mieć dokładnie 500x500 pikseli");
//                    }
//
//                } catch (IOException e) {
//                    throw new IllegalArgumentException("Nie udało się odczytać obrazu", e);
//                }
//


                pendingUserRepository.delete(pendingUser);
                return true;
            }
        }
        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}