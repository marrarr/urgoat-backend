package demo.security.controller;

import demo.SerwisAplikacji;
import demo.security.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Kontroler obsługujący żądania związane z zarządzaniem użytkownikami, takie jak logowanie, rejestracja i weryfikacja.
 */
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @Autowired
    private SerwisAplikacji serwisAplikacji;

    /**
     * Konstruktor kontrolera UserController.
     *
     * @param userService Serwis użytkowników
     * @param session     Sesja HTTP
     */
    public UserController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    /**
     * Wyświetla stronę logowania.
     *
     * @return Nazwa widoku "login"
     */
    @GetMapping("/login")
    public String login() {
        System.out.println("Wyswietlanie strony logowania");
        return "login";
    }

    /**
     * Wyświetla stronę główną aplikacji.
     *
     * @return Nazwa widoku "home"
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * Wyświetla stronę rejestracji użytkownika.
     *
     * @return Nazwa widoku "register"
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Obsługuje rejestrację nowego użytkownika, w tym weryfikację haseł, CAPTCHA i zdjęcia profilowego.
     *
     * @param imie            Imię użytkownika
     * @param nazwisko        Nazwisko użytkownika
     * @param zdjecie         Plik zdjęcia profilowego (opcjonalny)
     * @param username        Nazwa użytkownika
     * @param email           Adres e-mail użytkownika
     * @param password        Hasło użytkownika
     * @param confirmPassword Potwierdzenie hasła
     * @param captcha         Dane CAPTCHA
     * @param model           Model do przekazania danych do widoku
     * @param session         Sesja HTTP
     * @return Nazwa widoku "verify" w przypadku powodzenia lub "register" w przypadku błędu
     */
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
            userService.registerUser(username, email, password, imie, nazwisko, zdjecieBytes);

            model.addAttribute("email", email);
            return "verify";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    /**
     * Wyświetla stronę weryfikacji użytkownika.
     *
     * @param email Adres e-mail użytkownika (opcjonalny)
     * @param model Model do przekazania danych do widoku
     * @return Nazwa widoku "verify"
     */
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

    /**
     * Weryfikuje użytkownika na podstawie podanego adresu e-mail i kodu weryfikacyjnego.
     *
     * @param email Adres e-mail użytkownika
     * @param code  Kod weryfikacyjny
     * @param model Model do przekazania danych do widoku
     * @return Przekierowanie do strony logowania w przypadku powodzenia lub nazwa widoku "verify" w przypadku błędu
     * @throws RuntimeException W przypadku błędów wejścia/wyjścia podczas weryfikacji
     */
    @PostMapping("/verify")
    public String verifyUser(
            @RequestParam String email,
            @RequestParam String code,
            Model model
    ) {
        boolean verified = false;
        try {
            verified = userService.verifyUser(email, code);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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