package demo.znajomy;

import demo.czat.CzatTransData;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
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

    @RequestMapping(value = "/lista_znajomych", method = RequestMethod.GET)
    public String listaZnajomych(Model model)
    {
        Uzytkownik uzytkownik= uzytkownikRepository.findFirstByPseudonim("Ados");

        //long long_uzytkownikID=(long)uzytkownikID;
        List<Znajomy> znajomy = znajomyRepository.findByUzytkownik(uzytkownik);
        System.out.println(znajomy.toString());

        model.addAttribute("header","Lista wszystkich komentarzy"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaZnajomych",znajomy); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysznajomych"; //Przekierowanie na strone

    }
}