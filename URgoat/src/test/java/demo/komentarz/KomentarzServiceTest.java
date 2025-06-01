package demo.komentarz;

import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.ReakcjaService;
import demo.security.model.User;
import demo.security.repository.UserRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KomentarzServiceTest {

    @InjectMocks
    private KomentarzService komentarzService;

    @Mock
    private KomentarzRepository komentarzRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UzytkownikRepository uzytkownikRepository;

    @Mock
    private UzytkownikService uzytkownikService;

    @Mock
    private ReakcjaService reakcjaService;

    @Mock
    private UserRepository userRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void dodajKomentarz_powinienDodacKomentarz() {
        long userId = 1L;
        long postId = 2L;
        String tresc = "Test komentarza";

        Uzytkownik uzytkownik = new Uzytkownik();
        Post post = new Post();

        when(uzytkownikRepository.findById(userId)).thenReturn(Optional.of(uzytkownik));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        komentarzService.dodajKomentarz(userId, postId, tresc);

        verify(komentarzRepository).save(argThat(k ->
                k.getTresc().equals(tresc) &&
                        k.getUzytkownik() == uzytkownik &&
                        k.getPost() == post
        ));
    }

    @Test
    void usunKomentarz_powinienUsunacKomentarzJezeliAdmin() {
        Komentarz komentarz = new Komentarz();
        Uzytkownik u = new Uzytkownik(); u.setEmail("admin@example.com");
        komentarz.setUzytkownik(u);

        User admin = new User(); admin.setRole("ROLE_ADMIN");

        when(komentarzRepository.findById(1L)).thenReturn(Optional.of(komentarz));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(u);
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(admin));

        komentarzService.usunKomentarz(1L);

        verify(komentarzRepository).delete(komentarz);
    }

    @Test
    void usunKomentarz_powinienRzucicAccessDeniedDlaInnegoUzytkownika() {
        Komentarz komentarz = new Komentarz();
        Uzytkownik autor = new Uzytkownik(); autor.setUzytkownikID(5);
        komentarz.setUzytkownik(autor);

        Uzytkownik zalogowany = new Uzytkownik(); zalogowany.setUzytkownikID(99); zalogowany.setEmail("user@x.pl");
        User user = new User(); user.setRole("ROLE_USER");

        when(komentarzRepository.findById(1L)).thenReturn(Optional.of(komentarz));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(zalogowany);
        when(userRepository.findByEmail("user@x.pl")).thenReturn(Optional.of(user));

        assertThrows(SecurityException.class, () -> komentarzService.usunKomentarz(1L));
    }

    @Test
    void aktualizujKomentarz_powinienZapisacNowaTresc() {
        Komentarz komentarz = new Komentarz();
        komentarz.setTresc("Stara");
        Uzytkownik autor = new Uzytkownik(); autor.setUzytkownikID(1);
        komentarz.setUzytkownik(autor);

        when(komentarzRepository.findById(1L)).thenReturn(Optional.of(komentarz));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(autor);

        komentarzService.aktualizujKomentarz(1L, "Nowa treść");

        verify(komentarzRepository).save(komentarz);
        assertEquals("Nowa treść", komentarz.getTresc());
    }

    @Test
    void toTransData_powinienZmapowacKomentarz() {
        Komentarz komentarz = new Komentarz();
        Post post = new Post(); post.setPostID(10);
        Uzytkownik autor = new Uzytkownik();

        komentarz.setKomentarzID(1);
        komentarz.setTresc("treść");
        komentarz.setZdjecie(null);
        komentarz.setPost(post);
        komentarz.setUzytkownik(autor);

        when(uzytkownikService.toTransData(autor)).thenReturn(new demo.uzytkownik.UzytkownikTransData());
        when(reakcjaService.toTransData(any(List.class))).thenReturn(null);

        KomentarzTransData dto = komentarzService.toTransData(komentarz);
        assertNotNull(dto);
        assertEquals(1L, dto.getKomentarzID());
        assertEquals("treść", dto.getTresc());
        assertNull(dto.getZdjecie());
        assertEquals(10L, dto.getPostID());
    }
}
