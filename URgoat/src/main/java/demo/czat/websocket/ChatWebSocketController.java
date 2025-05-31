package demo.czat.websocket;

import demo.czat.CzatService;
import demo.czat.WiadomoscDTO;
import demo.wiadomosc.Wiadomosc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Base64;

/**
 * Kontroler WebSocket obsługujący wysyłanie i odbieranie wiadomości w czatach.
 * Przetwarza wiadomości przesyłane przez użytkowników i rozsyła je do odpowiednich subskrybentów czatu.
 */
@Controller
public class ChatWebSocketController {

    @Autowired
    private CzatService czatService;

    /**
     * Przetwarza i zapisuje wiadomość wysłaną przez użytkownika w danym czacie, a następnie rozsyła ją do subskrybentów.
     * Konwertuje zdjęcie na format Base64, jeśli jest dołączone, oraz ustawia informacje o nadawcy.
     *
     * @param czatId identyfikator czatu, do którego wysyłana jest wiadomość
     * @param message obiekt DTO zawierający dane wiadomości (treść, zdjęcie, nadawca)
     * @return obiekt DTO wiadomości z dodanymi informacjami o nadawcy i zdjęciem w formacie Base64
     * @throws Exception jeśli wystąpi błąd podczas zapisywania wiadomości lub przetwarzania danych
     */
    @MessageMapping("/chat.send/{czatId}")
    @SendTo("/topic/czat/{czatId}")
    public WiadomoscDTO sendMessage(@DestinationVariable int czatId, WiadomoscDTO message) {
        try {
            // Set czatId in case it's not set in the DTO
            message.setCzatId(czatId);

            // Save message to database
            Wiadomosc savedMessage = czatService.zapiszWiadomosc(message);

            // Convert image to Base64 for the response
            if (savedMessage.getZdjecie() != null) {
                message.setZdjecie(Base64.getEncoder().encodeToString(savedMessage.getZdjecie()));
            }

            // Set sender information
            if (savedMessage.getUzytkownik() != null) {
                message.setNadawcaEmail(savedMessage.getUzytkownik().getEmail());
                message.setNadawcaDisplayName(savedMessage.getUzytkownik().getPseudonim());
            }

            return message;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}