package demo.common;

import demo.czat.Czat;
import demo.czat.CzatRepository;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.Reakcja;
import demo.reakcja.ReakcjaRepository;
import demo.security.model.User;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.wiadomosc.Wiadomosc;
import demo.wiadomosc.WiadomoscRepository;
import demo.znajomy.Znajomy;
import demo.znajomy.ZnajomyRepository;
import demo.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@Order(2)
public class DataLoader implements CommandLineRunner {

    private final UzytkownikRepository uzytkownikRepository;
    private final CzatRepository czatRepository;
    private final KomentarzRepository komentarzRepository;
    private final PostRepository postRepository;
    private final ReakcjaRepository reakcjaRepository;
    private final WiadomoscRepository wiadomoscRepository;
    private final ZnajomyRepository znajomyRepository;
    private final UzytkownikService uzytkownikService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UzytkownikRepository uzytkownikRepository,
                      CzatRepository czatRepository,
                      KomentarzRepository komentarzRepository,
                      PostRepository postRepository,
                      ReakcjaRepository reakcjaRepository,
                      WiadomoscRepository wiadomoscRepository,
                      ZnajomyRepository znajomyRepository,
                      UserRepository userRepository, UzytkownikService uzytkownikService, PasswordEncoder passwordEncoder) {
        this.uzytkownikRepository = uzytkownikRepository;
        this.czatRepository = czatRepository;
        this.komentarzRepository = komentarzRepository;
        this.postRepository = postRepository;
        this.reakcjaRepository = reakcjaRepository;
        this.wiadomoscRepository = wiadomoscRepository;
        this.znajomyRepository = znajomyRepository;
        this.userRepository = userRepository;
        this.uzytkownikService = uzytkownikService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        System.out.println("=== WYKONUJE SIĘ DataLoader ===");

        // Jeśli admina nie ma w bazie to załaduj te dane
        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            admin.setVerified(true);
            userRepository.save(admin);

            User Ados = new User();
            Ados.setUsername("Ados");
            Ados.setEmail("adamnawrocki@gmail.com");
            Ados.setPassword(passwordEncoder.encode("ados123"));
            Ados.setRole("ROLE_USER");
            Ados.setVerified(true);
            userRepository.save(Ados);

            User Natik = new User();
            Natik.setUsername("Natik");
            Natik.setEmail("natkowalska@gmail.com");
            Natik.setPassword(passwordEncoder.encode("natik123"));
            Natik.setRole("ROLE_USER");
            Natik.setVerified(true);
            userRepository.save(Natik);

            User baczyk = new User();
            baczyk.setUsername("baczyk");
            baczyk.setEmail("bartoszkrawczyk@gmail.com");
            baczyk.setPassword(passwordEncoder.encode("bacz123"));
            baczyk.setRole("ROLE_USER");
            baczyk.setVerified(true);
            userRepository.save(baczyk);

            User ostyga = new User();
            ostyga.setUsername("ostyga");
            ostyga.setEmail("damianostry@gmail.com");
            ostyga.setPassword(passwordEncoder.encode("dami123"));
            ostyga.setRole("ROLE_USER");
            ostyga.setVerified(true);
            userRepository.save(ostyga);

            User mega = new User();
            mega.setUsername("mega");
            mega.setEmail("megamocny@gmail.com");
            mega.setPassword(passwordEncoder.encode("123"));
            mega.setRole("ROLE_USER");
            mega.setVerified(true);
            userRepository.save(mega);

            //sigma boys
            ClassPathResource zdjecie = new ClassPathResource("static/avatary_uzytkownikow/avatar4.png");
            byte[] zdjecieBajty;
            try {
                zdjecieBajty = zdjecie.getInputStream().readAllBytes();

                uzytkownikService.dodajUzytkownika(
                        "adamnawrocki@gmail.com",
                        "Miłosz",
                        "Nawrocki",
                        zdjecieBajty
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            zdjecie = new ClassPathResource("static/avatary_uzytkownikow/avatar3.png");
            try {
                zdjecieBajty = zdjecie.getInputStream().readAllBytes();

                uzytkownikService.dodajUzytkownika(
                        "natkowalska@gmail.com",
                        "Wiktor",
                        "Kowalska",
                        zdjecieBajty
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            zdjecie = new ClassPathResource("static/avatary_uzytkownikow/avatar2.png");
            try {
                zdjecieBajty = zdjecie.getInputStream().readAllBytes();

                uzytkownikService.dodajUzytkownika(
                        "bartoszkrawczyk@gmail.com",
                        "Radek",
                        "Krawczyk",
                        zdjecieBajty
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            zdjecie = new ClassPathResource("static/avatary_uzytkownikow/avatar1.png");
            try {
                zdjecieBajty = zdjecie.getInputStream().readAllBytes();

                uzytkownikService.dodajUzytkownika(
                        "damianostry@gmail.com",
                        "Seba",
                        "Ostry",
                        zdjecieBajty
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            zdjecie = new ClassPathResource("static/avatary_uzytkownikow/avatar2.png");
            try {
                zdjecieBajty = zdjecie.getInputStream().readAllBytes();

                uzytkownikService.dodajUzytkownika(
                        "megamocny@gmail.com",
                        "Marek",
                        "Piorun",
                        zdjecieBajty
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            Uzytkownik user1 = uzytkownikRepository.findByEmail("adamnawrocki@gmail.com");
            Uzytkownik user2 = uzytkownikRepository.findByEmail("natkowalska@gmail.com");
            Uzytkownik user3 = uzytkownikRepository.findByEmail("bartoszkrawczyk@gmail.com");
            Uzytkownik user4 = uzytkownikRepository.findByEmail("damianostry@gmail.com");
            Uzytkownik user5 = uzytkownikRepository.findByEmail("megamocny@gmail.com");

            //znajomi
            Znajomy znajomy1 = new Znajomy(user1, user2);
            Znajomy znajomy2 = new Znajomy(user2, user1);
            Znajomy znajomy3 = new Znajomy(user1, user5);
            Znajomy znajomy4 = new Znajomy(user5, user1);
            Znajomy znajomy5 = new Znajomy(user5, user3);
            Znajomy znajomy6 = new Znajomy(user3, user5);
            Znajomy znajomy7 = new Znajomy(user5, user2);
            Znajomy znajomy8 = new Znajomy(user2, user5);

            znajomyRepository.save(znajomy1);
            znajomyRepository.save(znajomy2);
            znajomyRepository.save(znajomy3);
            znajomyRepository.save(znajomy4);
            znajomyRepository.save(znajomy5);
            znajomyRepository.save(znajomy6);
            znajomyRepository.save(znajomy7);
            znajomyRepository.save(znajomy8);

            //czat
            Czat czat1 = new Czat(List.of(user1, user2));

            czatRepository.save(czat1);

            //wiadomości czatowe
            Wiadomosc wiadomosc1 = new Wiadomosc(czat1, "Cześć, co tam?");
            Wiadomosc wiadomosc2 = new Wiadomosc(czat1, "Dobrze a co u cb?");

            wiadomoscRepository.save(wiadomosc1);
            wiadomoscRepository.save(wiadomosc2);

            //Posty
            List<Post> posty = new ArrayList<>();
            posty.add(new Post(user1, "UwU neko nyaa~~"));
            posty.add(new Post(user3, "Drugi post testowy!"));
            posty.add(new Post(user3, "nie ma takiego dowodu!! jest nagroda pol miliona eurogabek dla czlowieka ktory wskaze CIEŃ dowodu...!!!"));
            posty.add(new Post(user5, "wbijam na bombsite a na na nanana na"));
            posty.add(new Post(user2, "https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            posty.add(new Post(user4, "lbjgljlsja"));

            postRepository.saveAll(posty);

            //Komentarze
            List<Komentarz> komentarze = new ArrayList<>();
            komentarze.add(new Komentarz(posty.get(0), user2, "Czy test pierwszy przebiegl pomyslnie"));
            komentarze.add(new Komentarz(posty.get(1), user4, "Za duzo tych testow"));
            komentarze.add(new Komentarz(posty.get(2), user2, "co ty gadasz czlowieku..."));
            komentarze.add(new Komentarz(posty.get(2), user3, "cichaj babo"));
            komentarze.add(new Komentarz(posty.get(2), user4, "zgadzam sie z toba Bartosz!!"));

            komentarzRepository.saveAll(komentarze);


            //Reakcje
            List<Reakcja> reakcje = new ArrayList<>();

            reakcje.add(new Reakcja(user1, null, posty.get(0), 1));
            reakcje.add(new Reakcja(user2, null, posty.get(0), 2));
            reakcje.add(new Reakcja(user3, null, posty.get(0), 1));
            reakcje.add(new Reakcja(user2, null, posty.get(2), 1));
            reakcje.add(new Reakcja(user4, komentarze.get(3), null, 1));
            reakcje.add(new Reakcja(user1, komentarze.get(3), null, 1));
            reakcje.add(new Reakcja(user2, komentarze.get(3), null, 2));

            reakcjaRepository.saveAll(reakcje);
        }
    }
}
