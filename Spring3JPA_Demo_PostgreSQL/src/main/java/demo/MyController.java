package demo;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.komentarz.KomentarzTransData;
import demo.post.Post;
import demo.post.PostRepository;
import demo.post.PostTransData;
import demo.reakcja.Reakcja;
import demo.reakcja.ReakcjaRepository;
import demo.reakcja.ReakcjaTransData;
import demo.uzytkownik.UzytkownikTransData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyController {
    @Autowired
    PostRepository postRepository;


    /**
     * Pobiera wszystkie posty, komentarze i użytkowników z repozytoriów.
     * Przesyła stronę główną z danymi.
     * @param model
     * @return
     */
    @RequestMapping(value = "/strona_glowna", method = RequestMethod.GET)
    public String wyswietlStroneGlowna(Model model)
    {
        // Przygotowanie postów
        List<Post> posty = postRepository.findAll();
        List<PostTransData> postyTransData = new ArrayList<>();
        for (Post post : posty) {
            UzytkownikTransData uzytkownikTransData = new UzytkownikTransData(
                    post.getUzytkownikID().getUzytkownikID(),
                    post.getUzytkownikID().getZdjecie(),
                    post.getUzytkownikID().getPseudonim()
            );

            List<Komentarz> komentarze = post.getKomentarze();
            List<KomentarzTransData> komentarzeTransData = new ArrayList<>();
            for (Komentarz komentarz : komentarze) {
                UzytkownikTransData uzytkownikKomentarzTransData = new UzytkownikTransData(
                        komentarz.getUzytkownikID().getUzytkownikID(),
                        komentarz.getUzytkownikID().getZdjecie(),
                        komentarz.getUzytkownikID().getPseudonim());

                List<Reakcja> reakcje = komentarz.getReakcje();
                List<ReakcjaTransData> reakcjeTransData = new ArrayList<>();
                for (Reakcja reakcja : reakcje) {
                    Integer postID = reakcja.getPostID() != null ? reakcja.getPostID().getPostID() : null;
                    Integer komentarzID = reakcja.getKomentarzID() != null ? reakcja.getKomentarzID().getKomentarzID() : null;

                    ReakcjaTransData reakcjaTransData = new ReakcjaTransData(
                            postID,
                            komentarzID,
                            reakcja.getReakcja(),
                            uzytkownikKomentarzTransData
                    );
                    reakcjeTransData.add(reakcjaTransData);
                }

                komentarzeTransData.add(new KomentarzTransData(
                        komentarz.getKomentarzID(),
                        komentarz.getTresc(),
                        komentarz.getZdjecie(),
                        komentarz.getPostID().getPostID(),
                        uzytkownikKomentarzTransData,
                        reakcjeTransData
                ));
            }

            List<Reakcja> reakcje = post.getReakcje();
            List<ReakcjaTransData> reakcjeTransData = new ArrayList<>();
            for (Reakcja reakcja : reakcje) {
                Integer postID = reakcja.getPostID() != null ? reakcja.getPostID().getPostID() : null;
                Integer komentarzID = reakcja.getKomentarzID() != null ? reakcja.getKomentarzID().getKomentarzID() : null;

                ReakcjaTransData reakcjaTransData = new ReakcjaTransData(
                        postID,
                        komentarzID,
                        reakcja.getReakcja(),
                        uzytkownikTransData
                );
                reakcjeTransData.add(reakcjaTransData);
            }

            postyTransData.add(new PostTransData(
                    post.getPostID(),
                    post.getTresc(),
                    uzytkownikTransData,
                    post.getZdjecie(),
                    komentarzeTransData,
                    reakcjeTransData
            ));

        }

        // wypisanie w konsoli do usuniecia
        testWypisz(postyTransData);

        // przesłanie strony
        model.addAttribute("header","Strona główna");
        model.addAttribute("posty", postyTransData);

        return "wysposty";
    }

    private void testWypisz(List<PostTransData> posts) {

        System.out.println("Lista postów:");
        for (PostTransData post : posts) {
            System.out.println("Post ID: " + post.getPostId());
            System.out.println("Treść: " + post.getTresc());
            System.out.println("Autor: " + post.getAutor().getPseudonim() + " (ID: " + post.getAutor().getUzytkownikID() + ")");
            System.out.println("Reakcje:");
            for (ReakcjaTransData reakcja : post.getReakcje()) {
                if (reakcja != null) {
                    System.out.println("  - PostID: " + post.getPostId() + ", Reakcja: " + reakcja.getReakcja());
                }
            }

            // Komentarze
            System.out.println("Komentarze:");
            for (KomentarzTransData komentarz : post.getKomentarze()) {
                System.out.println("  - Komentarz ID: " + komentarz.getKomentarzID());
                System.out.println("    Treść: " + komentarz.getTresc());
                System.out.println("    Autor: " + komentarz.getUzytkownik().getPseudonim() + " (ID: " + komentarz.getUzytkownik().getUzytkownikID() + ")");
                // Reakcje komentarz
                System.out.println("    Reakcje:");
                for (ReakcjaTransData reakcja : komentarz.getReakcje()) {
                    if (reakcja != null) {
                        System.out.println("      - KomentarzID: " + komentarz.getKomentarzID() + ", Reakcja: " + reakcja.getReakcja() + ", Autor: " + reakcja.getUzytkownik().getPseudonim());
                    }
                }
//
            }

            System.out.println("---");
        }
    }
}
