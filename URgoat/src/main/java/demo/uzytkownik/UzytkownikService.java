package demo.uzytkownik;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UzytkownikService {
    private final UzytkownikRepository uzytkownikRepository;

    public UzytkownikService(UzytkownikRepository uzytkownikRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
    }


    public Uzytkownik getZalogowanyUzytkownik() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return uzytkownikRepository.findFirstByPseudonim(username);
    }

    public UzytkownikTransData toTransData(Uzytkownik uzytkownik) {
        return new UzytkownikTransData(
                uzytkownik.getUzytkownikID(),
                uzytkownik.getZdjecie(),
                uzytkownik.getPseudonim()
        );
    }
}
