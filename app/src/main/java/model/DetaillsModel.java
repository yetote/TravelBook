package model;

/**
 * Created by TF on 2017/5/14.
 */

public class DetaillsModel {
    private String content, nickname, date, img;
    private int id;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DetaillsModel(int id, String nickname, String date, String content, String img) {
        this.id = id;
        this.nickname = nickname;
        this.date = date;
        this.content = content;
        this.img = img;
    }

    public DetaillsModel() {
    }
}
