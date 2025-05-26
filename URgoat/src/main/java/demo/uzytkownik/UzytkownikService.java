package demo.uzytkownik;

import demo.security.model.User;
import demo.security.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        return uzytkownikRepository.findByUserAccount_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika o loginie: " + username));
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

    @Transactional
    public void dodajUzytkownika(String email, String imie, String nazwisko, byte[] zdjecie) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow();

        if (imie == null || imie.isBlank()) {
            throw new IllegalArgumentException("Imię nie może być puste.");
        }

        if (nazwisko == null || nazwisko.isBlank()) {
            throw new IllegalArgumentException("Nazwisko nie może być puste.");
        }

        if (zdjecie == null || zdjecie.length == 0) {
            // TODO basic zdjecie
            throw new IllegalArgumentException("Zdjęcie nie może być puste.");
        }

        Uzytkownik uzytkownik = new Uzytkownik(
                user,
                user.getEmail(),
                user.getUsername(),
                imie,
                nazwisko,
                zdjecie,
                1
        );

        uzytkownik.setUserAccount(user);

        uzytkownikRepository.save(uzytkownik);
    }

    @Transactional
    public void aktualizujDane(Long uzytkownikID, String imie, String nazwisko, String pseudonim, MultipartFile zdjecie) throws IOException {
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

        if (pseudonim == null || pseudonim.isBlank()) {
            throw new IllegalArgumentException("Pseudonim nie może być pusty.");
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