package demo.admin;

import demo.log.DBLogRecord;
import demo.log.DBLogRecordRepository;
import demo.log.DBLogService;
import demo.log.LogOperacja;
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
}
