package demo.common;

import demo.log.LogOperacja;
import demo.log.URgoatLogger;
import demo.security.model.User;
import demo.security.repository.UserRepository;
import demo.uzytkownik.UzytkownikService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

/**
 * Kontroler obsługujący logowanie użytkowników i przekierowanie po zalogowaniu.
 * Przekierowuje użytkowników na odpowiednie strony w zależności od ich roli (administrator lub zwykły użytkownik).
 */
@Controller
public class LoginController {

    private final UserRepository userRepository;

    /**
     * Konstruktor kontrolera LoginController, wstrzykujący wymagane zależności.
     *
     * @param userRepository repozytorium użytkowników
     * @param uzytkownikService serwis użytkowników (niewykorzystywany w obecnej implementacji)
     */
    public LoginController(UserRepository userRepository, UzytkownikService uzytkownikService) {
        this.userRepository = userRepository;
    }

    /**
     * Przekierowuje użytkownika po zalogowaniu na odpowiednią stronę w zależności od roli.
     * Administratorzy są przekierowywani do panelu administracyjnego, a zwykli użytkownicy na stronę główną.
     * Rejestruje zdarzenie logowania w systemie logów.
     *
     * @param authentication obiekt uwierzytelnienia Spring Security, zawierający dane zalogowanego użytkownika
     * @return adres URL do przekierowania (panel administracyjny, strona główna lub strona logowania w przypadku braku uwierzytelnienia)
     */
    @GetMapping("/redirectAfterLogin")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        String logIsAdmin = isAdmin ? "admin" : "użytkownik";
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        String logId;
        if (user.isPresent()) {
            logId = String.valueOf(user.get().getId());
        } else {
            logId = String.valueOf(authentication.getPrincipal());
        }
        URgoatLogger.uzytkownikInfo("Zalogował się " + logIsAdmin + " id=" + logId,
                username,
                LogOperacja.LOGOWANIE
        );

        return isAdmin ? "redirect:/admin/panel" : "redirect:/strona_glowna";
    }
}