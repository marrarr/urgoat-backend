package demo.reakcja;

import demo.komentarz.Komentarz;
import demo.komentarz.KomentarzRepository;
import demo.post.Post;
import demo.post.PostRepository;
import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikRepository;
import demo.uzytkownik.UzytkownikService;
import demo.uzytkownik.UzytkownikTransData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    void toTransData_PowinnoPoprawnieZmappowacReakcjeDlaPosta_KiedyKomentarzJestNull() {
        Post post = new Post();
        post.setPostID(1);

        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setUzytkownikID(1);

        Reakcja reakcja = new Reakcja();
        reakcja.setReakcjaID(1);
        reakcja.setReakcja(1);
        reakcja.setPost(post);
        reakcja.setKomentarz(null);
        reakcja.setUzytkownik(uzytkownik);

        //
        UzytkownikTransData uzytkownikTransData = new UzytkownikTransData();
        uzytkownikTransData.setUzytkownikID(uzytkownik.getUzytkownikID());
        when(uzytkownikService.toTransDataBezImieniaNazwiska(uzytkownik)).thenReturn(uzytkownikTransData);

        //
        ReakcjaTransData reakcjaTransData = reakcjaService.toTransData(reakcja);
        assertEquals(1, reakcjaTransData.getReakcja());
        assertEquals(1, reakcjaTransData.getPostID());
        assertNull(reakcjaTransData.getKomentarzID());
        assertEquals(uzytkownikTransData, reakcjaTransData.getUzytkownik());
    }

    @Test
    void toTransData_PowinnoPoprawnieZmappowacReakcjeDlaKomentarza_KiedyPostJestNull() {
        Komentarz komentarz = new Komentarz();
        komentarz.setKomentarzID(1);

        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setUzytkownikID(1);

        Reakcja reakcja = new Reakcja();
        reakcja.setReakcjaID(1);
        reakcja.setReakcja(1);
        reakcja.setPost(null);
        reakcja.setKomentarz(komentarz);
        reakcja.setUzytkownik(uzytkownik);

        //
        UzytkownikTransData uzytkownikTransData = new UzytkownikTransData();
        uzytkownikTransData.setUzytkownikID(uzytkownik.getUzytkownikID());
        when(uzytkownikService.toTransDataBezImieniaNazwiska(uzytkownik)).thenReturn(uzytkownikTransData);

        //
        ReakcjaTransData reakcjaTransData = reakcjaService.toTransData(reakcja);
        assertEquals(1, reakcjaTransData.getReakcja());
        assertEquals(1, reakcjaTransData.getKomentarzID());
        assertNull(reakcjaTransData.getPostID());
        assertEquals(uzytkownikTransData, reakcjaTransData.getUzytkownik());
    }

    @Test
    void testToTransData_powinnoPoprawnieZmapowacListe() {
        // przygotowanie
        Post post = new Post();
        post.setPostID(1);

        Uzytkownik uzytkownik1 = new Uzytkownik();
        uzytkownik1.setUzytkownikID(1);
        Uzytkownik uzytkownik2 = new Uzytkownik();
        uzytkownik2.setUzytkownikID(2);

        Reakcja reakcja1 = new Reakcja();
        reakcja1.setReakcja(2);
        reakcja1.setPost(post);
        reakcja1.setUzytkownik(uzytkownik1);
        Reakcja reakcja2 = new Reakcja();
        reakcja2.setReakcja(3);
        reakcja2.setPost(post);
        reakcja2.setUzytkownik(uzytkownik2);

        List<Reakcja> reakcje = new ArrayList<>();
        reakcje.add(reakcja1);
        reakcje.add(reakcja2);

        UzytkownikTransData uzytkownikTransData1 = new UzytkownikTransData();
        uzytkownikTransData1.setUzytkownikID(uzytkownik1.getUzytkownikID());
        UzytkownikTransData uzytkownikTransData2 = new UzytkownikTransData();
        uzytkownikTransData2.setUzytkownikID(uzytkownik2.getUzytkownikID());

        // zachowanie mocka
        when(uzytkownikService.toTransDataBezImieniaNazwiska(uzytkownik1)).thenReturn(uzytkownikTransData1);
        when(uzytkownikService.toTransDataBezImieniaNazwiska(uzytkownik2)).thenReturn(uzytkownikTransData2);

        // testowana metoda
        List<ReakcjaTransData> reakcjeTransData = reakcjaService.toTransData(reakcje);

        // sprawdzenie
        assertEquals(2, reakcjeTransData.size());

        assertEquals(2, reakcjeTransData.get(0).getReakcja());
        assertEquals(3, reakcjeTransData.get(1).getReakcja());

        assertEquals(1, reakcjeTransData.get(0).getPostID());
        assertEquals(1, reakcjeTransData.get(1).getPostID());

        assertNull(reakcjeTransData.get(0).getKomentarzID());
        assertNull(reakcjeTransData.get(1).getKomentarzID());

        assertEquals(uzytkownikTransData1, reakcjeTransData.get(0).getUzytkownik());
        assertEquals(uzytkownikTransData2, reakcjeTransData.get(1).getUzytkownik());
    }

    @Test
    void dodajReakcje_powinnoDodacReakcjeDoPosta() {
        long userId = 1L;
        Long postId = 10L;
        Long komentarzId = null;
        int kodReakcji = 2;

        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setUzytkownikID((int) userId);

        Post post = new Post();
        post.setPostID(Math.toIntExact(postId));

        when(uzytkownikRepository.findById(userId)).thenReturn(Optional.of(uzytkownik));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // wywołanie testowanej metody
        reakcjaService.dodajReakcje(userId, postId, komentarzId, kodReakcji);

        // sprawdzenie czy save został wywołany z reakcją zawierającą post i użytkownika
        verify(reakcjaRepository).save(argThat(reakcja ->
                reakcja.getUzytkownik().equals(uzytkownik) &&
                        reakcja.getPost().equals(post) &&
                        reakcja.getReakcja() == kodReakcji &&
                        reakcja.getKomentarz() == null
        ));
    }

    @Test
    public void dodajReakcje_powinnoWyrzucicWyjatek_kiedyPostIdOrazKomentarzIdSaNull() {
        when(uzytkownikRepository.findById(1L)).thenReturn(Optional.of(new Uzytkownik()));

        assertThrows(IllegalArgumentException.class,
                () -> reakcjaService.dodajReakcje(1L, null, null, 1));
    }

    @Test
    public void dodajReakcje_powinnoWyrzucicWyjatek_kiedyPostIdOrazKomentarzIdNieSaNull() {
        when(uzytkownikRepository.findById(7L)).thenReturn(Optional.of(new Uzytkownik()));

        assertThrows(IllegalArgumentException.class,
                () -> reakcjaService.dodajReakcje(7L, 3L, 1L, 2));
    }

    @Test
    public void usunReakcje_powinnoUsunacReakcje() {
        long reakcjaID = 1L;

        Uzytkownik uzytkownikZalogowany = new Uzytkownik();
        uzytkownikZalogowany.setUzytkownikID(10);

        Uzytkownik autorReakcji = new Uzytkownik();
        autorReakcji.setUzytkownikID(10);

        Reakcja reakcja = new Reakcja();
        reakcja.setReakcjaID((int) reakcjaID);
        reakcja.setUzytkownik(autorReakcji);

        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(uzytkownikZalogowany);
        when(reakcjaRepository.findById(reakcjaID)).thenReturn(Optional.of(reakcja));

        reakcjaService.usunReakcje(reakcjaID);

        verify(reakcjaRepository).delete(reakcja);
    }

    @Test
    void usunReakcje_powinnoRzucicAccessDeniedExceptionKiedyInnyUzytkownik() {
        long reakcjaID = 2L;

        Uzytkownik uzytkownikZalogowany = new Uzytkownik();
        uzytkownikZalogowany.setUzytkownikID(10);

        Uzytkownik innyUzytkownik = new Uzytkownik();
        innyUzytkownik.setUzytkownikID(20);

        Reakcja reakcja = new Reakcja();
        reakcja.setReakcjaID((int) reakcjaID);
        reakcja.setUzytkownik(innyUzytkownik);

        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(uzytkownikZalogowany);
        when(reakcjaRepository.findById(reakcjaID)).thenReturn(Optional.of(reakcja));

        AccessDeniedException thrown = assertThrows(AccessDeniedException.class,
                () -> reakcjaService.usunReakcje(reakcjaID));

        assertEquals("Brak uprawnień do usunięcia komentarza", thrown.getMessage());

        verify(reakcjaRepository, never()).delete(any());
    }
}
