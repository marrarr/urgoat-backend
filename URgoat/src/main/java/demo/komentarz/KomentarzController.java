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

@Controller
public class KomentarzController {
    private final PostRepository postRepository;
    private final KomentarzRepository komentarzRepository;
    private final ReakcjaRepository reakcjaRepository;
    private final SerwisAplikacji serwisAplikacji;
    private final UzytkownikRepository uzytkownikRepository;
    private final UzytkownikService uzytkownikService;
    private final KomentarzService komentarzService;

    public KomentarzController(PostRepository postRepository, KomentarzRepository komentarzRepository, ReakcjaRepository reakcjaRepository, SerwisAplikacji serwisAplikacji, UzytkownikRepository uzytkownikRepository, UzytkownikService uzytkownikService, KomentarzService komentarzService) {
        this.postRepository = postRepository;
        this.komentarzRepository = komentarzRepository;
        this.reakcjaRepository = reakcjaRepository;
        this.serwisAplikacji = serwisAplikacji;
        this.uzytkownikRepository = uzytkownikRepository;
        this.uzytkownikService = uzytkownikService;
        this.komentarzService = komentarzService;
    }

    @RequestMapping("/dodaj_komentarz")
    public String dodajKomentarz(Model model, Long postID) {
        KomentarzTransData komentarzTransData = new KomentarzTransData();
        model.addAttribute("komentarzTransData", komentarzTransData);
        //model.addAttribute("postId_link", postID);
        return "addkom";
    }

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

    @RequestMapping(value = "/wyswietl_komentarze", method = RequestMethod.GET)
    public String wyswietlKomentarze(Model model, Long postID) {
        List<Komentarz> komentarze = komentarzRepository.findByPostPostID(postID);

        model.addAttribute("header", "Lista wszystkich komentarzy");
        model.addAttribute("listaKomentarzy", komentarze);
        model.addAttribute("id", postID);

        return "wyskom";
    }

    @RequestMapping(value = "/usun_komentarz", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String usunPost(Model model, @RequestParam Long id) {
        komentarzService.usunKomentarz(id);

        model.addAttribute("header", "Wynik");
        model.addAttribute("message", "Komentarz został usunięty");

        return "viewmessage";
    }

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