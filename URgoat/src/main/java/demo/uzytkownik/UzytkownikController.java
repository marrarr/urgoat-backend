package demo.uzytkownik;

import demo.security.repository.UserRepository;
import demo.security.service.UserService;
import demo.znajomy.Znajomy;
import demo.znajomy.ZnajomyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable int id) {
        long long_id=id;
        Uzytkownik u = uzytkownikRepository.findById(long_id).orElse(null);

        if (u == null || u.getZdjecie() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // albo dynamicznie: MediaType.parseMediaType(u.getContentType())
                .body(u.getZdjecie());
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
        model.addAttribute("header", "Edycja formularz");
        return "form_edycja_profilu";
    }

    @RequestMapping(value = "/edytuj_profil", method = RequestMethod.POST)
    public String zapiszEdycjeProfilu(Model model,
                                      @RequestParam("imie") String imie,
                                      @RequestParam("nazwisko") String nazwisko,
                                      @RequestParam("zdjecie") MultipartFile zdjecie) {
        Long id = (long) uzytkownikService.getZalogowanyUzytkownik().getUzytkownikID();
        uzytkownikService.aktualizujDane(id, imie, nazwisko, zdjecie);

        Uzytkownik uzytkownik1 = uzytkownikService.getZalogowanyUzytkownik();
        model.addAttribute("header", "Profil"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("profilUzytkownika", uzytkownik1); //Dodanie obiektu do pamieci lokalnej modelu
        return "wysprofiluzyt";
    }


}