package demo.znajomy;

import demo.czat.CzatTransData;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.security.service.SerwisAplikacji;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Transactional
    @RequestMapping(value = "/lista_znajomych", method = RequestMethod.GET)
    public String listaZnajomych(Model model)
    {
        // TODO zamiast Ados ma być uzytkownik zalogowany
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Uzytkownik uzytkownik_aktualny= uzytkownikRepository.findFirstByPseudonim(username);
        //Uzytkownik uzytkownik= uzytkownikRepository.findFirstByPseudonim("Ados");

        //long long_uzytkownikID=(long)uzytkownikID;
        List<Znajomy> znajomy = znajomyRepository.findAllByUser(uzytkownik_aktualny);
        //System.out.println(znajomy.toString());

        model.addAttribute("header","Lista wszystkich znajomych"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaZnajomych",znajomy); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysznajomych"; //Przekierowanie na strone

    }

    @RequestMapping("/dodaj_znajomego")
    public String dodajZnajomego(Model model, Long uzytkownik) {

        // TODO zamiast Ados ma być uzytkownik zalogowany
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Uzytkownik uzytkownik_aktualny= uzytkownikRepository.findFirstByPseudonim(username);
        //Uzytkownik uzytkownik2= uzytkownikRepository.findFirstByPseudonim("Ados");

        Uzytkownik uzytkownik2= uzytkownikRepository.findFirstByUzytkownikID(uzytkownik);
        if(znajomyRepository.sprawdz_znajomych(uzytkownik_aktualny,uzytkownik2)==0)
        {
            serwisAplikacji.dodajZnajomego(uzytkownik_aktualny.getUzytkownikID(),uzytkownik);
            serwisAplikacji.dodajZnajomego(uzytkownik,uzytkownik_aktualny.getUzytkownikID());
        }else{

            List<Uzytkownik> uzytkownicy =uzytkownikRepository.findAllExceptById(uzytkownik_aktualny.getUzytkownikID());
            model.addAttribute("header", "Lista wszystkich użytkowników"); //Dodanie obiektu do pamieci lokalnej modelu
            model.addAttribute("listaUzytkownikow", uzytkownicy); //Dodanie obiektu do pamieci lokalnej modelu

            return "wysuzytkownikow"; //Przekierowanie na strone
        }

        List<Znajomy> znajomy = znajomyRepository.findAllByUser(uzytkownik_aktualny);
        //System.out.println(znajomy.toString());

        model.addAttribute("header","Lista wszystkich znajomych"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaZnajomych",znajomy); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysznajomych"; //Przekierowanie na strone

    }
}
