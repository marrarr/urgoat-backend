package demo.post;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzService;
import demo.komentarz.KomentarzTransData;
import demo.reakcja.Reakcja;
import demo.reakcja.ReakcjaService;
import demo.reakcja.ReakcjaTransData;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.uzytkownik.UzytkownikTransData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UzytkownikRepository uzytkownikRepository;
    private final UzytkownikService uzytkownikService;
    private final KomentarzService komentarzService;
    private final ReakcjaService reakcjaService;

    public PostService(PostRepository postRepository, UzytkownikRepository uzytkownikRepository, UzytkownikService uzytkownikService, KomentarzService komentarzService, ReakcjaService reakcjaService) {
        this.postRepository = postRepository;
        this.uzytkownikRepository = uzytkownikRepository;
        this.uzytkownikService = uzytkownikService;
        this.komentarzService = komentarzService;
        this.reakcjaService = reakcjaService;
    }


    public List<PostTransData> getPostyZKomentarzamiOrazReakcjami() {
        // Przygotowanie postów
        List<Post> posty = postRepository.findAll(Sort.by(Sort.Direction.DESC, "postID"));
        List<PostTransData> postyTransData = new ArrayList<>();
        for (Post post : posty) {
            UzytkownikTransData uzytkownikTransData = uzytkownikService.toTransData(post.getUzytkownik());
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

    public List<PostTransData> getPosty() {
        List<Post> posty = postRepository.findAll();  // Pobierz wszystkie posty
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

    public void dodajPost(long userId, String tresc) {
        Uzytkownik user = uzytkownikRepository.findById(userId).orElseThrow();
        Post post = new Post();
        post.setUzytkownik(user);
        post.setTresc(tresc);
        postRepository.save(post);
    }

    public void usunPost(long postID) {
        Post post = postRepository.findById(postID).orElseThrow();
        postRepository.delete(post);
    }

    public void zaktualizujPost(long postID, String tresc) {
        Post post = postRepository.findById(postID).orElseThrow();
        post.setTresc(tresc);
        postRepository.save(post);
    }
}
