package demo.reakcja;

import demo.komentarz.KomentarzRepository;
import demo.post.PostRepository;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ReakcjaServiceTest {
    private UzytkownikService uzytkownikService;
    private UzytkownikRepository uzytkownikRepository;
    private PostRepository postRepository;
    private KomentarzRepository komentarzRepository;
    private ReakcjaRepository reakcjaRepository;
    private ReakcjaService reakcjaService;

    @BeforeEach
    void setUp() {
        uzytkownikService = mock(UzytkownikService.class);
        uzytkownikRepository = mock(UzytkownikRepository.class);
        postRepository = mock(PostRepository.class);
        komentarzRepository = mock(KomentarzRepository.class);
        reakcjaRepository = mock(ReakcjaRepository.class);
        reakcjaService = new ReakcjaService(
                uzytkownikService,
                uzytkownikRepository,
                postRepository,
                komentarzRepository,
                reakcjaRepository
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void toTransData() {
        Reakcja reakcja = new Reakcja();
        reakcja.set
    }

    @Test
    void testToTransData() {
    }
}