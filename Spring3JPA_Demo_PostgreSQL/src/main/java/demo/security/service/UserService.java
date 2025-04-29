package demo.security.service;

import demo.security.model.PendingUser;
import demo.security.model.User;
import demo.security.repository.PendingUserRepository;
import demo.security.repository.UserRepository;
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

    public void registerUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent() ||
                pendingUserRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Uzytkownik lub email juz istnieje");
        }

        PendingUser pendingUser = new PendingUser();
        pendingUser.setUsername(username);
        pendingUser.setEmail(email);
        pendingUser.setPassword(passwordEncoder.encode(password));
        String code = String.format("%06d", new Random().nextInt(999999));
        pendingUser.setVerificationCode(code);
        pendingUserRepository.save(pendingUser);

        System.out.println("Kod weryfikacyjny dla " + email + ": " + code);
    }

    public boolean verifyUser(String email, String code) {
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