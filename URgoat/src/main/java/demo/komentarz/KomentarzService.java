package demo.komentarz;

import demo.log.LogOperacja;
import demo.log.URgoatLogger;
import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.ReakcjaService;
import demo.security.model.User;
import demo.security.repository.UserRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serwis obsługujący operacje związane z komentarzami.
 * Umożliwia dodawanie, usuwanie, aktualizowanie oraz konwersję komentarzy na obiekty DTO.
 */
@Service
public class KomentarzService {

    private final KomentarzRepository komentarzRepository;
    private final PostRepository postRepository;
    private final UzytkownikRepository uzytkownikRepository;
    private final UzytkownikService uzytkownikService;
    private final ReakcjaService reakcjaService;
    private final UserRepository userRepository;

    /**
     * Konstruktor serwisu KomentarzService, wstrzykujący wymagane zależności.
     *
     * @param komentarzRepository repozytorium komentarzy
     * @param postRepository repozytorium postów
     * @param uzytkownikRepository repozytorium użytkowników
     * @param uzytkownikService serwis użytkowników
     * @param reakcjaService serwis reakcji
     * @param userRepository repozytorium użytkowników systemu zabezpieczeń
     */
    public KomentarzService(KomentarzRepository komentarzRepository, PostRepository postRepository,
                            UzytkownikRepository uzytkownikRepository, UzytkownikService uzytkownikService,
                            ReakcjaService reakcjaService, UserRepository userRepository) {
        this.komentarzRepository = komentarzRepository;
        this.postRepository = postRepository;
        this.uzytkownikRepository = uzytkownikRepository;
        this.uzytkownikService = uzytkownikService;
        this.reakcjaService = reakcjaService;
        this.userRepository = userRepository;
    }

    /**
     * Konwertuje encję komentarza na obiekt DTO.
     *
     * @param komentarz komentarz do konwersji
     * @return obiekt DTO zawierający dane komentarza
     */
    public KomentarzTransData toTransData(Komentarz komentarz) {
        return new KomentarzTransData(
                komentarz.getKomentarzID(),
                komentarz.getTresc(),
                komentarz.getZdjecie(),
                komentarz.getPost().getPostID(),
                uzytkownikService.toTransDataBezImieniaNazwiska(komentarz.getUzytkownik()),
                reakcjaService.toTransData(komentarz.getReakcje())
        );
    }

    /**
     * Konwertuje listę encji komentarzy na listę obiektów DTO.
     *
     * @param komentarze lista komentarzy do konwersji
     * @return lista obiektów DTO zawierających dane komentarzy
     */
    public List<KomentarzTransData> toTransData(List<Komentarz> komentarze) {
        return komentarze
                .stream()
                .map(komentarz -> toTransData(komentarz))
                .toList();
    }

    /**
     * Dodaje nowy komentarz do posta.
     *
     * @param userId identyfikator użytkownika dodającego komentarz
     * @param postId identyfikator posta, do którego dodawany jest komentarz
     * @param tresc treść komentarza
     * @throws RuntimeException jeśli użytkownik lub post nie zostanie znaleziony
     */
    @Transactional
    public void dodajKomentarz(long userId, long postId, String tresc) {
        Uzytkownik user = uzytkownikRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        Komentarz komentarz = new Komentarz();
        komentarz.setUzytkownik(user);
        komentarz.setPost(post);
        komentarz.setTresc(tresc);
        komentarzRepository.save(komentarz);

        URgoatLogger.uzytkownikInfo(
                "Dodano komentarz id=" + komentarz.getKomentarzID() + " dlugosc=" + komentarz.getTresc().length(),
                uzytkownikService.getZalogowanyUzytkownik().getPseudonim(),
                LogOperacja.DODAWANIE
        );
    }

    /**
     * Usuwa komentarz o podanym identyfikatorze.
     * Tylko autor komentarza lub administrator może usunąć komentarz.
     *
     * @param komentarzID identyfikator komentarza do usunięcia
     * @throws RuntimeException jeśli komentarz nie zostanie znaleziony
     * @throws AccessDeniedException jeśli użytkownik nie ma uprawnień do usunięcia komentarza
     */
    @Transactional
    public void usunKomentarz(long komentarzID) {
        Komentarz komentarz = komentarzRepository.findById(komentarzID).orElseThrow();
        Uzytkownik uzytkownik_zalogowany = uzytkownikService.getZalogowanyUzytkownik();
        User user = userRepository.findByEmail(uzytkownik_zalogowany.getEmail()).orElseThrow();

        if (user.getRole().equals("ROLE_ADMIN") ||
                uzytkownik_zalogowany.getUzytkownikID() == komentarz.getUzytkownik().getUzytkownikID()) {
            komentarzRepository.delete(komentarz);

            URgoatLogger.uzytkownikInfo(
                    "Usunięto komentarz id=" + komentarz.getKomentarzID() + " dlugosc=" + komentarz.getTresc().length(),
                    uzytkownikService.getZalogowanyUzytkownik().getPseudonim(),
                    LogOperacja.USUWANIE
            );
        } else {
            throw new AccessDeniedException("Brak uprawnień do usunięcia komentarza");
        }
    }

    /**
     * Aktualizuje treść komentarza o podanym identyfikatorze.
     * Tylko autor komentarza może zaktualizować komentarz.
     *
     * @param komentarzID identyfikator komentarza do aktualizacji
     * @param tresc nowa treść komentarza
     * @throws IllegalArgumentException jeśli treść komentarza jest pusta
     * @throws RuntimeException jeśli komentarz nie zostanie znaleziony
     * @throws AccessDeniedException jeśli użytkownik nie ma uprawnień do aktualizacji komentarza
     */
    @Transactional
    public void aktualizujKomentarz(long komentarzID, String tresc) {
        if (tresc.isBlank()) {
            throw new IllegalArgumentException("Treść komentarza nie może być pusta.");
        }

        Komentarz komentarz = komentarzRepository.findById(komentarzID).orElseThrow();
        Uzytkownik uzytkownik_zalogowany = uzytkownikService.getZalogowanyUzytkownik();

        if (uzytkownik_zalogowany.getUzytkownikID() == komentarz.getUzytkownik().getUzytkownikID()) {
            komentarz.setTresc(tresc);
            komentarzRepository.save(komentarz);

            URgoatLogger.uzytkownikInfo(
                    "Zaktualizowano komentarz id=" + komentarz.getKomentarzID() + " dlugosc=" + komentarz.getTresc().length(),
                    uzytkownikService.getZalogowanyUzytkownik().getPseudonim(),
                    LogOperacja.AKTUALIZOWANIE
            );
        } else {
            throw new AccessDeniedException("Brak uprawnień do usunięcia komentarza");
        }
    }
}