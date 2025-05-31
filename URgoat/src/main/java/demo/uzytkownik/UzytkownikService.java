package demo.uzytkownik;

import demo.log.LogOperacja;
import demo.log.URgoatLogger;
import demo.security.model.User;
import demo.security.repository.UserRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Serwis odpowiedzialny za zarządzanie profilami użytkowników, w tym dodawanie, aktualizowanie danych oraz pobieranie informacji o zalogowanym użytkowniku.
 */
@Service
public class UzytkownikService {

    private final UzytkownikRepository uzytkownikRepository;
    private final UserRepository userRepository;

    /**
     * Konstruktor serwisu UzytkownikService.
     *
     * @param uzytkownikRepository Repozytorium profili użytkowników
     * @param userRepository       Repozytorium kont użytkowników
     */
    public UzytkownikService(UzytkownikRepository uzytkownikRepository, UserRepository userRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
        this.userRepository = userRepository;
    }

    /**
     * Pobiera dane aktualnie zalogowanego użytkownika.
     *
     * @return Obiekt Uzytkownik reprezentujący zalogowanego użytkownika
     * @throws UsernameNotFoundException Jeśli użytkownik nie zostanie znaleziony
     */
    public Uzytkownik getZalogowanyUzytkownik() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return uzytkownikRepository.findByUserAccount_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika o loginie: " + username));
    }

    /**
     * Konwertuje obiekt Uzytkownik na obiekt UzytkownikTransData bez imienia i nazwiska.
     *
     * @param uzytkownik Obiekt Uzytkownik do konwersji
     * @return Obiekt UzytkownikTransData zawierający dane użytkownika
     */
    public UzytkownikTransData toTransDataBezImieniaNazwiska(Uzytkownik uzytkownik) {
        return new UzytkownikTransData(
                uzytkownik.getUzytkownikID(),
                uzytkownik.getZdjecie(),
                uzytkownik.getPseudonim()
        );
    }

    /**
     * Konwertuje obiekt Uzytkownik na obiekt UzytkownikTransData z pełnymi danymi.
     *
     * @param uzytkownik Obiekt Uzytkownik do konwersji
     * @return Obiekt UzytkownikTransData zawierający dane użytkownika
     */
    public UzytkownikTransData toTransData(Uzytkownik uzytkownik) {
        return new UzytkownikTransData(
                uzytkownik.getUzytkownikID(),
                uzytkownik.getZdjecie(),
                uzytkownik.getPseudonim(),
                uzytkownik.getImie(),
                uzytkownik.getNazwisko()
        );
    }

    /**
     * Dodaje nowego użytkownika do systemu.
     *
     * @param email   Adres e-mail użytkownika
     * @param imie    Imię użytkownika
     * @param nazwisko Nazwisko użytkownika
     * @param zdjecie Zdjęcie profilowe w formacie bajtów
     * @throws IOException              W przypadku błędów wejścia/wyjścia podczas wczytywania domyślnego zdjęcia
     * @throws IllegalArgumentException Jeśli imię lub nazwisko jest puste
     */
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
            ClassPathResource zdjecieSciezka = new ClassPathResource("static/avatary_uzytkownikow/default-avatar.png");
            try {
                zdjecie = zdjecieSciezka.getInputStream().readAllBytes();
            } catch (IOException e) {
                throw new IOException("Nie udało się wczytać zdjęcia.");
            }
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

        URgoatLogger.uzytkownikInfo("Dodano użytkownika username=" + user.getUsername(),
                "server",
                LogOperacja.DODAWANIE);
    }

    /**
     * Aktualizuje dane istniejącego użytkownika.
     *
     * @param uzytkownikID Identyfikator użytkownika
     * @param imie         Nowe imię użytkownika
     * @param nazwisko     Nowe nazwisko użytkownika
     * @param pseudonim    Nowy pseudonim użytkownika
     * @param zdjecie      Nowe zdjęcie profilowe
     * @throws IOException              W przypadku błędów wejścia/wyjścia podczas przetwarzania zdjęcia
     * @throws IllegalArgumentException Jeśli imię, nazwisko lub pseudonim jest puste
     */
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
            uzytkownik.setPseudonim(pseudonim);
        }

        if (zdjecie == null || zdjecie.isEmpty()) {
            ClassPathResource zdjecieSciezka = new ClassPathResource("static/avatary_uzytkownikow/default-avatar.png");
            byte[] zdjecieBajty = zdjecieSciezka.getInputStream().readAllBytes();
            uzytkownik.setZdjecie(zdjecieBajty);
        } else {
            uzytkownik.setZdjecie(zdjecie.getBytes());
        }

        uzytkownikRepository.save(uzytkownik);

        URgoatLogger.uzytkownikInfo("Zaktualizowano dane użytkownika id=" + uzytkownik.getUzytkownikID(),
                getZalogowanyUzytkownik().getPseudonim(),
                LogOperacja.AKTUALIZOWANIE);
    }
}