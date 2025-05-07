package demo.komentarz;

import demo.security.service.SerwisAplikacji;
import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.ReakcjaRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
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

    @RequestMapping("/dodaj_komentarz")
    public String dodajKomentarz(Model model, Long postID_link)
    {
        KomentarzTransData komentarzTransData = new KomentarzTransData();
        model.addAttribute("komentarzTransData", komentarzTransData);
        model.addAttribute("postId_link", postID_link);
        return "addkom";
    }

    @RequestMapping(value = "/dodaj_komentarz", method = RequestMethod.POST)
    public String dodajKomentarz(Model model, KomentarzTransData komentarzTransData) {

        String tresc = komentarzTransData.getTresc();
        int postID = komentarzTransData.getPostID() - 1;  // nie zmieniać
        long long_postID=(long)postID;


        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Uzytkownik uzytkownik_aktualny= uzytkownikRepository.findFirstByPseudonim(username);
        serwisAplikacji.dodajKomentarz(uzytkownik_aktualny.getUzytkownikID(), postID, tresc);

        model.addAttribute("header", "Wynik");
        model.addAttribute("message","Zostało porpawnie dodane");

        return "viewmessage";
    }

    @RequestMapping(value = "/wyswietl_komentarze", method = RequestMethod.GET)
    public String wyswietlKomentarze(Model model, Long postID)
    {
//        KomentarzTransData komentarzTransData = new KomentarzTransData();
//        model.addAttribute("komentarzTransData", komentarzTransData);
//
        List<Komentarz> komentarze = komentarzRepository.findByPostPostID(postID);
//        int idposttest = komentarzTransData.getPostID();
//        System.out.println("ID" +idposttest);
        model.addAttribute("header","Lista wszystkich komentarzy"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaKomentarzy",komentarze); //Dodanie obiektu do pamieci lokalnej modelu

        return "wyskom"; //Przekierowanie na strone

    }
}
