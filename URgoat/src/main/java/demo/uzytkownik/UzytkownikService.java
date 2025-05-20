package demo.uzytkownik;

import demo.security.model.User;
import demo.security.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UzytkownikService {
    private final UzytkownikRepository uzytkownikRepository;
    private final UserRepository userRepository;

    public UzytkownikService(UzytkownikRepository uzytkownikRepository, UserRepository userRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
        this.userRepository = userRepository;
    }


    public Uzytkownik getZalogowanyUzytkownik() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return uzytkownikRepository.findFirstByPseudonim(username);
    }

    public UzytkownikTransData toTransDataBezImieniaNazwiska(Uzytkownik uzytkownik) {
        return new UzytkownikTransData(
                uzytkownik.getUzytkownikID(),
                uzytkownik.getZdjecie(),
                uzytkownik.getPseudonim()
        );
    }

    public UzytkownikTransData toTransData(Uzytkownik uzytkownik) {
        return new UzytkownikTransData(
                uzytkownik.getUzytkownikID(),
                uzytkownik.getZdjecie(),
                uzytkownik.getPseudonim(),
                uzytkownik.getImie(),
                uzytkownik.getNazwisko()
        );
    }

    public void dodajUzytkownika(String email, String imie, String nazwisko, byte[] zdjecie) {
        User user = userRepository.findByEmail(email).orElseThrow();
        // Tworzenie nowego użytkownika
        Uzytkownik uzytkownik = new Uzytkownik(imie, nazwisko, zdjecie, null, null, 0);
        uzytkownik.setPseudonim(user.getUsername());
        uzytkownik.setEmail(user.getEmail());

        // Zapis do bazy danych
        uzytkownikRepository.save(uzytkownik);
    }

    public void aktualizujDane(Long uzytkownikID, String imie, String nazwisko, MultipartFile zdjecie) throws IOException {
        Uzytkownik uzytkownik = uzytkownikRepository.findById(uzytkownikID).orElseThrow();

        if (imie == null || imie.isBlank()) {
            throw new IllegalArgumentException("Imię nie może być puste.");
        } else {
            uzytkownik.setImie(imie);
        }

        if (nazwisko == null || nazwisko.isBlank()) {
            throw new IllegalArgumentException("Nazwisko nie może być puste.");
        } else {
            uzytkownik.setNazwisko(nazwisko);
        }

        if (zdjecie == null || zdjecie.isEmpty()) {
            throw new IllegalArgumentException("Zdjęcie nie może być puste.");
        } else {
            uzytkownik.setZdjecie(zdjecie.getBytes());
        }


        uzytkownikRepository.save(uzytkownik);
    }

}