package model;

/**
 * Created by TF on 2017/5/9.
 */

public class UserModel {
    private String nickname, tel, pwd, content;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    UserModel(String nickname, String pwd, String tel, String content) {
        this.nickname = nickname;
        this.pwd = pwd;
        this.content = content;
        this.tel = tel;
    }

    UserModel() {
    }
}
