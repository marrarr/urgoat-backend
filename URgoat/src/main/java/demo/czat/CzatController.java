package demo.czat;
import demo.SerwisAplikacji;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.wiadomosc.Wiadomosc;
import demo.wiadomosc.WiadomoscRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private WiadomoscRepository wiadomoscRepository;

    @GetMapping("/rozmowa/{idZnajomego}")
    public String otworzCzat(@AuthenticationPrincipal UserDetails userDetails,
                             @PathVariable Long idZnajomego,
                             Model model) {

        Uzytkownik currentUser = uzytkownikService.getZalogowanyUzytkownik();
        Uzytkownik friend = uzytkownikRepository.findById(idZnajomego)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));

        Czat czat = czatRepository.findByUzytkownicyContainsBoth(currentUser, friend)
                .orElseGet(() -> {
                    serwisAplikacji.dodajCzat(currentUser.getUzytkownikID(), friend.getUzytkownikID());
                    return czatRepository.findByUzytkownicyContainsBoth(currentUser, friend).orElseThrow();
                });

        // Load messages with proper sender information
        List<Wiadomosc> messages = wiadomoscRepository.findByCzatOrderByWiadomoscIDAsc(czat);

        List<WiadomoscDTO> messageDTOs = messages.stream()
                .map(w -> {
                    String senderEmail = "Unknown";
                    String displayName = "Unknown";

                    if (w.getUzytkownik() != null) {
                        senderEmail = w.getUzytkownik().getEmail();
                        displayName = w.getUzytkownik().getPseudonim(); // Or use getImie() + " " + getNazwisko()
                    }

                    String base64Image = null;
                    if (w.getZdjecie() != null) {
                        base64Image = Base64.getEncoder().encodeToString(w.getZdjecie());
                    }

                    return new WiadomoscDTO(
                            w.getTresc(),
                            senderEmail,
                            displayName,  // Add this field to your DTO
                            czat.getCzatID(),
                            base64Image,
                            w.getWiadomoscID() // Add message ID for potential use
                    );
                })
                .collect(Collectors.toList());

        model.addAttribute("czatId", czat.getCzatID());
        model.addAttribute("znajomy", friend.getPseudonim());
        model.addAttribute("currentUserEmail", currentUser.getEmail());
        model.addAttribute("wiadomosci", messageDTOs);

        return "czat";
    }
}
