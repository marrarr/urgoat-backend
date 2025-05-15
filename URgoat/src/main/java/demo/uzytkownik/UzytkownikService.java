package demo.uzytkownik;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public void aktualizujDane(Long uzytkownikID, String imie, String nazwisko, MultipartFile zdjecie) {
        Uzytkownik uzytkownik = uzytkownikRepository.findById(uzytkownikID).orElseThrow();

        if (imie.isBlank() || nazwisko.isBlank()) {
            throw new IllegalArgumentException("Imię i nazwisko nie może być puste.");
        }

        uzytkownik.setImie(imie);
        uzytkownik.setNazwisko(nazwisko);
 /*       try {
            uzytkownik.setZdjecie(zdjecie.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        uzytkownikRepository.save(uzytkownik);
    }
}
