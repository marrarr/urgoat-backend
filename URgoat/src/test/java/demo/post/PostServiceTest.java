package demo.post;

import demo.komentarz.KomentarzService;
import demo.komentarz.KomentarzTransData;
import demo.log.LogOperacja;
import demo.log.URgoatLogger;
import demo.reakcja.ReakcjaService;
import demo.reakcja.ReakcjaTransData;
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
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UzytkownikRepository uzytkownikRepository;

    @Mock
    private UzytkownikService uzytkownikService;

    @Mock
    private KomentarzService komentarzService;

    @Mock
    private ReakcjaService reakcjaService;

    @Mock
    private URgoatLogger urgoatLogger;

    @InjectMocks
    private PostService postService;

    private Uzytkownik testUser;
    private Post testPost;
    private PostTransData testPostTransData;

    @BeforeEach
    void setUp() {
        testUser = new Uzytkownik();
        testUser.setUzytkownikID(1);
        testUser.setPseudonim("testUser");

        testPost = new Post();
        testPost.setPostID(1);
        testPost.setTresc("Test post content");
        testPost.setUzytkownik(testUser);

        UzytkownikTransData userTransData = new UzytkownikTransData(1, "testUser");
        testPostTransData = new PostTransData(
                1L,
                "Test post content",
                userTransData,
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Test
    void getPostyZKomentarzamiOrazReakcjami_ShouldReturnListOfPostTransData() {
        // Arrange
        List<Post> posts = List.of(testPost);
        when(postRepository.findAll(any(Sort.class))).thenReturn(posts);
        when(uzytkownikService.toTransDataBezImieniaNazwiska(any(Uzytkownik.class)))
                .thenReturn(new UzytkownikTransData(1L, "testUser"));
        when(komentarzService.toTransData(anyList())).thenReturn(new ArrayList<>());
        when(reakcjaService.toTransData(anyList())).thenReturn(new ArrayList<>());

        // Act
        List<PostTransData> result = postService.getPostyZKomentarzamiOrazReakcjami();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPost.getPostID(), result.get(0).getPostID());
        assertEquals(testPost.getTresc(), result.get(0).getTresc());

        verify(postRepository).findAll(Sort.by(Sort.Direction.DESC, "postID"));
        verify(uzytkownikService).toTransDataBezImieniaNazwiska(testUser);
        verify(komentarzService).toTransData(testPost.getKomentarze());
        verify(reakcjaService).toTransData(testPost.getReakcje());
    }

    @Test
    void getPosty_ShouldReturnListOfPostTransDataWithNullFields() {
        // Arrange
        List<Post> posts = List.of(testPost);
        when(postRepository.findAll()).thenReturn(posts);

        // Act
        List<PostTransData> result = postService.getPosty();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPost.getPostID(), result.get(0).getPostID());
        assertEquals(testPost.getTresc(), result.get(0).getTresc());
        assertNull(result.get(0).getUzytkownik());
        assertNull(result.get(0).getKomentarze());
        assertNull(result.get(0).getReakcje());

        verify(postRepository).findAll();
    }

    @Test
    void dodajPost_ShouldSavePostAndLogOperation() {
        // Arrange
        String postContent = "New post content";
        when(uzytkownikRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        // Act
        postService.dodajPost(1L, postContent);

        // Assert
        verify(uzytkownikRepository).findById(1L);
        verify(postRepository).save(any(Post.class));
        verify(urgoatLogger).uzytkownikInfo(
                startsWith("Dodano post id="),
                eq("testUser"),
                eq(LogOperacja.DODAWANIE)
        );
    }

    @Test
    void dodajPost_ShouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(uzytkownikRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> postService.dodajPost(1L, "content"));
        verify(uzytkownikRepository).findById(1L);
        verify(postRepository, never()).save(any());
    }

    @Test
    void usunPost_ShouldDeletePostAndLogOperation() {
        // Arrange
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(testPost));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(testUser);

        // Act
        postService.usunPost(1L);

        // Assert
        verify(postRepository).findById(1L);
        verify(postRepository).delete(testPost);
        verify(urgoatLogger).uzytkownikInfo(
                eq("Usunięto post id=1"),
                eq("testUser"),
                eq(LogOperacja.USUWANIE)
        );
    }

    @Test
    void usunPost_ShouldThrowExceptionWhenPostNotFound() {
        // Arrange
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> postService.usunPost(1L));
        verify(postRepository).findById(1L);
        verify(postRepository, never()).delete(any());
    }

    @Test
    void zaktualizujPost_ShouldUpdatePostAndLogOperation() {
        // Arrange
        String updatedContent = "Updated content";
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(testPost));
        when(uzytkownikService.getZalogowanyUzytkownik()).thenReturn(testUser);

        // Act
        postService.zaktualizujPost(1L, updatedContent);

        // Assert
        verify(postRepository).findById(1L);
        assertEquals(updatedContent, testPost.getTresc());
        verify(postRepository).save(testPost);
        verify(urgoatLogger).uzytkownikInfo(
                startsWith("Zaktualizowano post id=1, dlugosc=" + updatedContent.length()),
                eq("testUser"),
                eq(LogOperacja.AKTUALIZOWANIE)
        );
    }

    @Test
    void zaktualizujPost_ShouldThrowExceptionWhenPostNotFound() {
        // Arrange
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> postService.zaktualizujPost(1L, "content"));
        verify(postRepository).findById(1L);
        verify(postRepository, never()).save(any());
    }
}