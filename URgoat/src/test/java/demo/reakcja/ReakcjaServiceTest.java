package demo.reakcja;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.log.LogOperacja;
import demo.log.URgoatLogger;
import demo.post.Post;
import demo.post.PostRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReakcjaServiceTest {

    @Mock
    private UzytkownikService uzytkownikService;

    @Mock
    private UzytkownikRepository uzytkownikRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private KomentarzRepository komentarzRepository;

    @Mock
    private ReakcjaRepository reakcjaRepository;

    @Mock
    private URgoatLogger logger;

    @InjectMocks
    private ReakcjaService reakcjaService;

    private Uzytkownik testUzytkownik;
    private Post testPost;
    private Komentarz testKomentarz;
    private Reakcja testReakcja;

    @BeforeEach
    void setUp() {
        testUzytkownik = new Uzytkownik();
        testUzytkownik.setUzytkownikID(1);
        testUzytkownik.setPseudonim("testUser");

        testPost = new Post();
        testPost.setPostID(1);

        testKomentarz = new Komentarz();
        testKomentarz.setKomentarzID(1);

        testReakcja = new Reakcja();
        testReakcja.setReakcjaID(1);
        testReakcja.setReakcja(1);
        testReakcja.setUzytkownik(testUzytkownik);
    }

    @Test
    void toTransData_ShouldConvertReakcjaToTransData() {
        // Given
        testReakcja.setPost(testPost);

        // When
        ReakcjaTransData result = reakcjaService.toTransData(testReakcja);

        // Then
        assertEquals(testPost.getPostID(), result.getPostID());
        assertNull(result.getKomentarzID());
        assertEquals(testReakcja.getReakcja(), result.getReakcja());
    }

    @Test
    void dodajReakcjeDoPosta_ShouldAddNewReaction() {
        // Given
        when(uzytkownikRepository.findById(1L)).thenReturn(Optional.of(testUzytkownik));
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));
        when(reakcjaRepository.findByUzytkownik_UzytkownikIDAndPost_PostID(1, 1)).thenReturn(Optional.empty());
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(testUzytkownik);

        // When
        reakcjaService.dodajReakcjeDoPosta(1L, 1L, 1);

        // Then
        verify(reakcjaRepository, times(1)).save(any(Reakcja.class));
    }

    @Test
    void dodajReakcjeDoPosta_PowinienZaktualizowacIstniejacaReakcje() {
        // Given
        when(uzytkownikRepository.findById(1L)).thenReturn(Optional.of(testUzytkownik));
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));
        when(reakcjaRepository.findByUzytkownik_UzytkownikIDAndPost_PostID(1, 1))
                .thenReturn(Optional.of(testReakcja));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(testUzytkownik);
        when(reakcjaRepository.findById(anyLong())).thenReturn(Optional.of(testReakcja));

        // When
        reakcjaService.dodajReakcjeDoPosta(1L, 1L, 2);

        // Then
        verify(reakcjaRepository, times(1)).save(testReakcja);
        assertEquals(2, testReakcja.getReakcja());
    }

    @Test
    void dodajReakcjeDoKomentarza_ShouldAddNewReaction() {
        // Given
        when(uzytkownikRepository.findById(1L)).thenReturn(Optional.of(testUzytkownik));
        when(komentarzRepository.findById(1L)).thenReturn(Optional.of(testKomentarz));
        when(reakcjaRepository.findByUzytkownik_UzytkownikIDAndKomentarz_KomentarzID(1, 1)).thenReturn(Optional.empty());
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(testUzytkownik);

        // When
        reakcjaService.dodajReakcjeDoKomentarza(1L, 1L, 1);

        // Then
        verify(reakcjaRepository, times(1)).save(any(Reakcja.class));
    }

    @Test
    void aktualizujReakcje_ShouldUpdateReactionWhenUserIsOwner() {
        // Given
        when(reakcjaRepository.findById(1L)).thenReturn(Optional.of(testReakcja));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(testUzytkownik);

        // When
        reakcjaService.aktualizujReakcje(1L, 2);

        // Then
        verify(reakcjaRepository, times(1)).save(testReakcja);
        assertEquals(2, testReakcja.getReakcja());
    }

    @Test
    void aktualizujReakcje_ShouldThrowAccessDeniedWhenUserIsNotOwner() {
        // Given
        Uzytkownik innyUzytkownik = new Uzytkownik();
        innyUzytkownik.setUzytkownikID(2);

        when(reakcjaRepository.findById(1L)).thenReturn(Optional.of(testReakcja));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(innyUzytkownik);

        // When & Then
        assertThrows(AccessDeniedException.class, () -> {
            reakcjaService.aktualizujReakcje(1L, 2);
        });
    }

    @Test
    void usunReakcje_ShouldDeleteReactionWhenUserIsOwner() {
        // Given
        when(reakcjaRepository.findById(1L)).thenReturn(Optional.of(testReakcja));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(testUzytkownik);

        // When
        reakcjaService.usunReakcje(1L);

        // Then
        verify(reakcjaRepository, times(1)).delete(testReakcja);
    }

    @Test
    void usunReakcje_ShouldThrowAccessDeniedWhenUserIsNotOwner() {
        // Given
        Uzytkownik innyUzytkownik = new Uzytkownik();
        innyUzytkownik.setUzytkownikID(2);

        when(reakcjaRepository.findById(1L)).thenReturn(Optional.of(testReakcja));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(innyUzytkownik);

        // When & Then
        assertThrows(AccessDeniedException.class, () -> {
            reakcjaService.usunReakcje(1L);
        });
    }

    @Test
    void dodajReakcjeDoPosta_ShouldThrowExceptionWhenPostIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            reakcjaService.dodajReakcjeDoPosta(1L, null, 1);
        });
    }

    @Test
    void dodajReakcjeDoKomentarza_ShouldThrowExceptionWhenKomentarzIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            reakcjaService.dodajReakcjeDoKomentarza(1L, null, 1);
        });
    }

    @Test
    void dodajReakcjeDoPosta_ShouldDoNothingWhenKodReakcjiIsZero() {
        reakcjaService.dodajReakcjeDoPosta(1L, 1L, 0);
        verify(reakcjaRepository, never()).save(any());
    }
}