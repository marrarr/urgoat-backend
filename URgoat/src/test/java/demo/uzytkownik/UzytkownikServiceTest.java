package demo.uzytkownik;

import demo.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

            when(uzytkownikRepository.findFirstByPseudonim("janek")).thenReturn(uzytkownik);

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
                () -> uzytkownikService.aktualizujDane(13L, imie, nazwisko,  null));
    }
}