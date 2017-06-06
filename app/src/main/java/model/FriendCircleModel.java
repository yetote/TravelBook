package model;

/**
 * Created by TF on 2017/5/14.
 */

public class FriendCircleModel {
    private int user, id, hobby, comment;
    private String content, time, image, img, nickname;

    public int getHobby() {
        return hobby;
    }

    public void setHobby(int hobby) {
        this.hobby = hobby;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public FriendCircleModel(String time, String content, String nickname, String image, String img, int user, int id, int hobby, int comment) {
        this.time = time;
        this.nickname = nickname;
        this.content = content;
        this.image = image;
        this.img = img;
        this.id = id;
        this.user = user;
        this.hobby = hobby;
        this.comment = comment;
    }

    public FriendCircleModel() {
    }
}
