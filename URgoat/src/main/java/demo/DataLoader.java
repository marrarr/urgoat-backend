package demo;
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
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@Order(2)
public class DataLoader implements ApplicationRunner{

    private UzytkownikRepository uzytkownikRepository;
    private CzatRepository czatRepository;
    private KomentarzRepository komentarzRepository;
    private PostRepository postRepository ;
    private ReakcjaRepository reakcjaRepository;
    private WiadomoscRepository wiadomoscRepository;
    private ZnajomyRepository znajomyRepository;
    private UserRepository userRepository;
    private final UzytkownikService uzytkownikService;

    @Autowired
    public DataLoader(UzytkownikRepository uzytkownikRepository,
                      CzatRepository czatRepository,
                      KomentarzRepository komentarzRepository,
                      PostRepository postRepository,
                      ReakcjaRepository reakcjaRepository,
                      WiadomoscRepository wiadomoscRepository,
                      ZnajomyRepository znajomyRepository,
                      UserRepository userRepository, UzytkownikService uzytkownikService) {
        this.uzytkownikRepository = uzytkownikRepository;
        this.czatRepository = czatRepository;
        this.komentarzRepository = komentarzRepository;
        this.postRepository = postRepository;
        this.reakcjaRepository = reakcjaRepository;
        this.wiadomoscRepository = wiadomoscRepository;
        this.znajomyRepository = znajomyRepository;
        this.userRepository = userRepository;
        this.uzytkownikService = uzytkownikService;
    }

    public void run(ApplicationArguments args) {

        System.out.println("=== WYKONUJE SIĘ DataLoader ===");

        if (uzytkownikRepository.count() == 0) //Przykladowe dane dodajemy tylko jak tabela jest pusta
        {
            System.out.println("=== WYKONUJE SIĘ DataLoader ===");
            //sigma boys
            ClassPathResource zdjecie = new ClassPathResource("static/avatary_uzytkownikow/avatar4.png");
            byte[] zdjecieBajty;
            try {
                zdjecieBajty = zdjecie.getInputStream().readAllBytes();

                uzytkownikService.dodajUzytkownika(
                        "adamnawrocki@gmail.com",
                        "Adam",
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
                        "Natalia",
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
                        "Bartosz",
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
                        "Damian",
                        "Ostry",
                        zdjecieBajty
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            Uzytkownik user1 = uzytkownikRepository.findByEmail("adamnawrocki@gmail.com");
            Uzytkownik user2 = uzytkownikRepository.findByEmail("natkowalska@gmail.com");
            Uzytkownik user3 = uzytkownikRepository.findByEmail("bartoszkrawczyk@gmail.com");
            Uzytkownik user4 = uzytkownikRepository.findByEmail("damianostry@gmail.com");

            //znajomi
            Znajomy znajomy1 = new Znajomy(user1, user2);
            Znajomy znajomy2 = new Znajomy(user2, user1);

            znajomyRepository.save(znajomy1);
            znajomyRepository.save(znajomy2);

            //czat
            Czat czat1 = new Czat(List.of(user1, user2));

            czatRepository.save(czat1);

            //wiadomości czatowe
            Wiadomosc wiadomosc1 = new Wiadomosc(czat1, "Cześć, co tam?");
            Wiadomosc wiadomosc2 = new Wiadomosc(czat1, "Dobrze a co u cb?");

            wiadomoscRepository.save(wiadomosc1);
            wiadomoscRepository.save(wiadomosc2);

            //Posty
            Post post1 = new Post(user1,"Pierwszy post testowy!");
            Post post2 = new Post(user3,"Drugi post testowy!");
            Post post3 = new Post(user3,"nie ma takiego dowodu!! jest nagroda pol miliona eurogabek dla czlowieka ktory wskaze CIEŃ dowodu...!!!");

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            //Komentarze
            Komentarz komentarz1 = new Komentarz(post1,user2,"Czy test pierwszy przebiegl pomyslnie");
            Komentarz komentarz2 = new Komentarz(post2,user4,"Za duzo tych testow");
            Komentarz komentarz3 = new Komentarz(post3,user2,"co ty gadasz czlowieku...");
            Komentarz komentarz4 = new Komentarz(post3,user3,"cichaj babo");
            Komentarz komentarz5 = new Komentarz(post3,user4,"zgadzam sie z toba Bartosz!!");

            komentarzRepository.save(komentarz1);
            komentarzRepository.save(komentarz2);
            komentarzRepository.save(komentarz3);
            komentarzRepository.save(komentarz4);
            komentarzRepository.save(komentarz5);

            //Reakcje
            Reakcja reakcja1 = new Reakcja(user1, null, post1, 1);
            Reakcja reakcja2 = new Reakcja(user2, null, post1, 2);
            Reakcja reakcja3 = new Reakcja(user3, null, post1, 1);
            Reakcja reakcja4 = new Reakcja(user2, null, post3, 1);
            Reakcja reakcja5 = new Reakcja(user4, komentarz4, null, 1);
            Reakcja reakcja6 = new Reakcja(user1, komentarz4, null, 1);
            Reakcja reakcja7 = new Reakcja(user2, komentarz4, null, 2);

            reakcjaRepository.save(reakcja1);
            reakcjaRepository.save(reakcja2);
            reakcjaRepository.save(reakcja3);
            reakcjaRepository.save(reakcja4);
            reakcjaRepository.save(reakcja5);
            reakcjaRepository.save(reakcja6);
            reakcjaRepository.save(reakcja7);
        }
    }
}
