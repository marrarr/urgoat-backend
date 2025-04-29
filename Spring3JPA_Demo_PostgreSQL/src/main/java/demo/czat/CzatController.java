package demo.czat;
import demo.komentarz.KomentarzTransData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class CzatController {
    @Autowired
    CzatRepository czatRepository;


    @RequestMapping("/dodaj_czat")
    public String dodajCzat(Model model, Long uzytkownikID_link)
    {
        CzatTransData czatTransData = new CzatTransData();
        model.addAttribute("czatTransData", czatTransData);
        model.addAttribute("uzytkownikId_link", uzytkownikID_link);
        return "addczat";
    }

//    @RequestMapping(value = "/dodaj_czat", method = RequestMethod.POST)
//    public String dodajCzat(Model model, CzatTransData czatTransData) {
//
//        String tresc = komentarzTransData.getTresc();
//        int postID = komentarzTransData.getPostID() - 1;  // nie zmieniać
//        long long_postID=(long)postID;
//
//        Post post = postRepository.findAll().get(postID);
//
//        komentarzRepository.save(new Komentarz(post, null, tresc));
//        model.addAttribute("header", "Wynik");
//        model.addAttribute("message","Zostało porpawnie dodane");
//
//        return "viewmessage";
//    }

}
