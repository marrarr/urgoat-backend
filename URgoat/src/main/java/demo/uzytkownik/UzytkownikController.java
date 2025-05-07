package demo.uzytkownik;

import demo.security.repository.UserRepository;
import demo.znajomy.Znajomy;
import demo.znajomy.ZnajomyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UzytkownikController {
    @Autowired
    UzytkownikRepository uzytkownikRepository;

    @Autowired
    ZnajomyRepository znajomyRepository;

    @RequestMapping(value = "/lista_uzytkownikow", method = RequestMethod.GET)
    public String listaUzytkownikow(Model model) {
        //List<Uzytkownik> uzytkownik = uzytkownikRepository.findAll();


        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Uzytkownik uzytkownik_aktualny= uzytkownikRepository.findFirstByPseudonim(username);
        List<Uzytkownik> uzytkownik =uzytkownikRepository.findAllExceptById(uzytkownik_aktualny.getUzytkownikID());

        model.addAttribute("header", "Lista wszystkich użytkowników"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaUzytkownikow", uzytkownik); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysuzytkownikow"; //Przekierowanie na strone

    }

    @RequestMapping("/wyswietl_profil")
    public String wyswietlProfil(Model model, Long uzytkownik) {
        Uzytkownik uzytkownik1 = uzytkownikRepository.findFirstByUzytkownikID(uzytkownik);
        model.addAttribute("header", "Profil"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("profilUzytkownika", uzytkownik1); //Dodanie obiektu do pamieci lokalnej modelu


        return "wysprofil";
    }
}