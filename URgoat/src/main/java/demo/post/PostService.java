package demo.post;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzService;
import demo.komentarz.KomentarzTransData;
import demo.reakcja.Reakcja;
import demo.reakcja.ReakcjaService;
import demo.reakcja.ReakcjaTransData;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.uzytkownik.UzytkownikTransData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    KomentarzService komentarzService;
    @Autowired
    ReakcjaService reakcjaService;
    @Autowired
    private UzytkownikService uzytkownikService;



    public List<PostTransData> getPostyZKomentarzamiOrazReakcjami() {
        // Przygotowanie postów
        List<Post> posty = postRepository.findAll();
        List<PostTransData> postyTransData = new ArrayList<>();
        for (Post post : posty) {
            UzytkownikTransData uzytkownikTransData = uzytkownikService.toTransData(post.getUzytkownikID());
            List<KomentarzTransData> komentarzeTransData = komentarzService.toTransData(post.getKomentarze());
            List<ReakcjaTransData> reakcjeTransData = reakcjaService.toTransData(post.getReakcje(), post.getUzytkownikID());

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
