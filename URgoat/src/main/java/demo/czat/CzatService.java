package demo.czat;

import demo.SerwisAplikacji;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.wiadomosc.Wiadomosc;
import demo.wiadomosc.WiadomoscRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class CzatService {

    @Autowired
    private CzatRepository czatRepository;
    @Autowired
    private UzytkownikRepository uzytkownikRepository;
    @Autowired
    private WiadomoscRepository wiadomoscRepository;

    public void zapiszWiadomosc(WiadomoscDTO dto) {
        Czat czat = czatRepository.findById((long)dto.getCzatId()).orElseThrow();
        Uzytkownik nadawca = uzytkownikRepository.findByEmail(dto.getNadawcaEmail());

        Wiadomosc wiadomosc = new Wiadomosc();
        wiadomosc.setCzat(czat);
        wiadomosc.setTresc(dto.getTresc());
        wiadomosc.setZdjecie(dto.getZdjecie());
        wiadomosc.setUzytkownik(nadawca);  // You need to add this field to Wiadomosc entity

        wiadomoscRepository.save(wiadomosc);
    }
}
