package demo.czat.websocket;

import demo.czat.CzatService;
import demo.czat.WiadomoscDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    @Autowired
    private CzatService czatService;

    @MessageMapping("/chat.send/{czatId}")  // Add {czatId} here
    @SendTo("/topic/czat/{czatId}")
    public WiadomoscDTO sendMessage(@DestinationVariable int czatId, WiadomoscDTO message) {
        message.setCzatId(czatId);  // Make sure czatId is set in the DTO
        czatService.zapiszWiadomosc(message);
        return message;
    }
}
