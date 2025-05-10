package demo.komentarz;

import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.ReakcjaService;
import demo.security.model.User;
import demo.security.repository.UserRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KomentarzService {
    @Autowired
    private KomentarzRepository komentarzRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UzytkownikRepository uzytkownikRepository;
    @Autowired
    private UzytkownikService uzytkownikService;
    @Autowired
    private ReakcjaService reakcjaService;
    @Autowired
    private UserRepository userRepository;


    public KomentarzTransData toTransData(Komentarz komentarz) {
        return new KomentarzTransData(
                komentarz.getKomentarzID(),
                komentarz.getTresc(),
                komentarz.getZdjecie(),
                komentarz.getPostID().getPostID(),
                uzytkownikService.toTransData(komentarz.getUzytkownikID()),
                reakcjaService.toTransData(komentarz.getReakcje(), komentarz.getUzytkownikID())
        );
    }

    public List<KomentarzTransData> toTransData(List<Komentarz> komentarze) {
        return komentarze
                .stream()
                .map(komentarz -> toTransData(komentarz))
                .toList();
    }

    public void dodajKomentarz(long userId, long postId, String tresc) {
        Uzytkownik user = uzytkownikRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        Komentarz komentarz = new Komentarz();
        komentarz.setUzytkownikID(user);
        komentarz.setPostID(post);
        komentarz.setTresc(tresc);
        komentarzRepository.save(komentarz);
    }

    public void usunKomentarz(long komentarzID) {
        Komentarz komentarz = komentarzRepository.findById(komentarzID).orElseThrow();

        // post moze usunac tylko autor lub osoba z permisjami, np admin
        Uzytkownik uzytkownik_zalogowany = uzytkownikService.getZalogowanyUzytkownik();
        User user = userRepository.findByEmail(uzytkownik_zalogowany.getEmail()).orElseThrow();
        if (user.getRole().equals("ROLE_ADMIN") ||
                uzytkownik_zalogowany.getUzytkownikID() == komentarz.getUzytkownikID().getUzytkownikID()) {
            komentarzRepository.delete(komentarz);
        } else {
            throw new AccessDeniedException("Brak uprawnień do usunięcia komentarza");
        }
    }
}