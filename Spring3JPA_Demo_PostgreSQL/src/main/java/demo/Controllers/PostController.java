package demo.Controllers;

import demo.Post;
import demo.PostRepository;
import demo.PostTransData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

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

        postRepository.save(new Post(tresc, null));
        model.addAttribute("header", "Wynik");
        model.addAttribute("message","Zostało porpawnie dodane");

        return "viewmessage";
    }

}
