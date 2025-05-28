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

    @GetMapping("/panel")
    public String wyswietlPanel(){
        return "admin/panel";
    }

    @GetMapping("/log")
    public String wyswietlLogi(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) LogOperacja operacja,
            @RequestParam(required = false) String level,
            Model model) {

        List<DBLogRecord> logi = dBLogService.findAllFiltruj(username, operacja, level);

        model.addAttribute("logi", logi);
        model.addAttribute("levels", List.of("INFO", "WARN", "ERROR", "DEBUG"));
        model.addAttribute("operacje", List.of(LogOperacja.values()));


        return "admin/wyswietl-logi";
    }

    @GetMapping("/adm_post")
    public String wyswietlAdm_post(Model model){
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "admin/list_postow";
    }

    @GetMapping("/adm_komentarz")
    public String wyswietlAdm_komentarz(Model model){
        List<Komentarz> comments = komentarzRepository.findAll();
        model.addAttribute("comments", comments);
        return "admin/list_komentarzy";
    }

    @GetMapping("/raporty")
    public String wyswietlRaporty(){
        return "admin/raporty_list";
    }

    @GetMapping("/raport_postow")
    public String wyswietlRaportyPosty(Model model) {
        long liczbaPostow = postRepository.count();
        model.addAttribute("liczbaPostow", liczbaPostow);
        return "admin/raporty_post";
    }

    @GetMapping("/raport_komentarzy")
    public String wyswietlRaportyKomentarze(Model model) {
        long liczbaKomentarzy = komentarzRepository.count();
        model.addAttribute("liczbaKomentarzy", liczbaKomentarzy);
        return "admin/raporty_kom";
    }

    @GetMapping("/raport_uzytkownikow")
    public String wyswietlRaportyUzytkownikow(Model model) {
        long liczbaZarejestrowanych = userRepository.count();
        model.addAttribute("liczbaZarejestrowanych", liczbaZarejestrowanych);
        return "admin/raporty_uzy";
    }
}
