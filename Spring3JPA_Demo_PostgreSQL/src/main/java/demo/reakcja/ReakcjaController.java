package demo.reakcja;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/wyswietl_reakcje_post", method = RequestMethod.GET)
    public String wyswietlReakcje(Model model, Long postID)
    {
        int intpostid = Integer.parseInt(postID.toString());
        Post post = postRepository.findAll().get(intpostid);

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
    public String dodajReakcjePost(Model model, Long postID_link)
    {
        ReakcjaTransData reakcjaTransData = new ReakcjaTransData();
        model.addAttribute("reakcjaTransData", reakcjaTransData);
        model.addAttribute("postId_link", postID_link);
        return "addreakcja";
    }

    @RequestMapping(value = "/dodaj_reakcje_post", method = RequestMethod.POST)
    public String dodajKomentarz(Model model, ReakcjaTransData reakcjaTransData) {

        int reakcja = reakcjaTransData.getReakcja();
        int postID = reakcjaTransData.getPostID();

        long long_postID=(long)postID;
        Post post = postRepository.findAll().get(postID);

        Komentarz komentarz = null;

        // TODO nie wiem czy moze byc null
        reakcjaRepository.save(new Reakcja(null, komentarz, post, reakcja));

        model.addAttribute("header", "Wynik");
        model.addAttribute("message","Zostało porpawnie dodane");

        return "viewmessage";
    }
}
