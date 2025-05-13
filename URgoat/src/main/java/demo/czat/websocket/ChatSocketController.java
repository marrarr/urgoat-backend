package demo.czat.websocket;

import demo.czat.Czat;
import demo.czat.CzatRepository;
import demo.SerwisAplikacji;
import demo.wiadomosc.WiadomoscRepository;
import demo.wiadomosc.WiadomoscTransData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

    @Autowired
    private WiadomoscRepository wiadomoscRepository;

    @Autowired
    private CzatRepository czatRepository;

    @Autowired
    SerwisAplikacji serwisAplikacji;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String handle(WiadomoscTransData wiadomosc) {
        Czat czat = czatRepository.findById((long)wiadomosc.getCzatID())
                .orElseThrow(() -> new RuntimeException("Czat nie istnieje"));

        serwisAplikacji.dodajWiadomosc(wiadomosc.getCzatID(), wiadomosc.getTresc());

        return wiadomosc.getTresc();
    }
}
