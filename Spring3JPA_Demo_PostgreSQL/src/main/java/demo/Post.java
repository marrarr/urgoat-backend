package demo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "post")
class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;

    //private int uzytkownikID;

    @Column(columnDefinition = "TEXT")
    private String tresc;

    @Lob
    private byte[] zdjecie;

    public Post(){}

    public Post(int postID, Uzytkownik uzytkownikID, String tresc, byte[] zdjecie){
        this.postID = postID;
        this.uzytkownik = uzytkownikID;
        this.tresc = tresc;
        this.zdjecie = zdjecie;
    }

    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    @OneToMany(mappedBy = "post")
    private List<Komentarz> komentarze;

    @OneToMany(mappedBy = "post")
    private List<Reakcja> reakcje;

    public int getPostID(){return postID;}
    public void setPostID(int postID){this.postID = postID;}

    public Uzytkownik getUzytkownikID(){return uzytkownik;}
    public void setUzytkownikID(Uzytkownik uzytkownik){this.uzytkownik = uzytkownik;}

    public String getTresc(){return tresc;}
    public void setTresc(String tresc){this.tresc = tresc;}

    public byte[] getZdjecie() {return zdjecie;}
    public void setZdjecie(byte[] zdjecie) {this.zdjecie = zdjecie;}



}
