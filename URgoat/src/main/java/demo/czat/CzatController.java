package demo.czat;
import demo.security.service.SerwisAplikacji;
import demo.komentarz.KomentarzTransData;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikTransData;
import demo.wiadomosc.Wiadomosc;
import demo.wiadomosc.WiadomoscRepository;
import demo.wiadomosc.WiadomoscTransData;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CzatController {
    @Autowired
    CzatRepository czatRepository;
    @Autowired
    UzytkownikRepository uzytkownikRepository;
    @Autowired
    SerwisAplikacji serwisAplikacji;
    @Autowired
    private WiadomoscRepository wiadomoscRepository;



    @GetMapping("/czat")
    public String chat(@RequestParam int znajomy, Model model) {
        // TODO zamiast Ados ma być uzytkownik zalogowany
        Uzytkownik u1 = uzytkownikRepository.findFirstByPseudonim("Ados");
      //  Uzytkownik u2 = uzytkownikRepository.findFirstByPseudonim("Natik");
        Uzytkownik u2 = uzytkownikRepository.findById((long) znajomy).orElseThrow();

        Czat czat = czatRepository.findByUzytkownicyContainsBoth(u1, u2)
                .orElseGet(() -> {
                    serwisAplikacji.dodajCzat(u1.getUzytkownikID(), u2.getUzytkownikID());
//                    Czat nowy = new Czat();
//                    nowy.getUzytkownicy().add(u1);
//                    nowy.getUzytkownicy().add(u2);
//                    return czatRepository.save(nowy);
                    return czatRepository.findByUzytkownicyContainsBoth(u1, u2).orElseThrow();
                });

        model.addAttribute("czatID", czat.getCzatID());
        return "chat";
    }



    @RequestMapping("/dodaj_czat")
    public String dodajCzat(Model model, Long znajomy)
    {
        // TODO zamiast Ados ma być uzytkownik zalogowany
        Uzytkownik uzytkownik1 = uzytkownikRepository.findFirstByPseudonim("Ados");
        Uzytkownik uzytkownik2 = uzytkownikRepository.findFirstByUzytkownikID(znajomy);

//        UzytkownikTransData uzytkownik1TransData = new UzytkownikTransData(
//                uzytkownik1.getUzytkownikID(),
//                null,
//                uzytkownik1.getPseudonim()
//        );
//
        UzytkownikTransData uzytkownik2TransData = new UzytkownikTransData(
                uzytkownik2.getUzytkownikID(),
                null,
                uzytkownik2.getPseudonim()
        );

        if(czatRepository.findByUzytkownicyContainsBoth(uzytkownik1,uzytkownik2).isEmpty())
        {
            serwisAplikacji.dodajCzat(uzytkownik1.getUzytkownikID(), uzytkownik2.getUzytkownikID());
        }else
        {
            System.out.println("Ten czat już istnieje");
        }



        model.addAttribute("uzytkownik", uzytkownik2TransData);
        return "czat";
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


    @RequestMapping("/czat")
    public String zaladujCzat(Model model) {
     /*   Czat czat = czatRepository.findByUzytkownicyContainsBoth();
        List<Wiadomosc> wiadomosci = wiadomoscRepository.findByCzat(czatRepository);*/
//        List<Wiadomosc> wiadomosci = wiadomoscRepository.findByCzat(czatRepository.findById(1L).get());

//        model.addAttribute("wiadomosci", wiadomosci);
        return "chat";
    }

}