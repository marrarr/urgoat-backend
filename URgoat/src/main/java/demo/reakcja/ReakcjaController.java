package demo.reakcja;

import demo.SerwisAplikacji;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
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


    @RequestMapping(value = "/wyswietl_reakcje_post", method = RequestMethod.GET)
    public String wyswietlReakcje(Model model, Long postID)
    {
        int intpostid = Integer.parseInt(postID.toString());
        Post post = postRepository.findByPostID(intpostid);

        model.addAttribute("header","Reakcje"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("reakcja_lubieto", reakcjaRepository.countByPostAndReakcja(post, 1)); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("reakcje_haha", reakcjaRepository.countByPostAndReakcja(post, 2)); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("reakcje_nielubie", reakcjaRepository.countByPostAndReakcja(post, 3)); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysreakcje"; //Przekierowanie na strone
    }

    @RequestMapping(value = "/wyswietl_reakcje_komentarze", method = RequestMethod.POST)
    public String wyswietlReakcje(Model model, Long postID, Long reakcja)
    {
        List<Reakcja> reakcje = reakcjaRepository.findByPostPostID(postID);

        model.addAttribute("header","Lista wszystkich komentarzy"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaKomentarzy", reakcje); //Dodanie obiektu do pamieci lokalnej modelu

        return "wyskom"; //Przekierowanie na strone
    }


    @RequestMapping("/dodaj_reakcje_post")
    public String dodajReakcjePost(Model model, Long postID)
    {
        ReakcjaTransData reakcjaTransData = new ReakcjaTransData();
        model.addAttribute("reakcjaTransData", reakcjaTransData);
        model.addAttribute("postId_link", postID);
        return "addreakcja";
    }

    @RequestMapping(value = "/dodaj_reakcje_post", method = RequestMethod.POST)
    public String dodajKomentarz(Model model, ReakcjaTransData reakcjaTransData) {

        int reakcja = reakcjaTransData.getReakcja();
        int postID = reakcjaTransData.getPostID();

        Post post = postRepository.findByPostID(postID);

        Komentarz komentarz = null;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Uzytkownik uzytkownik_aktualny = uzytkownikRepository.findFirstByPseudonim(username);
        reakcjaRepository.save(new Reakcja(uzytkownik_aktualny, komentarz, post, reakcja));

        model.addAttribute("header", "Wynik");
        model.addAttribute("message","Zostało porpawnie dodane");

        return "viewmessage";
    }

    @RequestMapping("/dodaj_reakcje_komentarz")
    public String dodajReakcjeKomentarz(Model model, Long komentarzID)
    {
        ReakcjaTransData reakcjaTransData = new ReakcjaTransData();
        model.addAttribute("reakcjaTransData", reakcjaTransData);
        model.addAttribute("komentarzId_link", komentarzID);
        return "addreakcjakom";
    }

    @RequestMapping(value = "/dodaj_reakcje_komentarz", method = RequestMethod.POST)
    public String dodajReakcjeKomentarz(Model model, ReakcjaTransData reakcjaTransData) {

        int reakcja = reakcjaTransData.getReakcja();
        int komentarzID = reakcjaTransData.getKomentarzID();

        long long_komentarzID=(long)komentarzID;
        Komentarz komentarz = komentarzRepository.findByKomentarzID(long_komentarzID);
        System.out.println("cotojest" + long_komentarzID);

        Post post = null;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Uzytkownik uzytkownik_aktualny = uzytkownikRepository.findFirstByPseudonim(username);
        reakcjaRepository.save(new Reakcja(uzytkownik_aktualny, komentarz, post, reakcja));

        model.addAttribute("header", "Wynik");
        model.addAttribute("message","Zostało porpawnie dodane");

        return "viewmessage";
    }

}
