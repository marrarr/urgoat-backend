package demo.komentarz;

import demo.log.URgoatLogger;
import demo.log.LogOperacja;
import demo.post.Post;
import demo.post.PostRepository;
import demo.reakcja.ReakcjaService;
import demo.reakcja.ReakcjaTransData;
import demo.security.model.User;
import demo.security.repository.UserRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.uzytkownik.UzytkownikTransData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KomentarzServiceTest {

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

    @Mock
    private URgoatLogger logger;

    @InjectMocks
    private KomentarzService komentarzService;

    private Uzytkownik uzytkownik;
    private Post post;
    private Komentarz komentarz;
    private User adminUser;
    private User normalUser;

    @BeforeEach
    void setUp() {
        uzytkownik = new Uzytkownik();
        uzytkownik.setUzytkownikID(1);
        uzytkownik.setPseudonim("testUser");
        uzytkownik.setEmail("test@example.com");

        post = new Post();
        post.setPostID(1);

        komentarz = new Komentarz();
        komentarz.setKomentarzID(1);
        komentarz.setTresc("Testowy komentarz");
        komentarz.setUzytkownik(uzytkownik);
        komentarz.setPost(post);

        adminUser = new User();
        adminUser.setRole("ROLE_ADMIN");
        adminUser.setEmail("admin@example.com");

        normalUser = new User();
        normalUser.setRole("ROLE_USER");
        normalUser.setEmail("test@example.com");
    }

    @Test
    void powinien_konwertowac_komentarz_na_obiekt_TransData() {
        // Given
        ReakcjaTransData reakcjaTransData = new ReakcjaTransData();
        UzytkownikTransData uzytkownikTransData = new UzytkownikTransData();

        komentarz.setReakcje(Collections.emptyList());

        // Mockowanie z dowolną listą (w tym pustą)
        when(reakcjaService.toTransData(anyList())).thenReturn(List.of(reakcjaTransData));
        when(uzytkownikService.toTransDataBezImieniaNazwiska(any(Uzytkownik.class))).thenReturn(uzytkownikTransData);

        // When
        KomentarzTransData result = komentarzService.toTransData(komentarz);

        // Then
        assertNotNull(result);
        assertEquals(komentarz.getKomentarzID(), result.getKomentarzID());
        assertEquals(komentarz.getTresc(), result.getTresc());
        assertEquals(komentarz.getPost().getPostID(), result.getPostID());
        assertEquals(1, result.getReakcje().size());
    }

    @Test
    void powinien_konwertowac_liste_komentarzy_na_liste_TransData() {
        // Given
        ReakcjaTransData reakcjaTransData = new ReakcjaTransData();
        UzytkownikTransData uzytkownikTransData = new UzytkownikTransData();

        // Upewniamy się, że komentarz ma pustą listę reakcji (nie null)
        komentarz.setReakcje(Collections.emptyList());

        // Mockowanie metod
        when(reakcjaService.toTransData(anyList())).thenReturn(List.of(reakcjaTransData));
        when(uzytkownikService.toTransDataBezImieniaNazwiska(any(Uzytkownik.class))).thenReturn(uzytkownikTransData);

        // When
        List<KomentarzTransData> result = komentarzService.toTransData(List.of(komentarz));

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(komentarz.getKomentarzID(), result.get(0).getKomentarzID());
        assertEquals(komentarz.getTresc(), result.get(0).getTresc());
        assertEquals(komentarz.getPost().getPostID(), result.get(0).getPostID());
    }

    @Test
    void powinien_dodac_nowy_komentarz() {
        // Given
        when(uzytkownikRepository.findById(anyLong())).thenReturn(Optional.of(uzytkownik));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(komentarzRepository.save(any(Komentarz.class))).thenReturn(komentarz);

        // Mockowanie zalogowanego użytkownika dla loggera
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(uzytkownik);

        // When
        komentarzService.dodajKomentarz(1L, 1L, "Nowy komentarz");

        // Then
        verify(komentarzRepository, times(1)).save(any(Komentarz.class));
        verify(uzytkownikService, times(1)).getZalogowanyUzytkownik();
    }

    @Test
    void powinien_rzucic_wyjatek_gdy_uzytkownik_nie_istnieje_przy_dodawaniu_komentarza() {
        // Given
        when(uzytkownikRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () ->
                komentarzService.dodajKomentarz(1L, 1L, "Nowy komentarz"));
    }

    @Test
    void powinien_rzucic_wyjatek_gdy_post_nie_istnieje_przy_dodawaniu_komentarza() {
        // Given
        when(uzytkownikRepository.findById(anyLong())).thenReturn(Optional.of(uzytkownik));
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () ->
                komentarzService.dodajKomentarz(1L, 1L, "Nowy komentarz"));
    }

    @Test
    void powinien_usunac_komentarz_gdy_uzytkownik_jest_autorem() {
        // Given
        when(komentarzRepository.findById(anyLong())).thenReturn(Optional.of(komentarz));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(uzytkownik);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(normalUser));

        // When
        komentarzService.usunKomentarz(1L);

        // Then
        verify(komentarzRepository, times(1)).delete(komentarz);
    }

    @Test
    void powinien_usunac_komentarz_gdy_uzytkownik_jest_administratorem() {
        // Given
        when(komentarzRepository.findById(anyLong())).thenReturn(Optional.of(komentarz));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(uzytkownik);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(adminUser));

        // When
        komentarzService.usunKomentarz(1L);

        // Then
        verify(komentarzRepository, times(1)).delete(komentarz);
    }

    @Test
    void powinien_rzucic_wyjatek_gdy_uzytkownik_nie_moze_usunac_komentarza() {
        // Given
        Uzytkownik innyUzytkownik = new Uzytkownik();
        innyUzytkownik.setUzytkownikID(2);
        innyUzytkownik.setEmail("inny@example.com"); // Dodajemy email

        // Mockowanie
        when(komentarzRepository.findById(anyLong())).thenReturn(Optional.of(komentarz));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(innyUzytkownik);

        // Używamy anyString() zamiast konkretnej wartości
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(normalUser));

        // When & Then
        assertThrows(AccessDeniedException.class, () ->
                komentarzService.usunKomentarz(1L));
    }

    @Test
    void powinien_aktualizowac_komentarz_gdy_uzytkownik_jest_autorem() {
        // Given
        String nowaTresc = "Zaktualizowany komentarz";
        when(komentarzRepository.findById(anyLong())).thenReturn(Optional.of(komentarz));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(uzytkownik);

        // When
        komentarzService.aktualizujKomentarz(1L, nowaTresc);

        // Then
        assertEquals(nowaTresc, komentarz.getTresc());
        verify(komentarzRepository, times(1)).save(komentarz);
    }

    @Test
    void powinien_rzucic_wyjatek_gdy_tresc_jest_pusta_przy_aktualizacji() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                komentarzService.aktualizujKomentarz(1L, ""));
    }

    @Test
    void powinien_rzucic_wyjatek_gdy_uzytkownik_nie_moze_aktualizowac_komentarza() {
        // Given
        Uzytkownik innyUzytkownik = new Uzytkownik();
        innyUzytkownik.setUzytkownikID(2);

        when(komentarzRepository.findById(anyLong())).thenReturn(Optional.of(komentarz));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(innyUzytkownik);

        // When & Then
        assertThrows(AccessDeniedException.class, () ->
                komentarzService.aktualizujKomentarz(1L, "Nowa treść"));
    }
}