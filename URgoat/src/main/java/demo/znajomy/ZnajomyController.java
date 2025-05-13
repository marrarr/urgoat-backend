package demo.znajomy;

import demo.SerwisAplikacji;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
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
    @Autowired
    private UzytkownikService uzytkownikService;


    @RequestMapping(value = "/lista_znajomych", method = RequestMethod.GET)
    public String listaZnajomych(Model model)
    {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Uzytkownik uzytkownik_aktualny= uzytkownikRepository.findFirstByPseudonim(username);
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();

        List<Znajomy> znajomy = znajomyRepository.findAllByUser(uzytkownik_aktualny);

        model.addAttribute("header","Lista wszystkich znajomych");
        model.addAttribute("listaZnajomych",znajomy);

        return "wysznajomych";

    }

    @RequestMapping(value = "/lista_znajomych_uzytkownika", method = RequestMethod.GET)
    public String listaZnajomychUzytkownika(Model model, Long uzytkownik)
    {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Uzytkownik uzytkownik_aktualny= uzytkownikRepository.findFirstByPseudonim(username);
        Uzytkownik uzytkownik1 = uzytkownikRepository.findFirstByUzytkownikID(uzytkownik);
        //Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();

        List<Znajomy> znajomy = znajomyRepository.findAllByUser(uzytkownik1);

        model.addAttribute("header","Lista wszystkich znajomych");
        model.addAttribute("listaZnajomych",znajomy);

        return "wysznajomych";

    }

    @RequestMapping("/dodaj_znajomego")
    public String dodajZnajomego(Model model, Long uzytkownik) {

//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Uzytkownik uzytkownik_aktualny= uzytkownikRepository.findFirstByPseudonim(username);
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();

        Uzytkownik uzytkownik_dodawany = uzytkownikRepository.findFirstByUzytkownikID(uzytkownik);
        if(znajomyRepository.sprawdz_znajomych(uzytkownik_aktualny, uzytkownik_dodawany) == 0)
        {
            serwisAplikacji.dodajZnajomego(uzytkownik_aktualny.getUzytkownikID(),uzytkownik);
            serwisAplikacji.dodajZnajomego(uzytkownik,uzytkownik_aktualny.getUzytkownikID());
        } else{  // jeśli użytkownicy są znajomi to przeładowuje stronę z użytkownikami

            List<Uzytkownik> uzytkownicy = uzytkownikRepository.findAllExceptById(uzytkownik_aktualny.getUzytkownikID());
            model.addAttribute("header", "Lista wszystkich użytkowników"); //Dodanie obiektu do pamieci lokalnej modelu
            model.addAttribute("listaUzytkownikow", uzytkownicy); //Dodanie obiektu do pamieci lokalnej modelu

            return "wysuzytkownikow"; //Przekierowanie na strone
        }

        List<Znajomy> lista_znajomych = znajomyRepository.findAllByUser(uzytkownik_aktualny);

        model.addAttribute("header","Lista wszystkich znajomych"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaZnajomych",lista_znajomych); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysznajomych"; //Przekierowanie na strone
    }
}
