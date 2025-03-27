package demo;

import jakarta.persistence.*;

@Entity
@Table(name = "reakcja")

class Reakcja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reakcjaID;

    //private int uzytkownikID;

    //private int komentarzID;

    //private int postID;

    private int reakcja;


    @ManyToOne
    @JoinColumn(name = "uzytkownikID")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "postID", nullable = true)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "komentarzID", nullable = true)
    private Komentarz komentarz;

    public Reakcja(){}

//    public Reakcja(int uzytkownikID, int komentarzID, int postID, int reakcja){
//        this.uzytkownikID = uzytkownikID;
//        this.komentarzID = komentarzID;
//        this.postID = postID;
//        this.reakcja = reakcja;
//    }

    public Reakcja(Uzytkownik uzytkownik, Komentarz komentarz, Post post, int reakcja){
        this.uzytkownik = uzytkownik;
        this.komentarz = komentarz;
        this.post = post;
        this.reakcja = reakcja;
    }

    public Uzytkownik getUzytkownikID(){return uzytkownik;}
    public void setUzytkownikID(Uzytkownik uzytkownik){this.uzytkownik = uzytkownik;}

    public Komentarz getKomentarzID(){return komentarz;}
    public void setKomentarzID(Komentarz komentarz){this.komentarz = komentarz;}

    public Post getPostID(){return post;}
    public void setPostID(Post post){this.post = post;}

    public int getReakcja(){return reakcja;}
    public void setReakcja(int reakcja){this.reakcja = reakcja;}
}
