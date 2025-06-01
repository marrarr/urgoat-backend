package demo.uzytkownik;

import demo.log.LogOperacja;
import demo.log.URgoatLogger;
import demo.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UzytkownikServiceTest {
    private UzytkownikRepository uzytkownikRepository;
    private UserRepository userRepository;
    private UzytkownikService uzytkownikService;

    @BeforeEach
    void setUp() {
        uzytkownikRepository = mock(UzytkownikRepository.class);
        userRepository = mock(UserRepository.class);
        uzytkownikService = new UzytkownikService(uzytkownikRepository, userRepository);
    }

    @Test
    void getZalogowanyUzytkownik_powinnoZwrocicZalogowanegoUzytkownika() {
        // mock SecurityContextHolder
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("janek");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);

        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Uzytkownik uzytkownik = new Uzytkownik();
            uzytkownik.setPseudonim("janek");

            when(uzytkownikRepository.findByUserAccount_Username("janek")).thenReturn(Optional.of(uzytkownik));

            // Test
            Uzytkownik zalogowanyUzytkownik = uzytkownikService.getZalogowanyUzytkownik();
            assertNotNull(zalogowanyUzytkownik);
            assertEquals("janek", zalogowanyUzytkownik.getPseudonim());
        }
    }

    @Test
    void toTransData_powinnoPoprawnieZmappowac_ZdjecieNull() {
        //
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setUzytkownikID(1);
        uzytkownik.setZdjecie(null);
        uzytkownik.setPseudonim("coolFox99");

        //
        UzytkownikTransData result = uzytkownikService.toTransDataBezImieniaNazwiska(uzytkownik);

        //
        assertEquals(1, result.getUzytkownikID());
        assertNull(result.getZdjecie());
        assertEquals("coolFox99", result.getPseudonim());
    }

    @Test
    void toTransData_powinnoZmappowac_ZdjecieNieJestNull() {
        //
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setUzytkownikID(1);
        uzytkownik.setZdjecie(new byte[]{1, 2, 3});
        uzytkownik.setPseudonim("coolFox99");

        //
        UzytkownikTransData result = uzytkownikService.toTransDataBezImieniaNazwiska(uzytkownik);

        //
        assertEquals(1, result.getUzytkownikID());
        assertArrayEquals(new byte[]{1, 2, 3}, result.getZdjecie());
        assertEquals("coolFox99", result.getPseudonim());
    }

    @Test
    void aktualizujDate_powinnoRzucicWyjatek_gdyImieLubNazwiskoJestPuste() {
        String imie = "     ";
        String nazwisko = "";

        when(uzytkownikRepository.findById(13L)).thenReturn(Optional.of(new Uzytkownik()));


        assertThrows(IllegalArgumentException.class,
                () -> uzytkownikService.aktualizujDane(13L, imie, nazwisko, null, null));
    }

    @Test
    void aktualizujDane_powinnoAktualizowacWszystkieDane() throws IOException {
        // Mock SecurityContext dla getZalogowanyUzytkownik()
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);

        try (MockedStatic<SecurityContextHolder> mockedSecurity = mockStatic(SecurityContextHolder.class);
             MockedStatic<URgoatLogger> mockedLogger = mockStatic(URgoatLogger.class)) {

            mockedSecurity.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Mock uzytkownikRepository dla getZalogowanyUzytkownik()
            Uzytkownik zalogowany = new Uzytkownik();
            zalogowany.setPseudonim("testuser");
            when(uzytkownikRepository.findByUserAccount_Username("testuser")).thenReturn(Optional.of(zalogowany));

            // Testowany przypadek
            Uzytkownik uzytkownik = new Uzytkownik();
            when(uzytkownikRepository.findById(1L)).thenReturn(Optional.of(uzytkownik));

            MultipartFile zdjecie = mock(MultipartFile.class);
            when(zdjecie.isEmpty()).thenReturn(false);
            when(zdjecie.getBytes()).thenReturn(new byte[]{1, 2, 3});

            uzytkownikService.aktualizujDane(1L, "NoweImie", "NoweNazwisko", "nowyPseudonim", zdjecie);

            assertEquals("NoweImie", uzytkownik.getImie());
            assertEquals("NoweNazwisko", uzytkownik.getNazwisko());
            assertEquals("nowyPseudonim", uzytkownik.getPseudonim());
            assertArrayEquals(new byte[]{1, 2, 3}, uzytkownik.getZdjecie());

            mockedLogger.verify(() -> URgoatLogger.uzytkownikInfo(
                    eq("Zaktualizowano dane użytkownika id=" + uzytkownik.getUzytkownikID()),
                    eq("testuser"),
                    eq(LogOperacja.AKTUALIZOWANIE)));
        }
    }

    @Test
    void aktualizujDane_powinnoUzycDomyślnegoAvataraGdyZdjecieJestPuste() throws IOException {
        // Mock SecurityContext
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);

        try (MockedStatic<SecurityContextHolder> mockedSecurity = mockStatic(SecurityContextHolder.class)) {
            mockedSecurity.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Mock dla getZalogowanyUzytkownik()
            Uzytkownik zalogowany = new Uzytkownik();
            zalogowany.setPseudonim("testuser");
            when(uzytkownikRepository.findByUserAccount_Username("testuser")).thenReturn(Optional.of(zalogowany));

            Uzytkownik uzytkownik = new Uzytkownik();
            when(uzytkownikRepository.findById(1L)).thenReturn(Optional.of(uzytkownik));

            // Wywołanie metody testowanej z null jako zdjęcie
            uzytkownikService.aktualizujDane(1L, "Jan", "Kowalski", "janek", null);

            // Sprawdzenie tylko czy zdjęcie nie jest null
            assertNotNull(uzytkownik.getZdjecie());
            assertTrue(uzytkownik.getZdjecie().length > 0);
        }
    }
}