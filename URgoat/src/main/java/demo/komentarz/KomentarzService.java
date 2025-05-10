package demo.komentarz;

import demo.reakcja.ReakcjaService;
import demo.uzytkownik.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KomentarzService {
    @Autowired
    private UzytkownikService uzytkownikService;
    @Autowired
    private ReakcjaService reakcjaService;

    public KomentarzTransData toTransData(Komentarz komentarz) {
        return new KomentarzTransData(
                komentarz.getKomentarzID(),
                komentarz.getTresc(),
                komentarz.getZdjecie(),
                komentarz.getPostID().getPostID(),
                uzytkownikService.toTransData(komentarz.getUzytkownikID()),
                reakcjaService.toTransData(komentarz.getReakcje(), komentarz.getUzytkownikID())
        );
    }

    public List<KomentarzTransData> toTransData(List<Komentarz> komentarze) {
        return komentarze
                .stream()
                .map(komentarz -> toTransData(komentarz))
                .toList();
    }
}