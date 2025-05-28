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

@Service
public class KomentarzService {
    @Autowired
    private KomentarzRepository komentarzRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UzytkownikRepository uzytkownikRepository;
    @Autowired
    private UzytkownikService uzytkownikService;
    @Autowired
    private ReakcjaService reakcjaService;
    @Autowired
    private UserRepository userRepository;


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

    public List<KomentarzTransData> toTransData(List<Komentarz> komentarze) {
        return komentarze
                .stream()
                .map(komentarz -> toTransData(komentarz))
                .toList();
    }

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

    @Transactional
    public void usunKomentarz(long komentarzID) {
        Komentarz komentarz = komentarzRepository.findById(komentarzID).orElseThrow();
        Uzytkownik uzytkownik_zalogowany = uzytkownikService.getZalogowanyUzytkownik();
        User user = userRepository.findByEmail(uzytkownik_zalogowany.getEmail()).orElseThrow();

        // post moze usunac tylko autor lub osoba z permisjami, np admin
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

    @Transactional
    public void aktualizujKomentarz(long komentarzID, String tresc) {
        if (tresc.isBlank()) {
            throw new IllegalArgumentException("Treść komentarza nie może być pusta.");
        }

        Komentarz komentarz = komentarzRepository.findById(komentarzID).orElseThrow();
        Uzytkownik uzytkownik_zalogowany = uzytkownikService.getZalogowanyUzytkownik();

        // post moze aktualizować tylko autor
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