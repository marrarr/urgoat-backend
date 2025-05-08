package demo.security.service;

import demo.security.model.PendingUser;
import demo.security.model.User;
import demo.security.repository.PendingUserRepository;
import demo.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PendingUserRepository pendingUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PendingUserRepository pendingUserRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.pendingUserRepository = pendingUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String imie, String nazwisko, String username, byte[] zdjecieBytes, String email, String password) {
        if (userRepository.findByUsername(username).isPresent() ||
                pendingUserRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Uzytkownik lub email juz istnieje");
        }

        PendingUser pendingUser = new PendingUser();
        pendingUser.setImie(imie);
        pendingUser.setNazwisko(nazwisko);
        pendingUser.setUsername(username);
        pendingUser.setZdjecie(zdjecieBytes);
        pendingUser.setEmail(email);
        pendingUser.setPassword(passwordEncoder.encode(password));
        String code = String.format("%06d", new Random().nextInt(999999));
        pendingUser.setVerificationCode(code);
        pendingUserRepository.save(pendingUser);

        System.out.println("Kod weryfikacyjny dla " + email + ": " + code);
    }

    @Transactional
    public PendingUser finalizeRegistration(String email, String code) {
        PendingUser p = pendingUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Brak rejestracji dla email"));
        if (!p.getVerificationCode().equals(code)) {
            throw new RuntimeException("Nieprawidłowy kod weryfikacyjny");
        }

        User user = new User();
        user.setUsername(p.getUsername());
        user.setEmail(p.getEmail());
        user.setPassword(p.getPassword());
        user.setRole("ROLE_USER");
        user.setVerified(true);
        userRepository.save(user);

        pendingUserRepository.delete(p);
        return p;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<PendingUser> findPendingByEmail(String email) {
        return pendingUserRepository.findByEmail(email);
    }
}