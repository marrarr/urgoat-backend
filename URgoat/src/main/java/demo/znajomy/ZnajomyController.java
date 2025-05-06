package demo.znajomy;

import demo.czat.CzatTransData;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.security.service.SerwisAplikacji;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ZnajomyController{
    @Autowired
    ZnajomyRepository znajomyRepository;
    @Autowired
    UzytkownikRepository uzytkownikRepository;
    @Autowired
    private SerwisAplikacji serwisAplikacji;


    @RequestMapping(value = "/lista_znajomych", method = RequestMethod.GET)
    public String listaZnajomych(Model model)
    {
        // TODO zamiast Ados ma być uzytkownik zalogowany
        Uzytkownik uzytkownik= uzytkownikRepository.findFirstByPseudonim("Ados");

        //long long_uzytkownikID=(long)uzytkownikID;
        List<Znajomy> znajomy = znajomyRepository.findByUzytkownik(uzytkownik);
        System.out.println(znajomy.toString());

        model.addAttribute("header","Lista wszystkich znajomych"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaZnajomych",znajomy); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysznajomych"; //Przekierowanie na strone

    }

    @RequestMapping("/dodaj_znajomego")
    public String dodajZnajomego(Model model, Long uzytkownik) {

        // TODO zamiast Ados ma być uzytkownik zalogowany
        Uzytkownik uzytkownik2= uzytkownikRepository.findFirstByPseudonim("Ados");
        serwisAplikacji.dodajZnajomego(uzytkownik2.getUzytkownikID(),uzytkownik);
        List<Znajomy> znajomy = znajomyRepository.findByUzytkownik(uzytkownik2);
        //System.out.println(znajomy.toString());

        model.addAttribute("header","Lista wszystkich znajomych"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaZnajomych",znajomy); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysznajomych"; //Przekierowanie na strone

    }
}
