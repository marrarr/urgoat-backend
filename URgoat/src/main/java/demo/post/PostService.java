package demo.post;

import demo.komentarz.KomentarzService;
import demo.komentarz.KomentarzTransData;
import demo.log.LogOperacja;
import demo.log.URgoatLogger;
import demo.reakcja.ReakcjaService;
import demo.reakcja.ReakcjaTransData;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.uzytkownik.UzytkownikTransData;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Serwis obsługujący operacje związane z postami.
 * Umożliwia pobieranie, dodawanie, usuwanie i aktualizowanie postów oraz ich konwersję na obiekty DTO.
 */
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UzytkownikRepository uzytkownikRepository;
    private final UzytkownikService uzytkownikService;
    private final KomentarzService komentarzService;
    private final ReakcjaService reakcjaService;

    /**
     * Konstruktor serwisu PostService, wstrzykujący wymagane zależności.
     *
     * @param postRepository repozytorium postów
     * @param uzytkownikRepository repozytorium użytkowników
     * @param uzytkownikService serwis użytkowników
     * @param komentarzService serwis komentarzy
     * @param reakcjaService serwis reakcji
     */
    public PostService(PostRepository postRepository, UzytkownikRepository uzytkownikRepository,
                       UzytkownikService uzytkownikService, KomentarzService komentarzService,
                       ReakcjaService reakcjaService) {
        this.postRepository = postRepository;
        this.uzytkownikRepository = uzytkownikRepository;
        this.uzytkownikService = uzytkownikService;
        this.komentarzService = komentarzService;
        this.reakcjaService = reakcjaService;
    }

    /**
     * Pobiera wszystkie posty wraz z komentarzami i reakcjami, konwertując je na obiekty DTO.
     * Posty są sortowane malejąco według identyfikatora.
     *
     * @return lista obiektów DTO zawierających dane postów, komentarzy i reakcji
     */
    public List<PostTransData> getPostyZKomentarzamiOrazReakcjami() {
        List<Post> posty = postRepository.findAll(Sort.by(Sort.Direction.DESC, "postID"));
        List<PostTransData> postyTransData = new ArrayList<>();
        for (Post post : posty) {
            UzytkownikTransData uzytkownikTransData = uzytkownikService.toTransDataBezImieniaNazwiska(post.getUzytkownik());
            List<KomentarzTransData> komentarzeTransData = komentarzService.toTransData(post.getKomentarze());
            List<ReakcjaTransData> reakcjeTransData = reakcjaService.toTransData(post.getReakcje());

            postyTransData.add(new PostTransData(
                    post.getPostID(),
                    post.getTresc(),
                    uzytkownikTransData,
                    post.getZdjecie(),
                    komentarzeTransData,
                    reakcjeTransData
            ));
        }

        return postyTransData;
    }

    /**
     * Pobiera wszystkie posty bez komentarzy i reakcji, konwertując je na obiekty DTO.
     *
     * @return lista obiektów DTO zawierających podstawowe dane postów
     */
    public List<PostTransData> getPosty() {
        List<Post> posty = postRepository.findAll();
        List<PostTransData> postyTransData = new ArrayList<>();
        for (Post post : posty) {
            postyTransData.add(new PostTransData(
                    post.getPostID(),
                    post.getTresc(),
                    null,
                    post.getZdjecie(),
                    null,
                    null
            ));
        }
        return postyTransData;
    }

    /**
     * Dodaje nowy post dla użytkownika.
     *
     * @param userId identyfikator użytkownika dodającego post
     * @param tresc treść posta
     * @throws RuntimeException jeśli użytkownik nie zostanie znaleziony
     */
    @Transactional
    public void dodajPost(long userId, String tresc) {
        Uzytkownik user = uzytkownikRepository.findById(userId).orElseThrow();
        Post post = new Post();
        post.setUzytkownik(user);
        post.setTresc(tresc);
        postRepository.save(post);

        URgoatLogger.uzytkownikInfo("Dodano post id=" + post.getPostID() + ", dlugosc=" + post.getTresc().length(),
                user.getPseudonim(),
                LogOperacja.DODAWANIE);
    }

    /**
     * Usuwa post o podanym identyfikatorze.
     *
     * @param postID identyfikator posta do usunięcia
     * @throws RuntimeException jeśli post nie zostanie znaleziony
     */
    @Transactional
    public void usunPost(long postID) {
        Post post = postRepository.findById(postID).orElseThrow();
        postRepository.delete(post);

        URgoatLogger.uzytkownikInfo("Usunięto post id=" + post.getPostID(),
                uzytkownikService.getZalogowanyUzytkownik().getPseudonim(),
                LogOperacja.USUWANIE);
    }

    /**
     * Aktualizuje treść posta o podanym identyfikatorze.
     *
     * @param postID identyfikator posta do aktualizacji
     * @param tresc nowa treść posta
     * @throws RuntimeException jeśli post nie zostanie znaleziony
     */
    @Transactional
    public void zaktualizujPost(long postID, String tresc) {
        Post post = postRepository.findById(postID)
                .orElseThrow(() -> new RuntimeException("Post nie został znaleziony: ID = " + postID));

        post.setTresc(tresc);
        postRepository.save(post);

        URgoatLogger.uzytkownikInfo("Zaktualizowano post id=" + post.getPostID() + ", dlugosc=" + post.getTresc().length(),
                uzytkownikService.getZalogowanyUzytkownik().getPseudonim(),
                LogOperacja.AKTUALIZOWANIE);
    }
}