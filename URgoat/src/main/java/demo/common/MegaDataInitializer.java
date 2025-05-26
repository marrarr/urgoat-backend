package demo.common;

import com.github.javafaker.Faker;
import demo.czat.Czat;
import demo.czat.CzatRepository;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.ReakcjaRepository;
import demo.reakcja.ReakcjaService;
import demo.security.model.User;
import demo.security.repository.UserRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.wiadomosc.Wiadomosc;
import demo.wiadomosc.WiadomoscRepository;
import demo.znajomy.Znajomy;
import demo.znajomy.ZnajomyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Order(1)
@Profile("prod")
public class MegaDataInitializer implements CommandLineRunner {
    private final Faker faker = new Faker();

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UzytkownikRepository uzytkownikRepository;
    private final CzatRepository czatRepository;
    private final KomentarzRepository komentarzRepository;
    private final PostRepository postRepository;
    private final ReakcjaRepository reakcjaRepository; // TODO dodac ladowanie reakcji
    private final WiadomoscRepository wiadomoscRepository;
    private final ZnajomyRepository znajomyRepository;
    private final UzytkownikService uzytkownikService;
    private final ReakcjaService reakcjaService;

    public MegaDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, UzytkownikRepository uzytkownikRepository, CzatRepository czatRepository, KomentarzRepository komentarzRepository, PostRepository postRepository, ReakcjaRepository reakcjaRepository, WiadomoscRepository wiadomoscRepository, ZnajomyRepository znajomyRepository, UzytkownikService uzytkownikService, ReakcjaService reakcjaService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.uzytkownikRepository = uzytkownikRepository;
        this.czatRepository = czatRepository;
        this.komentarzRepository = komentarzRepository;
        this.postRepository = postRepository;
        this.reakcjaRepository = reakcjaRepository;
        this.wiadomoscRepository = wiadomoscRepository;
        this.znajomyRepository = znajomyRepository;
        this.uzytkownikService = uzytkownikService;
        this.reakcjaService = reakcjaService;
    }

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {

            for (int i = 0; i < 300; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setEmail(user.getUsername() + "@urgggooat.com");
                user.setPassword(passwordEncoder.encode("123"));
                user.setRole("ROLE_USER");
                user.setVerified(true);
                userRepository.save(user);

                // TODO zdjęcie deufaultowe powinno być ładowe do strony, a nie do bazy (żeby jej nie zaśmiecać kopiami)
                ClassPathResource zdjecie = new ClassPathResource("static/avatary_uzytkownikow/default-avatar.png");
                byte[] zdjecieBajty;
                try {
                    zdjecieBajty = zdjecie.getInputStream().readAllBytes();

                    uzytkownikService.dodajUzytkownika(
                            user.getEmail(),
                            faker.name().firstName(),
                            faker.name().lastName(),
                            zdjecieBajty
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            //
            List<Uzytkownik> wszyscyUzytkownicy = uzytkownikRepository.findAll();

            // znajomi
            for (Uzytkownik uzytkownik : wszyscyUzytkownicy) {
                // losujemy ile znajomych ma mieć
                int liczbaZnajomych = faker.number().numberBetween(0, 10);

                for (int j = 0; j < liczbaZnajomych; j++) {
                    // wybierz losowego znajomego innego niż uzytkownik
                    Uzytkownik znajomy;
                    do {
                        znajomy = wszyscyUzytkownicy.get(faker.number().numberBetween(0, wszyscyUzytkownicy.size()));
                    } while (znajomy.equals(uzytkownik));

                    // zapisz relację jeśli jeszcze nie istnieje
                    if (!znajomyRepository.existsByUzytkownikAndUzytkownik2(uzytkownik, znajomy)) {
                        znajomyRepository.save(new Znajomy(uzytkownik, znajomy));
                        znajomyRepository.save(new Znajomy(znajomy, uzytkownik)); // dwukierunkowo
                    }
                }
            }

            // posty i komentarze
            for (Uzytkownik uzytkownik : wszyscyUzytkownicy) {
                double los = Math.random(); // 0.0 do 1.0
                int iloscPostow;

                if (los < 0.5) {
                    iloscPostow = faker.number().numberBetween(0, 2); // 50% przypadków
                } else if (los < 0.85) {
                    iloscPostow = faker.number().numberBetween(3, 6); // 35% przypadków
                } else {
                    iloscPostow = faker.number().numberBetween(7, 13); // 15% przypadków
                }

                int liczbaPostow = iloscPostow;

                for (int i = 0; i < liczbaPostow; i++) {
                    String trescPostu = faker.lorem().sentence();

                    Post post = new Post(uzytkownik, trescPostu);
                    postRepository.save(post);

                    // dodajemy komentarze do tego posta
                    int liczbaKomentarzy = faker.number().numberBetween(0, 4);
                    for (int k = 0; k < liczbaKomentarzy; k++) {
                        Uzytkownik komentujacy = wszyscyUzytkownicy.get(faker.number().numberBetween(0, wszyscyUzytkownicy.size()));
                        String trescKomentarza = faker.lorem().sentence();

                        Komentarz komentarz = new Komentarz(post, komentujacy, trescKomentarza);
                        komentarzRepository.save(komentarz);
                    }
                }

                // Ograniczenie ilości
                if (postRepository.count() > 300) {
                    break;
                }
            }

            // reakcje
            List<Post> wszystkiePosty = postRepository.findAll();
            for (Post post : wszystkiePosty) {
                //TODO osobna metoda
                double los = Math.random(); // 0.0 do 1.0
                int iloscReakcji;

                if (los < 0.45) {
                    iloscReakcji = faker.number().numberBetween(0, 20); // 45% przypadków
                } else if (los < 0.75) {
                    iloscReakcji = faker.number().numberBetween(20, 50); // 30% przypadków
                } else if (los < 0.97) {
                    iloscReakcji = faker.number().numberBetween(50, 100); // 22% przypadków
                } else {
                    iloscReakcji = faker.number().numberBetween(100, 200); // 3% przypadków
                }
                //

                Set<Uzytkownik> juzReagowali = new HashSet<>();
                for (int i = 0; i < iloscReakcji; i++) {
                    Uzytkownik reagujacy = wszyscyUzytkownicy.get(faker.number().numberBetween(0, wszyscyUzytkownicy.size()));
                    if (reagujacy.equals(post.getUzytkownik()) || juzReagowali.contains(reagujacy)) {
                        i--;
                        continue;
                    } else {
                        juzReagowali.add(reagujacy);
                    }

                    reakcjaService.dodajReakcje(
                            reagujacy.getUzytkownikID(),
                            (long) post.getPostID(),
                            null,
                            faker.number().numberBetween(1, 4)
                    );
                }
            }

            // czaty i wiadomości
            for (int i = 0; i < 100; i++) { // stwórzmy 100 czatów
                Uzytkownik u1 = wszyscyUzytkownicy.get(faker.number().numberBetween(0, wszyscyUzytkownicy.size()));
                Uzytkownik u2;
                do {
                    u2 = wszyscyUzytkownicy.get(faker.number().numberBetween(0, wszyscyUzytkownicy.size()));
                } while (u1.equals(u2));

                Czat czat = new Czat(List.of(u1, u2));
                czatRepository.save(czat);

                int liczbaWiadomosci = faker.number().numberBetween(1, 10);
                for (int j = 0; j < liczbaWiadomosci; j++) {
                    String tekst = faker.lorem().sentence();
                    Wiadomosc wiadomosc = new Wiadomosc(czat, tekst);
                    wiadomoscRepository.save(wiadomosc);
                }
            }

            //TODO
            // grupy




            System.out.println("=====================================");
            System.out.println("=====================================");
            System.out.println("=====================================");
            System.out.println("    >> KONIEC WCZYTYWANIA MEGA <<");
            System.out.println("=====================================");
            System.out.println("=====================================");
            System.out.println("=====================================");
        }
    }
}
