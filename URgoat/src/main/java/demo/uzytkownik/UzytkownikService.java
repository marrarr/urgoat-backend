package demo.uzytkownik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UzytkownikService {
    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    public Uzytkownik getZalogowanyUzytkownik() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return uzytkownikRepository.findFirstByPseudonim(username);
    }
}
