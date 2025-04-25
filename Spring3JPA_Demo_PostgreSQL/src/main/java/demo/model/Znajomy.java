package demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "znajomy")

public class Znajomy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int znajomyID;

    //private int uzytkownikID;

    //private int uzytkownik2ID;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private  Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "uzytkownik2ID")
    private  Uzytkownik uzytkownik2;

    public Znajomy(){}

    public Znajomy(Uzytkownik uzytkownik, Uzytkownik uzytkownik2){
        this.uzytkownik = uzytkownik;
        this.uzytkownik2 = uzytkownik2;
    }

    public int getZnajomyID(){return znajomyID;}
    public void setZnajomyID(int znajomyID){this.znajomyID = znajomyID;}

    public Uzytkownik getUzytkownikID(){return uzytkownik;}
    public void setUzytkownikID(Uzytkownik uzytkownik){this.uzytkownik = uzytkownik;}

    public Uzytkownik getUzytkownik2ID(){return uzytkownik2;}
    public void setUzytkownik2ID(Uzytkownik uzytkownik2){this.uzytkownik2 = uzytkownik2;}


}
