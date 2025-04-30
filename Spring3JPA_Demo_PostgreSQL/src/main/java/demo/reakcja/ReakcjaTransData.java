package demo.reakcja;

public class ReakcjaTransData {

    private Integer postID;
    private Integer komentarzID;
    private int reakcja;

    public ReakcjaTransData() {
        this.postID = null;
        this.komentarzID = null;
        this.reakcja = 0; // domyślnie brak reakcji
    }

    public ReakcjaTransData(Integer postID, Integer komentarzID, int reakcja) {
        this.postID = postID;
        this.komentarzID = komentarzID;
        this.reakcja = reakcja;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public Integer getKomentarzID() {
        return komentarzID;
    }

    public void setKomentarzID(Integer komentarzID) {
        this.komentarzID = komentarzID;
    }

    public int getReakcja() {
        return reakcja;
    }

    public void setReakcja(int reakcja) {
        this.reakcja = reakcja;
    }
}
