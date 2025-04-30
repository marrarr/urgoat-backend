package demo.post;

import java.util.List;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.reakcja.ReakcjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    KomentarzRepository komentarzRepository;
    @Autowired
    private ReakcjaRepository reakcjaRepository;

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

        postRepository.save(new Post(null, tresc));
        model.addAttribute("header", "Wynik");
        model.addAttribute("message","Zostało porpawnie dodane");

        return "viewmessage";
    }
    

    
    @RequestMapping(value = "/wyswietl_posty", method = RequestMethod.GET)
    public String wyswietlPosty(Model model) {
    List<Post> posty = postRepository.findAll();  // Pobierz wszystkie posty
    for (Post post : posty) {
        // Pobierz komentarze dla każdego postu
        List<Komentarz> komentarze = komentarzRepository.findByPostPostID(Long.valueOf(post.getPostID()));

        // TODO po chuj???
        post.setKomentarze(komentarze);  // Ustaw komentarze w poście
    }
    
    model.addAttribute("header", "Lista wszystkich postów");
    model.addAttribute("listaPostow", posty);  // Dodaj posty z komentarzami do modelu

    return "wysposty";  // Przekierowanie do widoku
}
    
    

    
}
