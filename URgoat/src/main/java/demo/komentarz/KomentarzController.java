package demo.komentarz;

import demo.SerwisAplikacji;
import demo.post.Post;
import demo.post.PostRepository;
import demo.post.PostTransData;
import demo.reakcja.ReakcjaRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Kontroler obsługujący operacje związane z komentarzami.
 * Umożliwia dodawanie, wyświetlanie, edytowanie oraz usuwanie komentarzy do postów.
 */
@Controller
public class KomentarzController {

    private final PostRepository postRepository;
    private final KomentarzRepository komentarzRepository;
    private final ReakcjaRepository reakcjaRepository;
    private final SerwisAplikacji serwisAplikacji;
    private final UzytkownikRepository uzytkownikRepository;
    private final UzytkownikService uzytkownikService;
    private final KomentarzService komentarzService;

    /**
     * Konstruktor kontrolera KomentarzController, wstrzykujący wymagane zależności.
     *
     * @param postRepository repozytorium postów
     * @param komentarzRepository repozytorium komentarzy
     * @param reakcjaRepository repozytorium reakcji
     * @param serwisAplikacji serwis aplikacji
     * @param uzytkownikRepository repozytorium użytkowników
     * @param uzytkownikService serwis użytkowników
     * @param komentarzService serwis komentarzy
     */
    public KomentarzController(PostRepository postRepository, KomentarzRepository komentarzRepository,
                               ReakcjaRepository reakcjaRepository, SerwisAplikacji serwisAplikacji,
                               UzytkownikRepository uzytkownikRepository, UzytkownikService uzytkownikService,
                               KomentarzService komentarzService) {
        this.postRepository = postRepository;
        this.komentarzRepository = komentarzRepository;
        this.reakcjaRepository = reakcjaRepository;
        this.serwisAplikacji = serwisAplikacji;
        this.uzytkownikRepository = uzytkownikRepository;
        this.uzytkownikService = uzytkownikService;
        this.komentarzService = komentarzService;
    }

    /**
     * Wyświetla formularz do dodawania nowego komentarza.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param postID identyfikator posta, do którego dodawany jest komentarz
     * @return nazwa szablonu widoku dla formularza dodawania komentarza
     */
    @RequestMapping("/dodaj_komentarz")
    public String dodajKomentarz(Model model, Long postID) {
        KomentarzTransData komentarzTransData = new KomentarzTransData();
        model.addAttribute("komentarzTransData", komentarzTransData);
        return "addkom";
    }

    /**
     * Przetwarza żądanie dodania nowego komentarza.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param komentarzTransData obiekt DTO zawierający dane komentarza (treść, identyfikator posta)
     * @return nazwa szablonu widoku z komunikatem o wyniku operacji
     */
    @RequestMapping(value = "/dodaj_komentarz", method = RequestMethod.POST)
    public String dodajKomentarz(Model model, KomentarzTransData komentarzTransData) {
        String tresc = komentarzTransData.getTresc();
        int postID = komentarzTransData.getPostID();
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();

        komentarzService.dodajKomentarz(uzytkownik_aktualny.getUzytkownikID(), postID, tresc);

        model.addAttribute("header", "Wynik");
        model.addAttribute("message", "Zostało porpawnie dodane");

        return "viewmessage";
    }

    /**
     * Wyświetla listę komentarzy dla określonego posta.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param postID identyfikator posta, którego komentarze mają być wyświetlone
     * @return nazwa szablonu widoku dla listy komentarzy
     */
    @RequestMapping(value = "/wyswietl_komentarze", method = RequestMethod.GET)
    public String wyswietlKomentarze(Model model, Long postID) {
        List<Komentarz> komentarze = komentarzRepository.findByPostPostID(postID);

        model.addAttribute("header", "Lista wszystkich komentarzy");
        model.addAttribute("listaKomentarzy", komentarze);
        model.addAttribute("id", postID);

        return "wyskom";
    }

    /**
     * Usuwa komentarz o podanym identyfikatorze.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param id identyfikator komentarza do usunięcia
     * @return nazwa szablonu widoku z komunikatem o wyniku operacji
     */
    @RequestMapping(value = "/usun_komentarz", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String usunPost(Model model, @RequestParam Long id) {
        komentarzService.usunKomentarz(id);

        model.addAttribute("header", "Wynik");
        model.addAttribute("message", "Komentarz został usunięty");

        return "viewmessage";
    }

    /**
     * Wyświetla formularz do edycji istniejącego komentarza.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param id identyfikator komentarza do edycji
     * @return nazwa szablonu widoku dla formularza edycji komentarza lub widoku z komunikatem błędu
     */
    @RequestMapping(value = "/edytuj_komentarz", method = RequestMethod.GET)
    public String edytujKomentarz(Model model, @RequestParam Long id) {
        if (id == null || id == 0) {
            model.addAttribute("header", "Błąd");
            model.addAttribute("message", "ID komentarza jest nieprawidłowe");
            return "viewmessage";
        }

        Komentarz komentarz = komentarzRepository.findByKomentarzID(id);
        KomentarzTransData transData = new KomentarzTransData();
        transData.setKomentarzID(komentarz.getKomentarzID());
        transData.setTresc(komentarz.getTresc());
        model.addAttribute("transData", transData);
        return "aktualizacja_komentarza";
    }

    /**
     * Przetwarza żądanie edycji istniejącego komentarza.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param komentarzTransData obiekt DTO zawierający zaktualizowane dane komentarza (treść, identyfikator)
     * @return nazwa szablonu widoku z komunikatem o wyniku operacji
     * @throws RuntimeException jeśli operacja aktualizacji nie powiedzie się
     */
    @RequestMapping(value = "/edytuj_komentarz", method = RequestMethod.POST)
    public String edytujKomentarz(Model model, KomentarzTransData komentarzTransData) {
        String tresc = komentarzTransData.getTresc();
        long id = komentarzTransData.getKomentarzID();

        if (tresc == null || tresc.isBlank()) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Komentarz nie może być pusty");
            return "viewmessage";
        }

        try {
            komentarzService.aktualizujKomentarz(id, tresc);
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Komentarz został zaktualizowany");
        } catch (RuntimeException e) {
            model.addAttribute("header", "Błąd");
            model.addAttribute("message", e.getMessage());
        }

        return "viewmessage";
    }
}