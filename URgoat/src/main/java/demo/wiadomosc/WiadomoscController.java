package demo.wiadomosc;

import demo.SerwisAplikacji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Kontroler obsługujący żądania związane z zarządzaniem wiadomościami w systemie.
 */
@Controller
public class WiadomoscController {

    @Autowired
    SerwisAplikacji serwisAplikacji;

}