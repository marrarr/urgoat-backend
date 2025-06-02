package demo.reakcja;

import demo.SerwisAplikacji;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
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
 * Kontroler obsługujący operacje związane z reakcjami.
 * Umożliwia wyświetlanie, dodawanie i usuwanie reakcji dla postów i komentarzy.
 */
@Controller
public class ReakcjaController {

    @Autowired
    ReakcjaRepository reakcjaRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private KomentarzRepository komentarzRepository;

    @Autowired
    SerwisAplikacji serwisAplikacji;

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private UzytkownikService uzytkownikService;

    @Autowired
    private ReakcjaService reakcjaService;

    /**
     * Wyświetla statystyki reakcji dla danego posta.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param postID identyfikator posta
     * @return nazwa szablonu widoku dla strony z reakcjami
     */
    @RequestMapping(value = "/wyswietl_reakcje_post", method = RequestMethod.GET)
    public String wyswietlReakcje(Model model, Long postID) {
        int intpostid = Integer.parseInt(postID.toString());
        Post post = postRepository.findByPostID(intpostid);

        model.addAttribute("header", "Reakcje");
        model.addAttribute("reakcja_lubieto", reakcjaRepository.countByPostAndReakcja(post, 1));
        model.addAttribute("reakcje_haha", reakcjaRepository.countByPostAndReakcja(post, 2));
        model.addAttribute("reakcje_nielubie", reakcjaRepository.countByPostAndReakcja(post, 3));

        return "wysreakcje";
    }

    /**
     * Wyświetla reakcje dla danego posta.
     * Uwaga: Metoda wydaje się zawierać błąd, ponieważ wyświetla reakcje jako komentarze.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param postID identyfikator posta
     * @param reakcja nieużywany parametr (prawdopodobnie błąd w implementacji)
     * @return nazwa szablonu widoku dla listy komentarzy (powinno być dla reakcji)
     */
    @RequestMapping(value = "/wyswietl_reakcje_komentarze", method = RequestMethod.POST)
    public String wyswietlReakcje(Model model, Long postID, Long reakcja) {
        List<Reakcja> reakcje = reakcjaRepository.findByPostPostID(postID);

        model.addAttribute("header", "Lista wszystkich komentarzy");
        model.addAttribute("listaKomentarzy", reakcje);

        return "wyskom";
    }

    /**
     * Wyświetla formularz do dodawania reakcji do posta.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param postID identyfikator posta
     * @return nazwa szablonu widoku dla formularza dodawania reakcji
     */
    @RequestMapping("/dodaj_reakcje_post")
    public String dodajReakcjePost(Model model, Long postID) {
        ReakcjaTransData reakcjaTransData = new ReakcjaTransData();
        model.addAttribute("reakcjaTransData", reakcjaTransData);
        model.addAttribute("postId_link", postID);
        return "addreakcja";
    }

    /**
     * Przetwarza żądanie dodania reakcji do posta.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param reakcjaTransData obiekt DTO zawierający dane reakcji (typ reakcji, identyfikator posta)
     * @return nazwa szablonu widoku z komunikatem o wyniku operacji
     */
    @RequestMapping(value = "/dodaj_reakcje_post", method = RequestMethod.POST)
    public String dodajKomentarz(Model model, ReakcjaTransData reakcjaTransData) {
        int reakcja = reakcjaTransData.getReakcja();
        int postID = reakcjaTransData.getPostID();

        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();
        reakcjaService.dodajReakcjeDoPosta(
                uzytkownik_aktualny.getUzytkownikID(),
                (long) postID,
                reakcja
        );

        model.addAttribute("header", "Wynik");
        model.addAttribute("message", "Zostało porpawnie dodane");

        return "redirect:/strona_glowna";
    }

    /**
     * Wyświetla formularz do dodawania reakcji do komentarza.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param komentarzID identyfikator komentarza
     * @return nazwa szablonu widoku dla formularza dodawania reakcji
     */
    @RequestMapping("/dodaj_reakcje_komentarz")
    public String dodajReakcjeKomentarz(Model model, Long komentarzID) {
        ReakcjaTransData reakcjaTransData = new ReakcjaTransData();
        model.addAttribute("reakcjaTransData", reakcjaTransData);
        model.addAttribute("komentarzId_link", komentarzID);
        return "addreakcjakom";
    }

    /**
     * Przetwarza żądanie dodania reakcji do komentarza.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param reakcjaTransData obiekt DTO zawierający dane reakcji (typ reakcji, identyfikator komentarza)
     * @return nazwa szablonu widoku z komunikatem o wyniku operacji
     */
    @RequestMapping(value = "/dodaj_reakcje_komentarz", method = RequestMethod.POST)
    public String dodajReakcjeKomentarz(Model model, ReakcjaTransData reakcjaTransData) {
        int reakcja = reakcjaTransData.getReakcja();
        int komentarzID = reakcjaTransData.getKomentarzID();

        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();
        reakcjaService.dodajReakcjeDoKomentarza(
                uzytkownik_aktualny.getUzytkownikID(),
                (long) komentarzID,
                reakcja
        );

        model.addAttribute("header", "Wynik");
        model.addAttribute("message", "Zostało porpawnie dodane");

        return "redirect:/strona_glowna";
    }

    /**
     * Usuwa reakcję o podanym identyfikatorze.
     *
     * @param model model do przekazania atrybutów do widoku
     * @param id identyfikator reakcji do usunięcia
     * @return nazwa szablonu widoku z komunikatem o wyniku operacji
     */
    @RequestMapping(value = "/usun_reakcje", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String usunPost(Model model, @RequestParam Long id) {
        reakcjaService.usunReakcje(id);

        model.addAttribute("header", "Wynik");
        model.addAttribute("message", "Reakcja została usunięta");

        return "viewmessage";
    }
}