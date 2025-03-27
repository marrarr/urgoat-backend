package demo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "post")
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;

    //private int uzytkownikID;

    @Column(columnDefinition = "TEXT")
    private String tresc;

    @Lob
    private byte[] zdjecie;

    public Post(){}

    public Post(String tresc, byte[] zdjecie){

        this.tresc = tresc;
        this.zdjecie = zdjecie;
    }



    public int getPostID(){return postID;}
    public void setPostID(int postID){this.postID = postID;}

    public String getTresc(){return tresc;}
    public void setTresc(String tresc){this.tresc = tresc;}

    public byte[] getZdjecie() {return zdjecie;}
    public void setZdjecie(byte[] zdjecie) {this.zdjecie = zdjecie;}



}
