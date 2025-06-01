package demo.common;

import demo.czat.Czat;
import demo.czat.CzatRepository;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.komentarz.KomentarzService;
import demo.log.LogOperacja;
import demo.log.URgoatLogger;
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

/**
 * Komponent odpowiedzialny za wstępne ładowanie danych testowych do bazy danych.
 * Inicjalizuje użytkowników, posty, komentarze, wiadomości, czaty, znajomych oraz reakcje przy uruchamianiu aplikacji.
 */
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
    private final KomentarzService komentarzService;

    /**
     * Konstruktor komponentu DataLoader, wstrzykujący wymagane zależności.
     *
     * @param uzytkownikRepository repozytorium użytkowników
     * @param czatRepository       repozytorium czatów
     * @param komentarzRepository  repozytorium komentarzy
     * @param postRepository       repozytorium postów
     * @param reakcjaRepository    repozytorium reakcji
     * @param wiadomoscRepository  repozytorium wiadomości
     * @param znajomyRepository    repozytorium znajomych
     * @param userRepository       repozytorium użytkowników systemu zabezpieczeń
     * @param uzytkownikService    serwis użytkowników
     * @param passwordEncoder      koder haseł
     * @param komentarzService
     */
    @Autowired
    public DataLoader(UzytkownikRepository uzytkownikRepository,
                      CzatRepository czatRepository,
                      KomentarzRepository komentarzRepository,
                      PostRepository postRepository,
                      ReakcjaRepository reakcjaRepository,
                      WiadomoscRepository wiadomoscRepository,
                      ZnajomyRepository znajomyRepository,
                      UserRepository userRepository,
                      UzytkownikService uzytkownikService,
                      PasswordEncoder passwordEncoder, KomentarzService komentarzService) {
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
        this.komentarzService = komentarzService;
    }

    /**
     * Metoda uruchamiana automatycznie podczas startu aplikacji, ładująca dane testowe do bazy danych.
     * Tworzy użytkowników, ich profile, relacje znajomych, czaty, wiadomości, posty, komentarze oraz reakcje,
     * jeśli użytkownik "admin" nie istnieje w bazie danych.
     *
     * @param args argumenty wiersza poleceń
     * @throws RuntimeException jeśli wystąpi błąd podczas wczytywania plików zdjęć profilowych
     */
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
            URgoatLogger.uzytkownikInfo(
                    "Stworzono konto userid=" + admin.getId(),
                    "server",
                    LogOperacja.DODAWANIE
            );

            User Ados = new User();
            Ados.setUsername("Ados");
            Ados.setEmail("adamnawrocki@gmail.com");
            Ados.setPassword(passwordEncoder.encode("ados123"));
            Ados.setRole("ROLE_USER");
            Ados.setVerified(true);
            userRepository.save(Ados);
            URgoatLogger.uzytkownikInfo(
                    "Stworzono konto userid=" + Ados.getId(),
                    "server",
                    LogOperacja.DODAWANIE
            );

            User Natik = new User();
            Natik.setUsername("Natik");
            Natik.setEmail("natkowalska@gmail.com");
            Natik.setPassword(passwordEncoder.encode("natik123"));
            Natik.setRole("ROLE_USER");
            Natik.setVerified(true);
            userRepository.save(Natik);
            URgoatLogger.uzytkownikInfo(
                    "Stworzono konto userid=" + Natik.getId(),
                    "server",
                    LogOperacja.DODAWANIE
            );

            User baczyk = new User();
            baczyk.setUsername("baczyk");
            baczyk.setEmail("bartoszkrawczyk@gmail.com");
            baczyk.setPassword(passwordEncoder.encode("bacz123"));
            baczyk.setRole("ROLE_USER");
            baczyk.setVerified(true);
            userRepository.save(baczyk);
            URgoatLogger.uzytkownikInfo(
                    "Stworzono konto userid=" + baczyk.getId(),
                    "server",
                    LogOperacja.DODAWANIE
            );

            User ostyga = new User();
            ostyga.setUsername("ostyga");
            ostyga.setEmail("damianostry@gmail.com");
            ostyga.setPassword(passwordEncoder.encode("dami123"));
            ostyga.setRole("ROLE_USER");
            ostyga.setVerified(true);
            userRepository.save(ostyga);
            URgoatLogger.uzytkownikInfo(
                    "Stworzono konto userid=" + ostyga.getId(),
                    "server",
                    LogOperacja.DODAWANIE
            );

            User mega = new User();
            mega.setUsername("mega");
            mega.setEmail("megamocny@gmail.com");
            mega.setPassword(passwordEncoder.encode("123"));
            mega.setRole("ROLE_USER");
            mega.setVerified(true);
            userRepository.save(mega);
            URgoatLogger.uzytkownikInfo(
                    "Stworzono konto userid=" + mega.getId(),
                    "server",
                    LogOperacja.DODAWANIE
            );


            //
            //sigma boys
            //

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

            zdjecie = new ClassPathResource("static/avatary_uzytkownikow/avatar5.jpg");
            try {
                zdjecieBajty = zdjecie.getInputStream().readAllBytes();

                uzytkownikService.dodajUzytkownika(
                        "megamocny@gmail.com",
                        "Gro",
                        "Goroth",
                        zdjecieBajty
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // potrzebne do tworzenia relacji niżej
            Uzytkownik user1 = uzytkownikRepository.findByEmail("adamnawrocki@gmail.com");
            Uzytkownik user2 = uzytkownikRepository.findByEmail("natkowalska@gmail.com");
            Uzytkownik user3 = uzytkownikRepository.findByEmail("bartoszkrawczyk@gmail.com");
            Uzytkownik user4 = uzytkownikRepository.findByEmail("damianostry@gmail.com");
            Uzytkownik user5 = uzytkownikRepository.findByEmail("megamocny@gmail.com");


            //
            //znajomi
            //
            List<Znajomy> znajomi = new ArrayList<>();

            znajomi.add(new Znajomy(user1, user2));
            znajomi.add(new Znajomy(user2, user1));
            znajomi.add(new Znajomy(user1, user5));
            znajomi.add(new Znajomy(user5, user1));
            znajomi.add(new Znajomy(user5, user3));
            znajomi.add(new Znajomy(user3, user5));
            znajomi.add(new Znajomy(user5, user2));
            znajomi.add(new Znajomy(user2, user5));

            znajomyRepository.saveAll(znajomi);

            for (Znajomy z : znajomi) {
                URgoatLogger.uzytkownikFine(
                        "Dodano nowa znajomość id=" + z.getZnajomyID() +
                                " między uzytkownikID=" + z.getUzytkownik().getUzytkownikID() +
                                " i uzytkownik2ID=" + z.getUzytkownik2().getUzytkownikID(),
                        "server",
                        LogOperacja.DODAWANIE
                );
            }


            //
            //czat
            //

            Czat czat1 = new Czat(List.of(user1, user2));
            Czat czat2 = new Czat(List.of(user3, user5));
            Czat czat3 = new Czat(List.of(user1, user5));

            czatRepository.save(czat1);
            czatRepository.save(czat2);
            czatRepository.save(czat3);

            URgoatLogger.uzytkownikFine(
                    "Utworzono nowy czat id=" + czat1.getCzatID(),
                    "server",
                    LogOperacja.DODAWANIE
            );
            URgoatLogger.uzytkownikFine(
                    "Utworzono nowy czat id=" + czat2.getCzatID(),
                    "server",
                    LogOperacja.DODAWANIE
            );
            URgoatLogger.uzytkownikFine(
                    "Utworzono nowy czat id=" + czat3.getCzatID(),
                    "server",
                    LogOperacja.DODAWANIE
            );


            //
            //wiadomości czatowe
            //

            // Wiadomości dla czatu 1 (Adam i Natalia - 5 wiadomości)
            List<Wiadomosc> wiadomosciCzat1 = List.of(
                    new Wiadomosc(czat1, "Hej Natalia, jak tam weekendowe plany?", null, user1),
                    new Wiadomosc(czat1, "Cześć Adam! Właśnie wróciłam z rodzicami znad jeziora, super było! A u Ciebie co słychać?", null, user2),
                    new Wiadomosc(czat1, "U mnie spokojnie, przygotowuję się do tego projektu o którym rozmawialiśmy. Pamiętasz może gdzie znajdę te materiały?", null, user1),
                    new Wiadomosc(czat1, "Jasne, wysłałam Ci link na maila. Sprawdź folder 'oferty' w załącznikach.", null, user2),
                    new Wiadomosc(czat1, "Dzięki wielkie! Jak znajdę coś ciekawego w tych materiałach to dam znać.", null, user1)
            );

            // Wiadomości dla czatu 2 (Bartosz i Gro Goroth - 13 wiadomości)
            List<Wiadomosc> wiadomosciCzat2 = List.of(
                    new Wiadomosc(czat2, "Ej Gro, gramy dziś w CSa?", null, user3),
                    new Wiadomosc(czat2, "Oczywiście że gramy! O której zbiórka?", null, user5),
                    new Wiadomosc(czat2, "Może 20:00? Mam jeszcze trochę roboty do zrobienia.", null, user3),
                    new Wiadomosc(czat2, "Spoko, dawaj na Discordzie jak będziesz gotowy.", null, user5),
                    new Wiadomosc(czat2, "Słuchaj, a pamiętasz tego gościa co grał z nami w zeszłym tygodniu?", null, user3),
                    new Wiadomosc(czat2, "Tego co ciągle krzyczał w mikrofon?", null, user5),
                    new Wiadomosc(czat2, "Tak właśnie! Właśnie mnie zaprosił do drużyny XD", null, user3),
                    new Wiadomosc(czat2, "Nie no, odmów. Męczył nas całą grę.", null, user5),
                    new Wiadomosc(czat2, "Masz rację. To tylko my dwaj dzisiaj, ok?", null, user3),
                    new Wiadomosc(czat2, "Zgoda. Będę trenował nowe smoki na Mirage.", null, user5),
                    new Wiadomosc(czat2, "Pokazesz mi jak to robisz? Zawsze ginę jak próbuję.", null, user3),
                    new Wiadomosc(czat2, "Spoko, pokażę Ci na prywatnym serwerze.", null, user5),
                    new Wiadomosc(czat2, "Super, dzięki! To do 20:00.", null, user3),
                    new Wiadomosc(czat2, "Do zobaczenia. Przygotuj słuchawki, bo będziemy niszczyć!", null, user5)
            );

            // Wiadomości dla czatu (Ados i Gro Goroth)
            List<Wiadomosc> wiadomosciCzat3 = List.of(
                    new Wiadomosc(czat3, "Gro, słyszałeś o nowym patchu do CS2?", null, user1),
                    new Wiadomosc(czat3, "Oczywiście! Buff na AWP to czyste szaleństwo XD", null, user5),
                    new Wiadomosc(czat3, "No właśnie, już widzę te kidsy z nowymi scope'ami...", null, user1),
                    new Wiadomosc(czat3, "Mam nowy setup do streamowania, zobaczysz jak ich zniszczymy wieczorem", null, user5),
                    new Wiadomosc(czat3, "O której start? Muszę tylko skończyć deployować ten projekt", null, user1),
                    new Wiadomosc(czat3, "21:00? Mamy wtedy turniej wśród znajomych", null, user5),
                    new Wiadomosc(czat3, "Dajesz! Przygotuj jakieś dobre skiny na show", null, user1),
                    new Wiadomosc(czat3, "Mam nowy karambit - wygląda jakby był z Diablo", null, user5),
                    new Wiadomosc(czat3, "Zajebisty! A pamiętasz jeszcze jak graliśmy w 1.6 na lanach?", null, user1),
                    new Wiadomosc(czat3, "Haha, te czasy! Monitor CRT i cola w plastikowych butelkach", null, user5),
                    new Wiadomosc(czat3, "Dokładnie! Teraz to same RGB i energy drinki", null, user1),
                    new Wiadomosc(czat3, "I lepsze haxy... znaczy umiejętności oczywiście ;)", null, user5),
                    new Wiadomosc(czat3, "No tak, 'umiejętności'... widziałem twoje ostatnie wallbangi", null, user1),
                    new Wiadomosc(czat3, "To było legit! Miałem dobry gaming chair!", null, user5),
                    new Wiadomosc(czat3, "XD No dobra, lecę kończyć deploy. Do wieczora!", null, user1),
                    new Wiadomosc(czat3, "Do zobaczenia na dust2 stary noobie!", null, user5)
            );


            // Zapis wszystkich wiadomości
            List<Wiadomosc> wszystkieWiadomosci = new ArrayList<>();
            wszystkieWiadomosci.addAll(wiadomosciCzat1);
            wszystkieWiadomosci.addAll(wiadomosciCzat2);
            wszystkieWiadomosci.addAll(wiadomosciCzat3);

            wiadomoscRepository.saveAll(wszystkieWiadomosci);

            for (Wiadomosc w : wszystkieWiadomosci) {
                String czyZdjecie = (w.getZdjecie() == null) ? "brak" : "dodano";
                String wiadomosc = "Użytkownik [" + w.getUzytkownik().getPseudonim() +
                        "] wysłał wiadomość: CzatID=" + w.getCzat().getCzatID() +
                        " ; Zdjecie=" + czyZdjecie +
                        " ; Dlugosc=" + w.getTresc().length();

                URgoatLogger.uzytkownikInfo(
                        wiadomosc,
                        w.getUzytkownik().getPseudonim(),
                        LogOperacja.DODAWANIE
                );
            }


            //
            // Posty
            //
            List<Post> posty = new ArrayList<>();

            posty.add(new Post(user1, "Właśnie wróciłem z super wycieczki w góry! Ktoś jeszcze był ostatnio na szlaku?"));
            posty.add(new Post(user2, "Polecacie jakieś dobre książki na wiosnę? Szukam czegoś lekkiego do czytania."));
            posty.add(new Post(user3, "Ostatnio odkryłem nową kawiarnię w centrum - mają najlepszą latte w mieście!"));
            posty.add(new Post(user4, "Kto gra w nowego Counter-Strike'a? Możemy zrobić team wieczorem."));
            posty.add(new Post(user5, "W końcu udało mi się przebiec 10km bez zatrzymania! 💪"));
            posty.add(new Post(user1, "Czy tylko mi się wydaje, że w tym roku wiosna przyszła później niż zwykle?"));
            posty.add(new Post(user2, "Zrobiłam dziś domowe pierogi - wyszły idealnie!"));
            posty.add(new Post(user3, "Szukam pomysłu na weekendowy wyjazd - macie jakieś sprawdzone miejsca?"));
            posty.add(new Post(user4, "Ogląda ktoś nowy sezon 'Gry o Tron'? Bez spoilów proszę!"));
            posty.add(new Post(user5, "W biurze mamy dzisiaj dzień piżamy - czuję się jak w podstawówce 😄"));
            posty.add(new Post(user1, "Zepsuł mi się laptop - polecicie dobrego serwis w Warszawie?"));
            posty.add(new Post(user2, "Właśnie skończyłam kurs programowania! Teraz czas na praktykę."));
            posty.add(new Post(user3, "Ktoś wie dlaczego w autobusach 131 jest ostatnio taki tłok?"));
            posty.add(new Post(user4, "Gramy w piłkę w sobotę - kto się przyłączy?"));
            posty.add(new Post(user5, "Znalazłem super przepis na wegańskie brownie - nawet mięsożercy będą zachwyceni!"));

            postRepository.saveAll(posty);

            for (Post p : posty) {
                URgoatLogger.uzytkownikInfo("Dodano post id=" + p.getPostID() + ", dlugosc=" + p.getTresc().length(),
                        p.getUzytkownik().getPseudonim(),
                        LogOperacja.DODAWANIE);
            }


            //
            // Komentarze
            //
            List<Komentarz> komentarze = new ArrayList<>();

            // Post 1 - Góry
            komentarze.add(new Komentarz(posty.get(0), user2, "Byłam w zeszłym miesiącu w Bieszczadach - polecam szlak na Połoninę Wetlińską!"));
            komentarze.add(new Komentarz(posty.get(0), user3, "Ja właśnie planuję wyjazd w Tatry w przyszłym tygodniu. Jaka była pogoda?"));
            komentarze.add(new Komentarz(posty.get(0), user4, "Zazdroszczę! Od roku nie byłem w górach przez pracę :("));
            komentarze.add(new Komentarz(posty.get(0), user5, "Pamiętajcie o dobrych butach - w zeszłym roku złamałem nogę przez złe obuwie."));

            // Post 2 - Książki
            komentarze.add(new Komentarz(posty.get(1), user1, "Ostatnio czytałem 'Dom nad rozlewiskiem' - bardzo przyjemna lektura!"));
            komentarze.add(new Komentarz(posty.get(1), user3, "Jeśli lubisz kryminały, to polecam nowość Remigiusza Mroza."));
            komentarze.add(new Komentarz(posty.get(1), user5, "A ja zawsze na wiosnę wracam do 'Małego Księcia' - nigdy się nie nudzi."));

            // Post 3 - Kawiarnia
            komentarze.add(new Komentarz(posty.get(2), user1, "Gdzie dokładnie jest ta kawiarnia? Szukam nowego miejsca do pracy zdalnej."));
            komentarze.add(new Komentarz(posty.get(2), user2, "Ceny są przystępne? Ostatnio wiele miejsc podniosło ceny o 30%..."));
            komentarze.add(new Komentarz(posty.get(2), user4, "Mają tam też dobre ciasta?"));
            komentarze.add(new Komentarz(posty.get(2), user5, "Potwierdzam - latte rzeczywiście świetne!"));
            komentarze.add(new Komentarz(posty.get(2), user3, "Jest przy ul. Kawiarnianej 15, otwarte do 20:00."));

            // Post 4 - Counter-Strike
            komentarze.add(new Komentarz(posty.get(3), user1, "Jestem chętny! Mój nick to Ados123"));
            komentarze.add(new Komentarz(posty.get(3), user2, "A ja gram tylko w Among Us 😅"));
            komentarze.add(new Komentarz(posty.get(3), user5, "Gram od wczoraj - jeszcze się uczę, ale możesz mnie dodać."));
            komentarze.add(new Komentarz(posty.get(3), user3, "O której godzinie? Mogę dołączyć po 20:00."));

            // Post 5 - Bieganie
            komentarze.add(new Komentarz(posty.get(4), user1, "Gratulacje! Ja wciąż walczę z 5km..."));
            komentarze.add(new Komentarz(posty.get(4), user2, "Jak długo trenujesz?"));
            komentarze.add(new Komentarz(posty.get(4), user3, "Pamiętaj o rozciąganiu po biegu!"));
            komentarze.add(new Komentarz(posty.get(4), user4, "Może kiedyś zrobimy wspólny bieg?"));
            komentarze.add(new Komentarz(posty.get(4), user5, "Dzięki wszystkim! Trenuję od 3 miesięcy, zaczynałem od 1km."));

            // Post 6 - Wiosna
            komentarze.add(new Komentarz(posty.get(5), user2, "U mnie w ogóle jeszcze śnieg leżał do zeszłego tygodnia!"));
            komentarze.add(new Komentarz(posty.get(5), user4, "Statystycznie w tym roku jest cieplej niż zwykle, ale faktycznie rośliny później zakwitły."));

            // Post 7 - Pierogi
            komentarze.add(new Komentarz(posty.get(6), user1, "Jakie nadzienie?"));
            komentarze.add(new Komentarz(posty.get(6), user3, "Zrób zdjęcie następnym razem!"));
            komentarze.add(new Komentarz(posty.get(6), user5, "Moja babcia ma najlepszy przepis - jak chcesz, mogę podesłać."));
            komentarze.add(new Komentarz(posty.get(6), user2, "Z mięsem i kapustą z grzybami. Zdjęcie następnym razem obiecuję!"));

            // Post 8 - Weekendowy wyjazd
            komentarze.add(new Komentarz(posty.get(7), user1, "Polecam Mazury - teraz przed sezonem będzie spokojnie."));
            komentarze.add(new Komentarz(posty.get(7), user4, "A ja wolę góry - świeże powietrze i widoki!"));
            komentarze.add(new Komentarz(posty.get(7), user5, "Jeśli masz budżet, to Wrocław ma super klimat na weekend."));

            // Post 9 - Gra o Tron
            komentarze.add(new Komentarz(posty.get(8), user1, "Dopiero zaczynam oglądać - nie zdradzajcie nic!"));
            komentarze.add(new Komentarz(posty.get(8), user2, "Ja czekam aż wyjdzie cały sezon, żeby obejrzeć jednym haustem."));
            komentarze.add(new Komentarz(posty.get(8), user3, "Oglądam, ale nie podoba mi się nowa postać..."));
            komentarze.add(new Komentarz(posty.get(8), user5, "Bez spoilów, ale odcinek 3 jest najlepszy!"));

            // Post 10 - Dzień piżamy
            komentarze.add(new Komentarz(posty.get(9), user1, "U nas w firmie mieliśmy dzień hawajski - było zabawnie!"));
            komentarze.add(new Komentarz(posty.get(9), user2, "A ja dzisiaj home office - piżama od rana 😁"));
            komentarze.add(new Komentarz(posty.get(9), user3, "Macie fajną atmosferę w pracy!"));
            komentarze.add(new Komentarz(posty.get(9), user4, "U nas szef nigdy by na to nie pozwolił..."));

            // Post 11 - Laptop
            komentarze.add(new Komentarz(posty.get(10), user3, "Polecam serwis przy ul. Technicznej, naprawili mi w jeden dzień!"));
            komentarze.add(new Komentarz(posty.get(10), user4, "Co się stało z laptopem? Może da się samemu naprawić."));
            komentarze.add(new Komentarz(posty.get(10), user5, "Unikaj serwisu na rogu - oszuści!"));

            // Post 12 - Kurs programowania
            komentarze.add(new Komentarz(posty.get(11), user1, "Gratulacje! Jakiego języka się uczyłaś?"));
            komentarze.add(new Komentarz(posty.get(11), user3, "Teraz najważniejsze to robić własne projekty w portfolio."));
            komentarze.add(new Komentarz(posty.get(11), user4, "Szukasz pracy w IT?"));
            komentarze.add(new Komentarz(posty.get(11), user5, "Powodzenia! Pierwsza praca w branży to zawsze wyzwanie."));
            komentarze.add(new Komentarz(posty.get(11), user2, "Dziękuję! Uczyłam się Pythona, myślę o backendzie."));

            // Post 13 - Autobusy
            komentarze.add(new Komentarz(posty.get(12), user1, "Bo zmienili rozkład i teraz jeździ co 20 minut zamiast co 10..."));
            komentarze.add(new Komentarz(posty.get(12), user2, "A ja myślałam, że to tylko rano tak tłoczno!"));
            komentarze.add(new Komentarz(posty.get(12), user4, "Lepiej wziąć rower - szybciej i zdrowiej."));

            // Post 14 - Piłka
            komentarze.add(new Komentarz(posty.get(13), user1, "Jestem chętny! Gdzie gramy?"));
            komentarze.add(new Komentarz(posty.get(13), user3, "Mogę przyjść, ale gram słabo - nie będę przeszkadzał?"));
            komentarze.add(new Komentarz(posty.get(13), user5, "O której? Mam trening do 16:00."));
            komentarze.add(new Komentarz(posty.get(13), user4, "Boisko przy szkole podstawowej, 17:00. Wszyscy mile widziani!"));

            // Post 15 - Wegańskie brownie
            komentarze.add(new Komentarz(posty.get(14), user1, "Podziel się przepisem!"));
            komentarze.add(new Komentarz(posty.get(14), user2, "Z czego zastąpiłeś jajka?"));
            komentarze.add(new Komentarz(posty.get(14), user3, "Muszę spróbować, choć nie jestem weganinem."));
            komentarze.add(new Komentarz(posty.get(14), user4, "Zdjęcie albo nie było!"));
            komentarze.add(new Komentarz(posty.get(14), user5, "Użyłem aquafaby z ciecierzycy. Przepis wrzucę wieczorem!"));

            komentarzRepository.saveAll(komentarze);

            for (Komentarz k : komentarze) {
                URgoatLogger.uzytkownikInfo(
                        "Dodano komentarz id=" + k.getKomentarzID() + " dlugosc=" + k.getTresc().length(),
                        k.getUzytkownik().getPseudonim(),
                        LogOperacja.DODAWANIE
                );
            }

            //
            // Reakcje do postów
            //
            List<Reakcja> reakcje = new ArrayList<>();

            // Post 1 - Góry
            reakcje.add(new Reakcja(user2, null, posty.get(0), 1)); // Lubię to
            reakcje.add(new Reakcja(user3, null, posty.get(0), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(0), 1)); // Lubię to

            // Post 2 - Książki
            reakcje.add(new Reakcja(user1, null, posty.get(1), 1)); // Lubię to
            reakcje.add(new Reakcja(user4, null, posty.get(1), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(1), 1)); // Lubię to

            // Post 3 - Kawiarnia
            reakcje.add(new Reakcja(user1, null, posty.get(2), 1)); // Lubię to
            reakcje.add(new Reakcja(user2, null, posty.get(2), 1)); // Lubię to
            reakcje.add(new Reakcja(user4, null, posty.get(2), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(2), 1)); // Lubię to

            // Post 4 - Counter-Strike
            reakcje.add(new Reakcja(user1, null, posty.get(3), 1)); // Lubię to
            reakcje.add(new Reakcja(user3, null, posty.get(3), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(3), 1)); // Lubię to
            reakcje.add(new Reakcja(user4, null, posty.get(3), 1)); // Lubię to (autor postu)

            // Post 5 - Bieganie
            reakcje.add(new Reakcja(user1, null, posty.get(4), 1)); // Lubię to
            reakcje.add(new Reakcja(user2, null, posty.get(4), 1)); // Lubię to
            reakcje.add(new Reakcja(user3, null, posty.get(4), 1)); // Lubię to
            reakcje.add(new Reakcja(user4, null, posty.get(4), 1)); // Lubię to

            // Post 6 - Wiosna
            reakcje.add(new Reakcja(user2, null, posty.get(5), 3)); // Wrr (bo narzeka na pogodę)
            reakcje.add(new Reakcja(user4, null, posty.get(5), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(5), 3)); // Wrr

            // Post 7 - Pierogi
            reakcje.add(new Reakcja(user1, null, posty.get(6), 1)); // Lubię to
            reakcje.add(new Reakcja(user3, null, posty.get(6), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(6), 1)); // Lubię to

            // Post 8 - Weekendowy wyjazd
            reakcje.add(new Reakcja(user1, null, posty.get(7), 1)); // Lubię to
            reakcje.add(new Reakcja(user4, null, posty.get(7), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(7), 1)); // Lubię to

            // Post 9 - Gra o Tron
            reakcje.add(new Reakcja(user1, null, posty.get(8), 1)); // Lubię to
            reakcje.add(new Reakcja(user2, null, posty.get(8), 1)); // Lubię to
            reakcje.add(new Reakcja(user3, null, posty.get(8), 3)); // Wrr (bo nie podoba mu się nowa postać)
            reakcje.add(new Reakcja(user5, null, posty.get(8), 1)); // Lubię to

            // Post 10 - Dzień piżamy
            reakcje.add(new Reakcja(user1, null, posty.get(9), 2)); // Haha
            reakcje.add(new Reakcja(user2, null, posty.get(9), 2)); // Haha
            reakcje.add(new Reakcja(user3, null, posty.get(9), 1)); // Lubię to
            reakcje.add(new Reakcja(user4, null, posty.get(9), 3)); // Wrr (bo zazdrości)

            // Post 11 - Laptop
            reakcje.add(new Reakcja(user3, null, posty.get(10), 1)); // Lubię to (bo pomaga)
            reakcje.add(new Reakcja(user4, null, posty.get(10), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(10), 3)); // Wrr (bo ostrzega przed oszustami)

            // Post 12 - Kurs programowania
            reakcje.add(new Reakcja(user1, null, posty.get(11), 1)); // Lubię to
            reakcje.add(new Reakcja(user3, null, posty.get(11), 1)); // Lubię to
            reakcje.add(new Reakcja(user4, null, posty.get(11), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(11), 1)); // Lubię to

            // Post 13 - Autobusy
            reakcje.add(new Reakcja(user1, null, posty.get(12), 3)); // Wrr (narzeka na rozkład)
            reakcje.add(new Reakcja(user2, null, posty.get(12), 3)); // Wrr
            reakcje.add(new Reakcja(user4, null, posty.get(12), 1)); // Lubię to (bo promuje rower)

            // Post 14 - Piłka
            reakcje.add(new Reakcja(user1, null, posty.get(13), 1)); // Lubię to
            reakcje.add(new Reakcja(user3, null, posty.get(13), 1)); // Lubię to
            reakcje.add(new Reakcja(user5, null, posty.get(13), 1)); // Lubię to

            // Post 15 - Wegańskie brownie
            reakcje.add(new Reakcja(user1, null, posty.get(14), 1)); // Lubię to
            reakcje.add(new Reakcja(user2, null, posty.get(14), 1)); // Lubię to
            reakcje.add(new Reakcja(user3, null, posty.get(14), 1)); // Lubię to
            reakcje.add(new Reakcja(user4, null, posty.get(14), 1)); // Lubię to


            // Reakcje do komentarzy

            // Komentarz 1 - Góry (Natalia poleca szlak)
            reakcje.add(new Reakcja(user1, komentarze.get(0), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user5, komentarze.get(0), null, 1)); // Lubię to

            // Komentarz 5 - Książki (Python)
            reakcje.add(new Reakcja(user2, komentarze.get(4), null, 1)); // Lubię to

            // Komentarz 7 - Kawiarnia (Ceny)
            reakcje.add(new Reakcja(user3, komentarze.get(7), null, 3)); // Wrr (bo drogo)

            // Komentarz 12 - Counter-Strike (Dołączę po 20:00)
            reakcje.add(new Reakcja(user4, komentarze.get(12), null, 1)); // Lubię to

            // Komentarz 19 - Pierogi (Zdjęcie)
            reakcje.add(new Reakcja(user1, komentarze.get(19), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user3, komentarze.get(19), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user5, komentarze.get(19), null, 1)); // Lubię to

            // Komentarz 25 - Gra o Tron (Bez spoilów)
            reakcje.add(new Reakcja(user1, komentarze.get(25), null, 3)); // Wrr (bo spoiluje)
            reakcje.add(new Reakcja(user2, komentarze.get(25), null, 3)); // Wrr

            // Komentarz 30 - Kurs programowania (Python)
            reakcje.add(new Reakcja(user1, komentarze.get(30), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user3, komentarze.get(30), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user4, komentarze.get(30), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user5, komentarze.get(30), null, 1)); // Lubię to

            // Komentarz 35 - Wegańskie brownie (Przepis)
            reakcje.add(new Reakcja(user1, komentarze.get(35), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user2, komentarze.get(35), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user3, komentarze.get(35), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user4, komentarze.get(35), null, 1)); // Lubię to
            reakcje.add(new Reakcja(user5, komentarze.get(35), null, 1)); // Lubię to

            reakcjaRepository.saveAll(reakcje);

            for (Reakcja r : reakcje) {
                String doCzego = "";
                if (r.getKomentarz() == null) {
                    doCzego = " do postu id=" + r.getPost().getPostID();
                } else {
                    doCzego = " do komentarza id=" + r.getKomentarz().getKomentarzID();
                }

                URgoatLogger.uzytkownikFine(
                        "Dodano reakcje id=" + r.getReakcjaID() + " kod=" + r.getReakcja() + doCzego,
                        r.getUzytkownik().getPseudonim(),
                        LogOperacja.DODAWANIE
                );
            }


            System.out.println("=====================================");
            System.out.println("=====================================");
            System.out.println("=====================================");
            System.out.println(" >> KONIEC WCZYTYWANIA DATALOADER <<");
            System.out.println("=====================================");
            System.out.println("=====================================");
            System.out.println("=====================================");
        }
    }
}