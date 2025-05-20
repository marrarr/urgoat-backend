package demo.post;

import java.util.List;

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

    @RequestMapping("/dodaj_post")
    public String dodajPost(Model model)
    {
        PostTransData transData = new PostTransData();
        model.addAttribute("transData", transData);
        return "addform";
    }

    @RequestMapping(value = "/dodaj_post", method = RequestMethod.POST)
    public String dodajPost(Model model, PostTransData postTransData) {
        String tresc = postTransData.getTresc();

        if (tresc.isBlank()) {
            model.addAttribute("header", "Wynik");
            model.addAttribute("message","Post nie może być pusty");

            return "viewmessage";
        } else {
            Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();

            postService.dodajPost(uzytkownik_aktualny.getUzytkownikID(), tresc);
            model.addAttribute("header", "Wynik");
            model.addAttribute("message","Post został poprawnie dodany");

            return "viewmessage";
        }
    }

    @RequestMapping(value = "/usun_post", method = RequestMethod.DELETE)
    public String usunPost(Model model, Long id) {
        postService.usunPost(id);

        model.addAttribute("header", "Wynik");
        model.addAttribute("message","Post został usunięty");

        return "viewmessage";
    }

    @RequestMapping(value = "/wyswietl_posty", method = RequestMethod.GET)
    public String wyswietlPosty(Model model) {
        List<PostTransData> postyTransData = postService.getPosty();

        model.addAttribute("header", "Lista wszystkich postów");
        model.addAttribute("listaPostow", postyTransData);

        return "wysposty";  // Przekierowanie do widoku
    }

    @RequestMapping(value = "/strona_glowna", method = RequestMethod.GET)
    public String wyswietlStroneGlowna(Model model) {
        List<PostTransData> postyTransData = postService.getPostyZKomentarzamiOrazReakcjami();

        model.addAttribute("header","Strona główna");
        model.addAttribute("posty", postyTransData);

        return "wysposty";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String przekierujNaStroneGlwona(Model model) {
        return "redirect:/strona_glowna";
    }


}