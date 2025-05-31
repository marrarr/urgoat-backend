package demo.post;

import demo.SerwisAplikacji;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.reakcja.ReakcjaRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

/**
 * Kontroler obsługujący operacje związane z postami.
 * Umożliwia dodawanie, usuwanie, edytowanie oraz wyświetlanie postów na stronie głównej.
 */
@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    KomentarzRepository komentarzRepository;

    @Autowired
    private ReakcjaRepository reakcjaRepository;

    @Autowired
    private SerwisAplikacji serwisAplikacji;

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private UzytkownikService uzytkownikService;

    @Autowired
    private PostService postService;

    /**
     * Wyświetla formularz do dodawania nowego posta.
     *
     * @param model model do przekazania atrybutów do widoku
     * @return nazwa szablonu widoku dla formularza dodawania posta
     */
    @RequestMapping("/dodaj_post")
    public String dodajPost(Model model) {
        PostTransData transData = new PostTransData();
        model.addAttribute("transData", transData);
        return "addform";
    }

    /**
     * Przetwarza żądanie dodania nowego posta.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param postTransData obiekt DTO zawierający dane posta (treść)
     * @return nazwa szablonu widoku z komunikatem o wyniku operacji
     */
    @RequestMapping(value = "/dodaj_post", method = RequestMethod.POST)
    public String dodajPost(Model model, PostTransData postTransData) {
        String tresc = postTransData.getTresc();

        if (tresc.isBlank()) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Post nie może być pusty");
            return "viewmessage";
        } else {
            Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();

            postService.dodajPost(uzytkownik_aktualny.getUzytkownikID(), tresc);
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Post został poprawnie dodany");
            return "viewmessage";
        }
    }

    /**
     * Usuwa post o podanym identyfikatorze.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param id identyfikator posta do usunięcia
     * @return nazwa szablonu widoku z komunikatem o wyniku operacji
     */
    @RequestMapping(value = "/usun_post", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String usunPost(Model model, @RequestParam Long id) {
        postService.usunPost(id);

        model.addAttribute("header", "Wynik");
        model.addAttribute("message", "Post został usunięty");
        return "viewmessage";
    }

    /**
     * Wyświetla formularz do edycji istniejącego posta.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param id identyfikator posta do edycji
     * @return nazwa szablonu widoku dla formularza edycji posta lub widoku z komunikatem błędu
     */
    @RequestMapping(value = "/edytuj_post", method = RequestMethod.GET)
    public String edytujPost(Model model, @RequestParam Long id) {
        if (id == null || id == 0) {
            model.addAttribute("header", "Błąd");
            model.addAttribute("message", "ID posta jest nieprawidłowe");
            return "viewmessage";
        }

        int int_id = Integer.parseInt(id.toString());
        Post post = postRepository.findByPostID(int_id);
        PostTransData transData = new PostTransData();
        transData.setPostId(post.getPostID());
        transData.setTresc(post.getTresc());
        model.addAttribute("transData", transData);
        return "aktualizacja_postu";
    }

    /**
     * Przetwarza żądanie edycji istniejącego posta.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param postTransData obiekt DTO zawierający zaktualizowane dane posta (treść, identyfikator)
     * @return nazwa szablonu widoku z komunikatem o wyniku operacji
     * @throws RuntimeException jeśli operacja aktualizacji nie powiedzie się
     */
    @RequestMapping(value = "/edytuj_post", method = RequestMethod.POST)
    public String edytujPost(Model model, PostTransData postTransData) {
        String tresc = postTransData.getTresc();
        long id = postTransData.getPostId();

        if (tresc == null || tresc.isBlank()) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Post nie może być pusty");
            return "viewmessage";
        }

        try {
            postService.zaktualizujPost(id, tresc);
            model.addAttribute("header", "Wynik");
            model.addAttribute("message", "Post został zaktualizowany");
        } catch (RuntimeException e) {
            model.addAttribute("header", "Błąd");
            model.addAttribute("message", e.getMessage());
        }

        return "viewmessage";
    }

    /**
     * Wyświetla stronę główną z listą postów, komentarzy i reakcji.
     *
     * @param model model do przekazania atrybutów do widoku
     * @return nazwa szablonu widoku dla strony głównej
     */
    @RequestMapping(value = "/strona_glowna", method = RequestMethod.GET)
    public String wyswietlStroneGlowna(Model model) {
        List<PostTransData> postyTransData = postService.getPostyZKomentarzamiOrazReakcjami();

        model.addAttribute("header", "Strona główna");
        model.addAttribute("posty", postyTransData);
        return "wysposty";
    }

    /**
     * Przekierowuje na stronę główną.
     *
     * @param model model do przekazania atrybutów do widoku
     * @return adres URL do przekierowania na stronę główną
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String przekierujNaStroneGlwona(Model model) {
        return "redirect:/strona_glowna";
    }
}