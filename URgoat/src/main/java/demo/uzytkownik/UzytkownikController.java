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
import java.util.Collections;
import java.util.List;

/**
 * Kontroler obsługujący żądania związane z zarządzaniem profilami użytkowników, w tym wyświetlaniem, edycją oraz historią aktywności.
 */
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

    /**
     * Wyświetla listę wszystkich użytkowników z wyjątkiem aktualnie zalogowanego.
     *
     * @param model Model do przekazania danych do widoku
     * @return Nazwa widoku "wysuzytkownikow"
     */
    @RequestMapping(value = "/lista_uzytkownikow", method = RequestMethod.GET)
    public String listaUzytkownikow(Model model) {
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();
        List<Uzytkownik> uzytkownicy = uzytkownikRepository.findAllExceptById(uzytkownik_aktualny.getUzytkownikID());

        model.addAttribute("header", "Lista wszystkich użytkowników");
        model.addAttribute("listaUzytkownikow", uzytkownicy);

        return "wysuzytkownikow";
    }

    /**
     * Wyświetla profil wybranego użytkownika na podstawie identyfikatora.
     *
     * @param model      Model do przekazania danych do widoku
     * @param uzytkownik Identyfikator użytkownika
     * @return Nazwa widoku "wysprofil"
     */
    @RequestMapping("/wyswietl_profil")
    public String wyswietlProfil(Model model, Long uzytkownik) {
        Uzytkownik uzytkownik1 = uzytkownikRepository.findFirstByUzytkownikID(uzytkownik);
        model.addAttribute("header", "Profil");
        model.addAttribute("profilUzytkownika", uzytkownik1);

        return "wysprofil";
    }

    /**
     * Pobiera zdjęcie profilowe użytkownika na podstawie identyfikatora.
     *
     * @param id Identyfikator użytkownika
     * @return Odpowiedź HTTP z danymi zdjęcia lub status 404, jeśli użytkownik lub zdjęcie nie istnieje
     */
    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable int id) {
        long long_id = id;
        Uzytkownik u = uzytkownikRepository.findById(long_id).orElse(null);

        if (u == null || u.getZdjecie() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(u.getZdjecie());
    }

    /**
     * Wyświetla profil aktualnie zalogowanego użytkownika.
     *
     * @param model Model do przekazania danych do widoku
     * @return Nazwa widoku "wysprofiluzyt"
     */
    @RequestMapping("/wyswietl_profil_aktualnego_uzytkownika")
    public String wyswietlProfilAktualnegoUzytkownika(Model model) {
        Uzytkownik uzytkownik1 = uzytkownikService.getZalogowanyUzytkownik();
        model.addAttribute("header", "Profil");
        model.addAttribute("profilUzytkownika", uzytkownik1);

        return "wysprofiluzyt";
    }

    /**
     * Wyświetla formularz edycji profilu aktualnie zalogowanego użytkownika.
     *
     * @param model Model do przekazania danych do widoku
     * @return Nazwa widoku "form_edycja_profilu"
     */
    @RequestMapping(value = "/edytuj_profil", method = RequestMethod.GET)
    public String edytujProfil(Model model) {
        Uzytkownik uzytkownik = uzytkownikService.getZalogowanyUzytkownik();
        model.addAttribute("header", "Edycja formularz");
        model.addAttribute("dane", uzytkownikService.toTransData(uzytkownik));
        return "form_edycja_profilu";
    }

    /**
     * Zapisuje zmiany w profilu aktualnie zalogowanego użytkownika.
     *
     * @param model     Model do przekazania danych do widoku
     * @param imie      Nowe imię użytkownika
     * @param nazwisko  Nowe nazwisko użytkownika
     * @param pseudonim Nowy pseudonim użytkownika
     * @param zdjecie   Nowe zdjęcie profilowe
     * @return Przekierowanie do profilu użytkownika lub nazwa widoku "form_edycja_profilu" w przypadku błędu
     * @throws IOException              W przypadku błędów wejścia/wyjścia podczas przetwarzania zdjęcia
     * @throws IllegalArgumentException W przypadku niepoprawnych danych
     */
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

    /**
     * Wyświetla stronę z historią wpisów aktualnie zalogowanego użytkownika.
     *
     * @param model Model do przekazania danych do widoku
     * @return Nazwa widoku "historia_wpisow"
     */
    @RequestMapping("/historia_wpisow")
    public String historiaWpisow(Model model) {
        Uzytkownik uzytkownik1 = uzytkownikService.getZalogowanyUzytkownik();
        model.addAttribute("header", "Profil");
        model.addAttribute("profilUzytkownika", uzytkownik1);

        return "historia_wpisow";
    }

    /**
     * Wyświetla listę postów utworzonych przez aktualnie zalogowanego użytkownika.
     *
     * @param model Model do przekazania danych do widoku
     * @return Nazwa widoku "historia_postow"
     */
    @RequestMapping(value = "/historia_postow", method = RequestMethod.GET)
    public String listaPostow(Model model) {
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();
        List<Post> posty = postRepository.findByUzytkownik(uzytkownik_aktualny);
        Collections.reverse(posty);

        model.addAttribute("header", "Lista wszystkich twoich postów");
        model.addAttribute("listaPostow", posty);

        return "historia_postow";
    }

    /**
     * Wyświetla listę komentarzy dodanych przez aktualnie zalogowanego użytkownika.
     *
     * @param model Model do przekazania danych do widoku
     * @return Nazwa widoku "historia_komentarzy"
     */
    @RequestMapping(value = "/historia_komentarzy", method = RequestMethod.GET)
    public String listaKomentarzy(Model model) {
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();
        List<Komentarz> komentarze = komentarzRepository.findByUzytkownik(uzytkownik_aktualny);
        Collections.reverse(komentarze);

        model.addAttribute("header", "Lista wszystkich twoich komentarzy do postów");
        model.addAttribute("listaKomentarzy", komentarze);

        return "historia_komentarzy";
    }

    /**
     * Wyświetla listę reakcji dodanych przez aktualnie zalogowanego użytkownika do postów i komentarzy.
     *
     * @param model Model do przekazania danych do widoku
     * @return Nazwa widoku "historia_reakcji"
     */
    @RequestMapping(value = "/historia_reakcji", method = RequestMethod.GET)
    public String listaReakcji(Model model) {
        Uzytkownik uzytkownik_aktualny = uzytkownikService.getZalogowanyUzytkownik();
        List<Reakcja> reakcje_posty = reakcjaRepository.findByUzytkownik(uzytkownik_aktualny);
        Collections.reverse(reakcje_posty);

        model.addAttribute("headerPosty", "Lista wszystkich twoich reakcji do postów");
        model.addAttribute("listaReakcjiPosty", reakcje_posty);
        model.addAttribute("headerKomentarze", "Lista wszystkich twoich reakcji do komentarzy");

        return "historia_reakcji";
    }
}