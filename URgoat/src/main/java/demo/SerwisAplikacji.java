package demo;

import demo.czat.Czat;
import demo.czat.CzatRepository;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.Reakcja;
import demo.reakcja.ReakcjaRepository;
import demo.security.model.User;
import demo.security.repository.UserRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.wiadomosc.Wiadomosc;
import demo.wiadomosc.WiadomoscRepository;
import demo.znajomy.Znajomy;
import demo.znajomy.ZnajomyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serwis aplikacji odpowiedzialny za zarządzanie głównymi funkcjonalnościami, takimi jak dodawanie znajomych, wiadomości, czatów i grup.
 */
@Service
public class SerwisAplikacji {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private KomentarzRepository komentarzRepository;

    @Autowired
    private ReakcjaRepository reakcjaRepository;

    @Autowired
    private ZnajomyRepository znajomyRepository;

    @Autowired
    private WiadomoscRepository wiadomoscRepository;

    @Autowired
    private CzatRepository czatRepository;

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Dodaje relację znajomości między dwoma użytkownikami.
     *
     * @param userId1 Identyfikator pierwszego użytkownika
     * @param userId2 Identyfikator drugiego użytkownika
     * @throws IllegalArgumentException Jeśli któryś z użytkowników nie istnieje
     */
    public void dodajZnajomego(long userId1, long userId2) {
        Uzytkownik u1 = uzytkownikRepository.findById(userId1).orElseThrow();
        Uzytkownik u2 = uzytkownikRepository.findById(userId2).orElseThrow();

        Znajomy znajomy = new Znajomy();
        znajomy.setUzytkownik(u1);
        znajomy.setUzytkownik2(u2);
        znajomyRepository.save(znajomy);
    }

    /**
     * Dodaje nową wiadomość do określonego czatu.
     *
     * @param czatId Identyfikator czatu
     * @param tresc  Treść wiadomości
     * @throws IllegalArgumentException Jeśli czat nie istnieje
     */
    public void dodajWiadomosc(long czatId, String tresc) {
        Czat czat = czatRepository.findById(czatId).orElseThrow();
        Wiadomosc wiadomosc = new Wiadomosc();
        wiadomosc.setCzat(czat);
        wiadomosc.setTresc(tresc);
        wiadomoscRepository.save(wiadomosc);
    }

    /**
     * Tworzy nowy czat między dwoma użytkownikami.
     *
     * @param userId1 Identyfikator pierwszego użytkownika
     * @param userId2 Identyfikator drugiego użytkownika
     * @throws IllegalArgumentException Jeśli któryś z użytkowników nie istnieje
     */
    public void dodajCzat(long userId1, long userId2) {
        Uzytkownik u1 = uzytkownikRepository.findById(userId1).orElseThrow();
        Uzytkownik u2 = uzytkownikRepository.findById(userId2).orElseThrow();
        Czat czat = new Czat(List.of(u1, u2));
        czatRepository.save(czat);
    }

    /**
     * Tworzy nową grupę czatu z listą użytkowników.
     *
     * @param userIds Lista identyfikatorów użytkowników
     * @throws IllegalArgumentException Jeśli grupa ma mniej niż 3 użytkowników
     */
    public void dodajGrupe(List<Long> userIds) {
        List<Uzytkownik> uczestnicy = uzytkownikRepository.findAllById(userIds);

        if (uczestnicy.size() < 3) {
            throw new IllegalArgumentException("Grupa musi mieć co najmniej 3 użytkowników");
        }

        Czat grupa = new Czat(uczestnicy);
        czatRepository.save(grupa);
    }
}