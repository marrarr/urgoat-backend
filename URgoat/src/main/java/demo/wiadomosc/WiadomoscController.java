package demo.wiadomosc;

import demo.security.service.SerwisAplikacji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WiadomoscController {

    @Autowired
    SerwisAplikacji serwisAplikacji;

}