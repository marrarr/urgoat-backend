package demo.komentarz;

import demo.SerwisAplikacji;
import demo.post.PostRepository;
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

import java.util.List;

@Controller
public class KomentarzController {
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
    private KomentarzService komentarzService;

    @RequestMapping("/dodaj_komentarz")
    public String dodajKomentarz(Model model, Long postID)
    {
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
        model.addAttribute("message","Zostało porpawnie dodane");

        return "viewmessage";
    }

    @RequestMapping(value = "/wyswietl_komentarze", method = RequestMethod.GET)
    public String wyswietlKomentarze(Model model, Long postID)
    {
        List<Komentarz> komentarze = komentarzRepository.findByPostPostID(postID);

        model.addAttribute("header","Lista wszystkich komentarzy"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaKomentarzy",komentarze); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("id",postID); //Dodanie obiektu do pamieci lokalnej modelu

        return "wyskom"; //Przekierowanie na strone

    }
}
