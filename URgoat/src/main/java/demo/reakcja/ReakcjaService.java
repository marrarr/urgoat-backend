package demo.reakcja;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.log.LogOperacja;
import demo.log.URgoatLogger;
import demo.post.Post;
import demo.post.PostRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.uzytkownik.UzytkownikTransData;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReakcjaService {
    private final UzytkownikService uzytkownikService;
    private final UzytkownikRepository uzytkownikRepository;
    private final PostRepository postRepository;
    private final KomentarzRepository komentarzRepository;
    private final ReakcjaRepository reakcjaRepository;

    public ReakcjaService(UzytkownikService uzytkownikService, UzytkownikRepository uzytkownikRepository, PostRepository postRepository, KomentarzRepository komentarzRepository, ReakcjaRepository reakcjaRepository) {
        this.uzytkownikService = uzytkownikService;
        this.uzytkownikRepository = uzytkownikRepository;
        this.postRepository = postRepository;
        this.komentarzRepository = komentarzRepository;
        this.reakcjaRepository = reakcjaRepository;
    }

    public ReakcjaTransData toTransData(Reakcja reakcja) {
        Integer postID = reakcja.getPost() != null ? reakcja.getPost().getPostID() : null;
        Integer komentarzID = reakcja.getKomentarz() != null ? reakcja.getKomentarz().getKomentarzID() : null;
        UzytkownikTransData uzytkownikKomentarzTransData = uzytkownikService.toTransDataBezImieniaNazwiska(reakcja.getUzytkownik());

        return new ReakcjaTransData(
                postID,
                komentarzID,
                reakcja.getReakcja(),
                uzytkownikKomentarzTransData
        );
    }

    public List<ReakcjaTransData> toTransData(List<Reakcja> reakcje) {
        return reakcje
                .stream()
                .map(reakcja -> toTransData(reakcja))
                .toList();
    }

    // TODO gdyby reakcje na stronie były klikalne, a nie przez form, to zrobić usunięcie reakcji po wciśnięciu na już dodaną
    // ale trzeba byloby jeszcze jakoś wyróżniać wcisnieta na stronie
    @Transactional
    public void dodajReakcjeDoPosta(long uzytkownikId, Long postId, int kodReakcji) {
        if (kodReakcji == 0) { return; }

        if (postId != null) {
            Uzytkownik uzytkownik = uzytkownikRepository.findById(uzytkownikId).orElseThrow();
            Post post = postRepository.findById(postId).orElseThrow();
            // czy uzytkownik ma juz dodana reakcje, jesli tak zaktualizuj kodreakcji
            Optional<Reakcja> istniejaca = reakcjaRepository
                    .findByUzytkownik_UzytkownikIDAndPost_PostID((int)uzytkownikId, post.getPostID());
            if (istniejaca.isPresent()) {
                aktualizujReakcje(istniejaca.get().getReakcjaID(), kodReakcji);
                return;
            }

            Reakcja reakcja = new Reakcja();
            reakcja.setUzytkownik(uzytkownik);
            reakcja.setReakcja(kodReakcji);
            reakcja.setPost(post);
            reakcja.setKomentarz(null);
            reakcjaRepository.save(reakcja);

            URgoatLogger.uzytkownikInfo(
                    "Dodano reakcje id=" + reakcja.getReakcjaID() + " kod=" + kodReakcji + " do postu id=" + postId,
                    uzytkownikService.getZalogowanyUzytkownik().getPseudonim(),
                    LogOperacja.DODAWANIE
            );
        } else {
            throw new IllegalArgumentException("PostId nie może być pusty");
        }
    }

    @Transactional
    public void dodajReakcjeDoKomentarza(long uzytkownikId, Long komentarzId, int kodReakcji) {
        if (kodReakcji == 0) { return; }

        if (komentarzId != null) {
            Uzytkownik uzytkownik = uzytkownikRepository.findById(uzytkownikId).orElseThrow();
            Komentarz komentarz = komentarzRepository.findById(komentarzId).orElseThrow();
            // czy uzytkownik ma juz dodana reakcje, jesli tak zaktualizuj kodreakcji
            Optional<Reakcja> istniejaca = reakcjaRepository
                    .findByUzytkownik_UzytkownikIDAndKomentarz_KomentarzID((int)uzytkownikId, komentarz.getKomentarzID());
            if (istniejaca.isPresent()) {
                aktualizujReakcje(istniejaca.get().getReakcjaID(), kodReakcji);
                return;
            }

            Reakcja reakcja = new Reakcja();
            reakcja.setUzytkownik(uzytkownik);
            reakcja.setReakcja(kodReakcji);
            reakcja.setPost(null);
            reakcja.setKomentarz(komentarz);
            reakcjaRepository.save(reakcja);

            URgoatLogger.uzytkownikInfo(
                    "Dodano reakcje id=" + reakcja.getReakcjaID() + " kod=" + kodReakcji + " do komentarza id=" + komentarzId,
                    uzytkownikService.getZalogowanyUzytkownik().getPseudonim(),
                    LogOperacja.DODAWANIE
            );
        } else {
            throw new IllegalArgumentException("KomentarzId nie może być pusty");
        }
    }

    @Transactional
    public void aktualizujReakcje(long reakcjaID, int kodReakcji) {
        Uzytkownik uzytkownik_zalogowany = uzytkownikService.getZalogowanyUzytkownik();
        Reakcja reakcja = reakcjaRepository.findById(reakcjaID).orElseThrow();

        if (uzytkownik_zalogowany.getUzytkownikID() == reakcja.getUzytkownik().getUzytkownikID()) {
            reakcja.setReakcja(kodReakcji);
            reakcjaRepository.save(reakcja);

            URgoatLogger.uzytkownikInfo(
                    "Zaktualizowano reakcje id=" + reakcja.getReakcjaID() + " na kod=" + kodReakcji,
                    uzytkownikService.getZalogowanyUzytkownik().getPseudonim(),
                    LogOperacja.AKTUALIZOWANIE
            );
        } else {
            throw new AccessDeniedException("Brak uprawnień do aktualizacji reakcji");
        }
    }

    @Transactional
    public void usunReakcje(long reakcjaID) {
        Uzytkownik uzytkownik_zalogowany = uzytkownikService.getZalogowanyUzytkownik();
        Reakcja reakcja = reakcjaRepository.findById(reakcjaID).orElseThrow();

        if (uzytkownik_zalogowany.getUzytkownikID() == reakcja.getUzytkownik().getUzytkownikID()) {
            reakcjaRepository.delete(reakcja);

            URgoatLogger.uzytkownikInfo(
                    "Usunięto reakcje id=" + reakcja.getReakcjaID() + " kod=" + reakcja.getReakcja(),
                    uzytkownikService.getZalogowanyUzytkownik().getPseudonim(),
                    LogOperacja.USUWANIE
            );
        } else {
            throw new AccessDeniedException("Brak uprawnień do usunięcia reakcji");
        }
    }
}