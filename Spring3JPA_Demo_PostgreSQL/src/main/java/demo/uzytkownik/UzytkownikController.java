package demo.uzytkownik;

import demo.znajomy.Znajomy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UzytkownikController {
    @Autowired
    UzytkownikRepository uzytkownikRepository;

    @RequestMapping(value = "/lista_uzytkownikow", method = RequestMethod.GET)
    public String listaUzytkownikow(Model model) {
        List<Uzytkownik> uzytkownik = uzytkownikRepository.findAll();

        //long long_uzytkownikID=(long)uzytkownikID;
        //System.out.println(znajomy.toString());

        model.addAttribute("header", "Lista wszystkich użytkowników"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaUzytkownikow", uzytkownik); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysuzytkownikow"; //Przekierowanie na strone

    }

    @RequestMapping("/wyswietl_profil")
    public String wyswietlProfil(Model model, Long uzytkownik) {
        // TODO zamiast Ados ma być uzytkownik zalogowany

        Uzytkownik uzytkownik1 = uzytkownikRepository.findFirstByUzytkownikID(uzytkownik);

        model.addAttribute("header", "Profil"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("profilUzytkownika", uzytkownik1); //Dodanie obiektu do pamieci lokalnej modelu


        return "wysprofil";
    }
}