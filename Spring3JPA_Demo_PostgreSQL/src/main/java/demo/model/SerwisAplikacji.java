package demo.model;

import demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

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

    // ----------------------- POST -----------------------

    public void dodajPost(long userId, String tresc) {
        Uzytkownik user = uzytkownikRepository.findById(userId).orElseThrow();
        Post post = new Post();
        post.setUzytkownikID(user);
        post.setTresc(tresc);
        postRepository.save(post);
    }

    // ----------------------- KOMENTARZ -----------------------

    public void dodajKomentarz(long userId, long postId, String tresc) {
        Uzytkownik user = uzytkownikRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        Komentarz komentarz = new Komentarz();
        komentarz.setUzytkownikID(user);
        komentarz.setPostID(post);
        komentarz.setTresc(tresc);
        komentarzRepository.save(komentarz);
    }

    // ----------------------- REAKCJA -----------------------

    public void dodajReakcje(long userId, Long postId, Long komentarzId, int kodReakcji) {
        Uzytkownik user = uzytkownikRepository.findById(userId).orElseThrow();
        Reakcja reakcja = new Reakcja();
        reakcja.setUzytkownikID(user);
        reakcja.setReakcja(kodReakcji);

        if (postId != null) {
            Post post = postRepository.findById(postId).orElseThrow();
            reakcja.setPostID(post);
        }

        if (komentarzId != null) {
            Komentarz komentarz = komentarzRepository.findById(komentarzId).orElseThrow();
            reakcja.setKomentarzID(komentarz);
        }

        reakcjaRepository.save(reakcja);
    }

    // ----------------------- ZNAJOMY -----------------------

    public void dodajZnajomego(long userId1, long userId2) {
        Uzytkownik u1 = uzytkownikRepository.findById(userId1).orElseThrow();
        Uzytkownik u2 = uzytkownikRepository.findById(userId2).orElseThrow();

        Znajomy znajomy = new Znajomy();
        znajomy.setUzytkownikID(u1);
        znajomy.setUzytkownik2ID(u2);
        znajomyRepository.save(znajomy);
    }

    // ----------------------- WIADOMOŚĆ -----------------------

    public void dodajWiadomosc(long czatId, String tresc) {
        Czat czat = czatRepository.findById(czatId).orElseThrow();
        Wiadomosc wiadomosc = new Wiadomosc();
        wiadomosc.setCzatID(czat);
        wiadomosc.setTresc(tresc);
        wiadomoscRepository.save(wiadomosc);
    }

    // ----------------------- CZAT -----------------------

    public void dodajCzat(long userId1, long userId2) {
        Uzytkownik u1 = uzytkownikRepository.findById(userId1).orElseThrow();
        Uzytkownik u2 = uzytkownikRepository.findById(userId2).orElseThrow();
        Czat czat = new Czat(List.of(u1, u2));
        czatRepository.save(czat);
    }

    // ----------------------- GRUPA -----------------------

    public void dodajGrupe(List<Long> userIds) {
        List<Uzytkownik> uczestnicy = uzytkownikRepository.findAllById(userIds);

        if (uczestnicy.size() < 3) {
            throw new IllegalArgumentException("Grupa musi mieć co najmniej 3 użytkowników");
        }

        Czat grupa = new Czat(uczestnicy);
        czatRepository.save(grupa);
    }
}
