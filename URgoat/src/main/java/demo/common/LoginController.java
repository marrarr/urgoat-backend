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

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository, UzytkownikService uzytkownikService) {
        this.userRepository = userRepository;
    }

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