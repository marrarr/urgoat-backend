package demo.uzytkownik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class UzytkownikController {
    @Autowired
    UzytkownikRepository uzytkownikRepository;
    @Autowired
    private UzytkownikService uzytkownikService;

    @RequestMapping(value = "/lista_uzytkownikow", method = RequestMethod.GET)
    public String listaUzytkownikow(Model model) {
        //List<Uzytkownik> uzytkownik = uzytkownikRepository.findAll();


//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Uzytkownik uzytkownik_aktualny= uzytkownikRepository.findFirstByPseudonim(username);
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();
        List<Uzytkownik> uzytkownicy = uzytkownikRepository.findAllExceptById(uzytkownik_aktualny.getUzytkownikID());

        model.addAttribute("header", "Lista wszystkich użytkowników"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("listaUzytkownikow", uzytkownicy); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysuzytkownikow"; //Przekierowanie na strone
    }

    @RequestMapping("/wyswietl_profil")
    public String wyswietlProfil(Model model, Long uzytkownik) {
        Uzytkownik uzytkownik1 = uzytkownikRepository.findFirstByUzytkownikID(uzytkownik);
        model.addAttribute("header", "Profil"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("profilUzytkownika", uzytkownik1); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysprofil";
    }

    @RequestMapping("/wyswietl_profil_aktualnego_uzytkownika")
    public String wyswietlProfilAktualnegoUzytkownika(Model model) {
        Uzytkownik uzytkownik1 = uzytkownikService.getZalogowanyUzytkownik();
        model.addAttribute("header", "Profil"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("profilUzytkownika", uzytkownik1); //Dodanie obiektu do pamieci lokalnej modelu

        return "wysprofiluzyt";
    }

    @RequestMapping(value = "/edytuj_profil", method = RequestMethod.GET)
    public String edytujProfil(Model model) {
        Uzytkownik uzytkownik = uzytkownikService.getZalogowanyUzytkownik();
        model.addAttribute("header", "Edycja formularz");
        model.addAttribute("dane", uzytkownikService.toTransData(uzytkownik));
        return "form_edycja_profilu";
    }

    @RequestMapping(value = "/edytuj_profil", method = RequestMethod.POST)
    public String zapiszEdycjeProfilu(Model model,
                                      @RequestParam("imie") String imie,
                                      @RequestParam("nazwisko") String nazwisko,
                                      @RequestParam("pseudonim") String pseudonim,
                                      @RequestParam("zdjecie") MultipartFile zdjecie
    ) {
        Uzytkownik zalogowanyUzytkownik = uzytkownikService.getZalogowanyUzytkownik();
        Long id = (long) zalogowanyUzytkownik.getUzytkownikID();
        try {
            uzytkownikService.aktualizujDane(id, imie, nazwisko, pseudonim, zdjecie);
        } catch (IOException | IllegalArgumentException e) {
            model.addAttribute("header", "Edycja formularz");
            model.addAttribute("dane", uzytkownikService.toTransData(zalogowanyUzytkownik));
            model.addAttribute("status", e.getMessage());

            return "form_edycja_profilu";
        }

        return "redirect:/wyswietl_profil_aktualnego_uzytkownika";
    }


}