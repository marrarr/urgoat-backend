package demo.reakcja;

import demo.uzytkownik.Uzytkownik;
import demo.uzytkownik.UzytkownikService;
import demo.uzytkownik.UzytkownikTransData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReakcjaService {
    @Autowired
    private UzytkownikService uzytkownikService;

    public ReakcjaTransData toTransData(Reakcja reakcja, Uzytkownik uzytkownik) {
        Integer postID = reakcja.getPostID() != null ? reakcja.getPostID().getPostID() : null;
        Integer komentarzID = reakcja.getKomentarzID() != null ? reakcja.getKomentarzID().getKomentarzID() : null;
        UzytkownikTransData uzytkownikKomentarzTransData = uzytkownikService.toTransData(uzytkownik);

        return new ReakcjaTransData(
                postID,
                komentarzID,
                reakcja.getReakcja(),
                uzytkownikKomentarzTransData
        );
    }

    public List<ReakcjaTransData> toTransData(List<Reakcja> reakcje, Uzytkownik uzytkownik) {
        return reakcje
                .stream()
                .map(reakcja -> toTransData(reakcja, uzytkownik))
                .toList();
    }
}