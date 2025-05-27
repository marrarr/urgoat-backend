package demo.uzytkownik;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.Reakcja;
import demo.reakcja.ReakcjaRepository;
import demo.znajomy.Znajomy;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class UzytkownikController {
    @Autowired
    UzytkownikRepository uzytkownikRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ReakcjaRepository reakcjaRepository;
    @Autowired
    KomentarzRepository komentarzRepository;
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


    @RequestMapping("/historia_wpisow")
    public String historiaWpisow(Model model) {
        Uzytkownik uzytkownik1 = uzytkownikService.getZalogowanyUzytkownik();
        model.addAttribute("header", "Profil"); //Dodanie obiektu do pamieci lokalnej modelu
        model.addAttribute("profilUzytkownika", uzytkownik1); //Dodanie obiektu do pamieci lokalnej modelu

        return "historia_wpisow";
    }

    @RequestMapping(value = "/historia_postow", method = RequestMethod.GET)
    public String listaPostow(Model model)
    {
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();

        List<Post> posty = postRepository.findByUzytkownik(uzytkownik_aktualny);

        model.addAttribute("header","Lista wszystkich twoich postów");
        model.addAttribute("listaPostow",posty);

        return "historia_postow";

    }

    @RequestMapping(value = "/historia_komentarzy", method = RequestMethod.GET)
    public String listaKomentarzy(Model model)
    {
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();

        List<Komentarz> komentarze = komentarzRepository.findByUzytkownik(uzytkownik_aktualny);

        model.addAttribute("header","Lista wszystkich twoich komentarzy do postów");
        model.addAttribute("listaKomentarzy",komentarze);

        return "historia_komentarzy";

    }

    @RequestMapping(value = "/historia_reakcji", method = RequestMethod.GET)
    public String listaReakcji(Model model)
    {
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();
        List<Reakcja> reakcje_posty = reakcjaRepository.findByUzytkownik(uzytkownik_aktualny);

        model.addAttribute("headerPosty","Lista wszystkich twoich reakcji do postów");
        model.addAttribute("listaReakcjiPosty",reakcje_posty);
        model.addAttribute("headerKomentarze","Lista wszystkich twoich reakcji do komentarzy");

        return "historia_reakcji";
    }





}