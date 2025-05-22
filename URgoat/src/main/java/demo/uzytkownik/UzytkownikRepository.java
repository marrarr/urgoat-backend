package demo.uzytkownik;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long>{
    //Wyszukaj użytkownika po imieniu
    List<Uzytkownik> findByImie(String imie);

    //Wyszukaj użytkownika po nazwisku
    List<Uzytkownik> findByNazwisko(String nazwisko);

    //Wyszukaj użytkownika po imieniu i nazwisku
    List<Uzytkownik> findByImieAndNazwisko(String imie,String nazwisko);

    //Wyszukiwanie po pseudonimie
    List<Uzytkownik> findByPseudonim(String pseudonim);

    Uzytkownik findFirstByPseudonim(String pseudonim);

    Uzytkownik findFirstByUzytkownikID(Long uzytkownikIDLink);

    @Query("SELECT u FROM Uzytkownik u WHERE u.uzytkownikID <> :id")
    List<Uzytkownik> findAllExceptById(@Param("id") int id);

    Optional<Uzytkownik> findByUserAccount_Username(String username);

}
