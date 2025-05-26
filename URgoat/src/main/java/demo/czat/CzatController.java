package demo.czat;
import demo.SerwisAplikacji;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/czat")
public class CzatController {

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private CzatRepository czatRepository;

    @Autowired
    private SerwisAplikacji serwisAplikacji;
    @Autowired
    private UzytkownikService uzytkownikService;

    @GetMapping("/rozmowa/{idZnajomego}")
    public String otworzCzat(@AuthenticationPrincipal UserDetails userDetails,
                             @PathVariable Long idZnajomego,
                             Model model) {

        Uzytkownik u1 = uzytkownikService.getZalogowanyUzytkownik();
        Uzytkownik u2 = uzytkownikRepository.findById(idZnajomego)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));

        Czat czat = czatRepository.findByUzytkownicyContainsBoth(u1, u2)
                .orElseGet(() -> {
                    serwisAplikacji.dodajCzat(u1.getUzytkownikID(), u2.getUzytkownikID());
                    return czatRepository.findByUzytkownicyContainsBoth(u1, u2).orElseThrow();
                });

        model.addAttribute("czatId", czat.getCzatID());
        model.addAttribute("znajomy", u2.getPseudonim());
        model.addAttribute("nadawcaEmail", userDetails.getUsername());

        return "czat"; // => resources/templates/czat.html
    }
}
