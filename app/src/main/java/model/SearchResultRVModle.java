package model;

/**
 * Created by TF on 2017/1/17.
 */

public class SearchResultRVModle {
    private String title;
    private String adress;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public SearchResultRVModle(String title, String adress) {
        this.title = title;
        this.adress = adress;
    }
}
