package demo.admin;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.log.DBLogRecord;
import demo.log.DBLogRecordRepository;
import demo.log.DBLogService;
import demo.log.LogOperacja;
import demo.post.Post;
import demo.post.PostRepository;
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
}
