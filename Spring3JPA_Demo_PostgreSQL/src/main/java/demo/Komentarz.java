package demo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "komentarz")

class Komentarz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int komentarzID;

    //private int postID;

    //private int uzytkownikID;

    @Column(columnDefinition = "TEXT")
    private String tresc;

    @Lob
    private byte[] zdjecie;

    public Komentarz(){}

    public Komentarz(Post postID, Uzytkownik uzytkownikID, String tresc, byte[] zdjecie){
        this.post = postID;
        this.uzytkownik = uzytkownikID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
    }

    @ManyToOne
    @JoinColumn(name = "postID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    @OneToMany(mappedBy = "komentarz")
    private List<Reakcja> reakcje;

    public int getKomentarzID(){return komentarzID;}
    public void setKomentarzID(int komentarzID){this.komentarzID = komentarzID;}

    public Post getPostID(){return post;}
    public void setPostID(Post postID){this.post = postID;}

    public Uzytkownik getUzytkownikID(){return uzytkownik;}
    public void setUzytkownikID(Uzytkownik uzytkownikID){this.uzytkownik = uzytkownikID;}

    public String getTresc(){return tresc;}
    public void setTresc(String tresc){this.tresc = tresc;}

    public byte[] getZdjecie() {return zdjecie;}
    public void setZdjecie(byte[] zdjecie) {this.zdjecie = zdjecie;}




}
