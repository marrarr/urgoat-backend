package demo.reakcja;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.uzytkownik.UzytkownikTransData;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReakcjaService {
    private final UzytkownikService uzytkownikService;
    private final UzytkownikRepository uzytkownikRepository;
    private final PostRepository postRepository;
    private final KomentarzRepository komentarzRepository;
    private final ReakcjaRepository reakcjaRepository;

    public ReakcjaService(UzytkownikService uzytkownikService, UzytkownikRepository uzytkownikRepository, PostRepository postRepository, KomentarzRepository komentarzRepository, ReakcjaRepository reakcjaRepository) {
        this.uzytkownikService = uzytkownikService;
        this.uzytkownikRepository = uzytkownikRepository;
        this.postRepository = postRepository;
        this.komentarzRepository = komentarzRepository;
        this.reakcjaRepository = reakcjaRepository;
    }

    public ReakcjaTransData toTransData(Reakcja reakcja) {
        Integer postID = reakcja.getPost() != null ? reakcja.getPost().getPostID() : null;
        Integer komentarzID = reakcja.getKomentarz() != null ? reakcja.getKomentarz().getKomentarzID() : null;
        UzytkownikTransData uzytkownikKomentarzTransData = uzytkownikService.toTransDataBezImieniaNazwiska(reakcja.getUzytkownik());

        return new ReakcjaTransData(
                postID,
                komentarzID,
                reakcja.getReakcja(),
                uzytkownikKomentarzTransData
        );
    }

    public List<ReakcjaTransData> toTransData(List<Reakcja> reakcje) {
        return reakcje
                .stream()
                .map(reakcja -> toTransData(reakcja))
                .toList();
    }

    @Transactional
    public void dodajReakcje(long userId, Long postId, Long komentarzId, int kodReakcji) {
        Uzytkownik user = uzytkownikRepository.findById(userId).orElseThrow();
        Reakcja reakcja = new Reakcja();
        reakcja.setUzytkownik(user);
        reakcja.setReakcja(kodReakcji);

        if (postId == null && komentarzId == null) {
            throw new IllegalArgumentException("Reakcja nie jest przypisana do posta lub komentarza");
        }

        if (postId != null && komentarzId != null) {
            throw new IllegalArgumentException("Reakcja nie może być przypisana jednocześnie do posta i komentarza");
        }

        if (postId != null) {
            Post post = postRepository.findById(postId).orElseThrow();
            reakcja.setPost(post);
        }

        if (komentarzId != null) {
            Komentarz komentarz = komentarzRepository.findById(komentarzId).orElseThrow();
            reakcja.setKomentarz(komentarz);
        }

        reakcjaRepository.save(reakcja);
    }

    @Transactional
    public void usunReakcje(long reakcjaID) {
        Uzytkownik uzytkownik_zalogowany = uzytkownikService.getZalogowanyUzytkownik();
        Reakcja reakcja = reakcjaRepository.findById(reakcjaID).orElseThrow();

        // reakcje moze aktualizować tylko autor
        if (uzytkownik_zalogowany.getUzytkownikID() == reakcja.getUzytkownik().getUzytkownikID()) {
            reakcjaRepository.delete(reakcja);
        } else {
            throw new AccessDeniedException("Brak uprawnień do usunięcia komentarza");
        }
    }
}