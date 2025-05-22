package demo.common;

import demo.security.model.User;
import demo.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            admin.setVerified(true);
            userRepository.save(admin);
            System.out.println("Konto admina pomyslnie utworzone!");
        } else {
            System.out.println("Konto admina juz istnieje!");
        }
        if (userRepository.findByUsername("Ados").isEmpty()) {
            User Ados = new User();
            Ados.setUsername("Ados");
            Ados.setEmail("adamnawrocki@gmail.com");
            Ados.setPassword(passwordEncoder.encode("ados123"));
            Ados.setRole("ROLE_USER");
            Ados.setVerified(true);
            userRepository.save(Ados);
            System.out.println("Konto Ados pomyslnie utworzone!");
        } else {
            System.out.println("Konto Ados juz istnieje!");
        }
        if (userRepository.findByUsername("Natik").isEmpty()) {
            User Natik = new User();
            Natik.setUsername("Natik");
            Natik.setEmail("natkowalska@gmail.com");
            Natik.setPassword(passwordEncoder.encode("natik123"));
            Natik.setRole("ROLE_USER");
            Natik.setVerified(true);
            userRepository.save(Natik);
            System.out.println("Konto Natik pomyslnie utworzone!");
        } else {
            System.out.println("Konto Natik juz istnieje!");
        }
        if (userRepository.findByUsername("baczyk").isEmpty()) {
            User baczyk = new User();
            baczyk.setUsername("baczyk");
            baczyk.setEmail("bartoszkrawczyk@gmail.com");
            baczyk.setPassword(passwordEncoder.encode("bacz123"));
            baczyk.setRole("ROLE_USER");
            baczyk.setVerified(true);
            userRepository.save(baczyk);
            System.out.println("Konto baczyk pomyslnie utworzone!");
        } else {
            System.out.println("Konto baczyk juz istnieje!");
        }
        if (userRepository.findByUsername("ostyga").isEmpty()) {
            User ostyga = new User();
            ostyga.setUsername("ostyga");
            ostyga.setEmail("damianostry@gmail.com");
            ostyga.setPassword(passwordEncoder.encode("dami123"));
            ostyga.setRole("ROLE_USER");
            ostyga.setVerified(true);
            userRepository.save(ostyga);
            System.out.println("Konto ostyga pomyslnie utworzone!");
        } else {
            System.out.println("Konto ostyga juz istnieje!");
        }
        if (userRepository.findByUsername("mega").isEmpty()) {
            User ostyga = new User();
            ostyga.setUsername("mega");
            ostyga.setEmail("megamocny@gmail.com");
            ostyga.setPassword(passwordEncoder.encode("123"));
            ostyga.setRole("ROLE_USER");
            ostyga.setVerified(true);
            userRepository.save(ostyga);
            System.out.println("Konto mega pomyslnie utworzone!");
        } else {
            System.out.println("Konto mega juz istnieje!");
        }
    }
}