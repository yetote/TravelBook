package model;

/**
 * Created by TF on 2017/2/16.
 */

public class ViewImgModel {
    private String jd_img;

    public String getJd_img() {
        return jd_img;
    }

    public void setJd_img(String jd_img) {
        this.jd_img = jd_img;
    }

    private ViewImgModel(String jd_img) {
        this.jd_img = jd_img;
    }

    private ViewImgModel() {
    }
}
