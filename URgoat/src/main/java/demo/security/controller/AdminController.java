package demo.security.controller;

import demo.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Kontroler obsługujący żądania związane z panelem administracyjnym.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    /**
     * Konstruktor kontrolera AdminController.
     *
     * @param userService Serwis użytkowników
     */
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Wyświetla listę wszystkich użytkowników w panelu administracyjnym.
     *
     * @param model Model do przekazania danych do widoku
     * @return Nazwa widoku "admin/users"
     */
    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    /**
     * Usuwa użytkownika na podstawie podanego identyfikatora.
     *
     * @param id Identyfikator użytkownika do usunięcia
     * @return Przekierowanie do listy użytkowników
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}