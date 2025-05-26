package demo.czat;

import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.wiadomosc.Wiadomosc;
import demo.wiadomosc.WiadomoscRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@Transactional
public class CzatService {

    @Autowired
    private CzatRepository czatRepository;

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private WiadomoscRepository wiadomoscRepository;

    public Wiadomosc zapiszWiadomosc(WiadomoscDTO dto) {
        // Find chat and sender
        Czat czat = czatRepository.findById((long)dto.getCzatId())
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));

        Uzytkownik nadawca = uzytkownikRepository.findByEmail(dto.getNadawcaEmail());
        if (nadawca == null) {
            throw new IllegalArgumentException("Sender not found");
        }

        // Create and save message
        Wiadomosc wiadomosc = new Wiadomosc();
        wiadomosc.setCzat(czat);
        wiadomosc.setTresc(dto.getTresc());
        wiadomosc.setUzytkownik(nadawca);

        // Handle image if present
        if (dto.getZdjecie() != null && !dto.getZdjecie().isEmpty()) {
            try {
                wiadomosc.setZdjecie(Base64.getDecoder().decode(dto.getZdjecie()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid image data");
            }
        }

        return wiadomoscRepository.save(wiadomosc);
    }
}
