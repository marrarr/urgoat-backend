package demo.reakcja;

public class ReakcjaTransData {

    private int postID;
    private int komentarzID;
    private int reakcja;

    public ReakcjaTransData() {
        this.postID = -1;
        this.komentarzID = -1;
        this.reakcja = 0; // domyślnie brak reakcji
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getKomentarzID() {
        return komentarzID;
    }

    public void setKomentarzID(int komentarzID) {
        this.komentarzID = komentarzID;
    }

    public int getReakcja() {
        return reakcja;
    }

    public void setReakcja(int reakcja) {
        this.reakcja = reakcja;
    }
}
