package demo.post;

import demo.komentarz.KomentarzService;
import demo.reakcja.ReakcjaService;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {
    private PostRepository postRepository;
    private UzytkownikRepository uzytkownikRepository;
    private UzytkownikService uzytkownikService;
    private KomentarzService komentarzService;
    private ReakcjaService reakcjaService;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        uzytkownikRepository = mock(UzytkownikRepository.class);
        uzytkownikService = mock(UzytkownikService.class);
        komentarzService = mock(KomentarzService.class);
        reakcjaService = mock(ReakcjaService.class);
        postService = new PostService(
                postRepository,
                uzytkownikRepository,
                uzytkownikService,
                komentarzService,
                reakcjaService
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPostyZKomentarzamiOrazReakcjami() {
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setUzytkownikID(1);

        Post post = new Post();
        post.setPostID(1);
        post.setTresc("Diss na miszora i jego wpływ na wynik wyborczy Macieja Miaciaka.");
        post.setUzytkownikID(uzytkownik);
        post.setZdjecie(null);
    }

    @Test
    void getPosty() {
    }

    @Test
    void dodajPost() {

    }

    @Test
    void usunPost() {
    }

    @Test
    void zaktualizujPost() {
    }
}