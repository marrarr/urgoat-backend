package demo.admin;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.log.DBLogRecord;
import demo.log.DBLogRecordRepository;
import demo.log.DBLogService;
import demo.log.LogOperacja;
import demo.post.Post;
import demo.post.PostRepository;
import demo.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Kontroler obsługujący funkcjonalności panelu administracyjnego.
 * Udostępnia punkty końcowe do wyświetlania logów, postów, komentarzy oraz generowania raportów.
 */
@Controller
@RequestMapping("/admin")
public class AdminPanelController {
    @Autowired
    private DBLogRecordRepository logRepository;
    @Autowired
    private DBLogService dBLogService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private KomentarzRepository komentarzRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Wyświetla główną stronę panelu administracyjnego.
     *
     * @return nazwa szablonu widoku dla panelu administracyjnego
     */
    @GetMapping("/panel")
    public String wyswietlPanel() {
        return "admin/panel";
    }

    /**
     * Wyświetla stronę z wpisami logów z opcjonalnym filtrowaniem.
     *
     * @param username opcjonalny filtr dla nazwy użytkownika
     * @param operacja opcjonalny filtr dla typu operacji
     * @param level    opcjonalny filtr dla poziomu logów (np. INFO, WARN, ERROR, DEBUG)
     * @param model    model do przekazania atrybutów do widoku
     * @return nazwa szablonu widoku dla wyświetlania logów
     */
    @GetMapping("/log")
    public String wyswietlLogi(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) LogOperacja operacja,
            @RequestParam(required = false) String level,
            Model model) {

        List<DBLogRecord> logi = dBLogService.findAllFiltruj(username, operacja, level);

        model.addAttribute("logi", logi);
        model.addAttribute("levels", List.of("INFO", "FINE", "WARN", "ERROR", "DEBUG"));
        model.addAttribute("operacje", List.of(LogOperacja.values()));

        return "admin/wyswietl-logi";
    }

    /**
     * Wyświetla listę wszystkich postów do zarządzania administracyjnego.
     *
     * @param model model do przekazania atrybutów do widoku
     * @return nazwa szablonu widoku dla wyświetlania postów
     */
    @GetMapping("/adm_post")
    public String wyswietlAdm_post(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "admin/list_postow";
    }

    /**
     * Wyświetla listę wszystkich komentarzy do zarządzania administracyjnego.
     *
     * @param model model do przekazania atrybutów do widoku
     * @return nazwa szablonu widoku dla wyświetlania komentarzy
     */
    @GetMapping("/adm_komentarz")
    public String wyswietlAdm_komentarz(Model model) {
        List<Komentarz> comments = komentarzRepository.findAll();
        model.addAttribute("comments", comments);
        return "admin/list_komentarzy";
    }

    /**
     * Wyświetla stronę przeglądu raportów.
     *
     * @return nazwa szablonu widoku dla listy raportów
     */
    @GetMapping("/raporty")
    public String wyswietlRaporty() {
        return "admin/raporty_list";
    }

    /**
     * Wyświetla raport z łączną liczbą postów.
     *
     * @param model model do przekazania atrybutów do widoku
     * @return nazwa szablonu widoku dla raportu postów
     */
    @GetMapping("/raport_postow")
    public String wyswietlRaportyPosty(Model model) {
        long liczbaPostow = postRepository.count();
        model.addAttribute("liczbaPostow", liczbaPostow);
        return "admin/raporty_post";
    }

    /**
     * Wyświetla raport z łączną liczbą komentarzy.
     *
     * @param model model do przekazania atrybutów do widoku
     * @return nazwa szablonu widoku dla raportu komentarzy
     */
    @GetMapping("/raport_komentarzy")
    public String wyswietlRaportyKomentarze(Model model) {
        long liczbaKomentarzy = komentarzRepository.count();
        model.addAttribute("liczbaKomentarzy", liczbaKomentarzy);
        return "admin/raporty_kom";
    }

    /**
     * Wyświetla raport z łączną liczbą zarejestrowanych użytkowników.
     *
     * @param model model do przekazania atrybutów do widoku
     * @return nazwa szablonu widoku dla raportu użytkowników
     */
    @GetMapping("/raport_uzytkownikow")
    public String wyswietlRaportyUzytkownikow(Model model) {
        long liczbaZarejestrowanych = userRepository.count();
        model.addAttribute("liczbaZarejestrowanych", liczbaZarejestrowanych);
        return "admin/raporty_uzy";
    }
}