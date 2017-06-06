package model;

/**
 * Created by TF on 2017/5/11.
 */

public class UserUpdate {
    private String name, code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserUpdate(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
