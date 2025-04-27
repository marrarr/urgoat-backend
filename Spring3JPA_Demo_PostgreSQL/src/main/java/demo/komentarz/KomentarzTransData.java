package demo.komentarz;

public class KomentarzTransData {

    private String tresc;
    private byte[] zdjecie;
    private int postID;

    public KomentarzTransData() {
        this.tresc = "";
        this.zdjecie = null;
        this.postID = 0;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public byte[] getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
