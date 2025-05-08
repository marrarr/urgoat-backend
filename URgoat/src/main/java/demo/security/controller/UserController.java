package demo.security.controller;

import demo.SerwisAplikacji;
import demo.security.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;
    
    @Autowired
    private SerwisAplikacji serwisAplikacji;

    public UserController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("Wyswietlanie strony logowania");
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String imie,
            @RequestParam String nazwisko,
            @RequestParam(required = false) MultipartFile zdjecie,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String captcha,
            Model model,
            HttpSession session
    ) {
        try {
            // Weryfikacja zgodności haseł
            if (!password.equals(confirmPassword)) {
                throw new RuntimeException("Hasła nie są identyczne");
            }

            // Weryfikacja CAPTCHA
            String[] selectedImages = captcha.split(",");
            List<String> validImages = Arrays.asList("dog1", "dog2");
            if (selectedImages.length != 2 || !validImages.containsAll(Arrays.asList(selectedImages))) {
                throw new RuntimeException("Proszę wybrać obrazy z psem");
            }

            // Weryfikacja zdjęcia
            byte[] zdjecieBytes = null;
            if (zdjecie != null && !zdjecie.isEmpty()) {
                if (!zdjecie.getContentType().startsWith("image/")) {
                    throw new RuntimeException("Przesłany plik musi być obrazem");
                }
                if (zdjecie.getSize() > 5 * 1024 * 1024) { // Limit 5MB
                    throw new RuntimeException("Zdjęcie jest za duże (maks. 5MB)");
                }
                zdjecieBytes = zdjecie.getBytes();
            }

            // Zapisanie CAPTCHA w sesji
            session.setAttribute("captcha", captcha);

            // Rejestracja użytkownika w encji User przez userService
            userService.registerUser(username, email, password);

            // Rejestracja danych w encji Uzytkownik przez SerwisAplikacji
            serwisAplikacji.dodajUzytkownika(imie, nazwisko, zdjecieBytes);

            model.addAttribute("email", email);
            return "verify";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/verify")
    public String verify(
            @RequestParam(required = false) String email,
            Model model
    ) {
        if (email != null) {
            model.addAttribute("email", email);
        }
        return "verify";
    }

    @PostMapping("/verify")
    public String verifyUser(
            @RequestParam String email,
            @RequestParam String code,
            Model model
    ) {
        boolean verified = userService.verifyUser(email, code);
        if (verified) {
            // Clear session after verification
            session.removeAttribute("captcha");
            return "redirect:/login";
        }
        model.addAttribute("error", "Nieprawidlowy kod lub email");
        model.addAttribute("email", email);
        return "verify";
    }
}