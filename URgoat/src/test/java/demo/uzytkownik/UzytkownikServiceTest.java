package demo.uzytkownik;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UzytkownikServiceTest {
    private UzytkownikRepository uzytkownikRepository;
    private UzytkownikService uzytkownikService;

    @BeforeEach
    void setUp() {
        uzytkownikRepository = mock(UzytkownikRepository.class);
        uzytkownikService = new UzytkownikService(uzytkownikRepository);
    }

    @Test
    void getZalogowanyUzytkownik() {
        // Mockujemy SecurityContextHolder
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("janek");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);

        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Mock repo
            Uzytkownik fakeUser = new Uzytkownik();
            fakeUser.setPseudonim("janek");

            when(uzytkownikRepository.findFirstByPseudonim("janek")).thenReturn(fakeUser);

            // Test
            Uzytkownik result = uzytkownikService.getZalogowanyUzytkownik();
            assertNotNull(result);
            assertEquals("janek", result.getPseudonim());
        }
    }

    @Test
    void toTransDataZdjecieNull() {
        // given
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setUzytkownikID(1);
        uzytkownik.setZdjecie(null);
        uzytkownik.setPseudonim("coolFox99");

        // when
        UzytkownikTransData result = uzytkownikService.toTransData(uzytkownik);

        // then
        assertEquals(1, result.getUzytkownikID());
        assertNull(result.getZdjecie());
        assertEquals("coolFox99", result.getPseudonim());
    }

    @Test
    void toTransDataZdjecie() {
        // given
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setUzytkownikID(1);
        uzytkownik.setZdjecie(new byte[]{1, 2, 3});
        uzytkownik.setPseudonim("coolFox99");

        // when
        UzytkownikTransData result = uzytkownikService.toTransData(uzytkownik);

        // then
        assertEquals(1, result.getUzytkownikID());
        assertArrayEquals(new byte[]{1, 2, 3}, result.getZdjecie());
        assertEquals("coolFox99", result.getPseudonim());
    }
}