package demo;

import demo.czat.Czat;
import demo.czat.CzatRepository;
import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.Reakcja;
import demo.reakcja.ReakcjaRepository;
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
    
    //--------------------DODAWANIE UZYTKOWNIKA---------------

    public void dodajUzytkownika(String imie, String nazwisko, byte[] zdjecie) {
        // Tworzenie nowego użytkownika
        Uzytkownik uzytkownik = new Uzytkownik(imie, nazwisko, zdjecie, null, null, 0);
        
        // Zapis do bazy danych
        uzytkownikRepository.save(uzytkownik);
    }
}
