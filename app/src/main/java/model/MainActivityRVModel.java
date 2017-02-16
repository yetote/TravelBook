package model;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import com.example.tf.travelbook.MainActivity;

/**
 * Created by TF on 2017/1/15.
 */

public class MainActivityRVModel {

    private String jd_name;
    private String jd_content;
    private String jd_img;
    private String jd_city;
    private String jd_adress;
    private int jd_id;
    private float jd_price;
    private float jd_rating;
    private String jd_type;

    public String getJd_type() {
        return jd_type;
    }

    public void setJd_type(String jd_type) {
        this.jd_type = jd_type;
    }


    public String getJd_name() {
        return jd_name;
    }

    public void setJd_name(String jd_name) {
        this.jd_name = jd_name;
    }

    public String getJd_content() {
        return jd_content;
    }

    public void setJd_content(String jd_content) {
        this.jd_content = jd_content;
    }

    public String getJd_city() {
        return jd_city;
    }

    public void setJd_city(String jd_city) {
        this.jd_city = jd_city;
    }

    public String getJd_img() {
        return jd_img;
    }

    public void setJd_img(String jd_img) {
        this.jd_img = jd_img;
    }

    public int getJd_id() {
        return jd_id;
    }

    public void setJd_id(int jd_id) {
        this.jd_id = jd_id;
    }

    public String getJd_adress() {
        return jd_adress;
    }

    public void setJd_adress(String jd_adress) {
        this.jd_adress = jd_adress;
    }

    public float getJd_price() {
        return jd_price;
    }

    public void setJd_price(float jd_price) {
        this.jd_price = jd_price;
    }

    public float getJd_rating() {
        return jd_rating;
    }

    public void setJd_rating(float jd_rating) {
        this.jd_rating = jd_rating;
    }

    public MainActivityRVModel() {
    }

    public MainActivityRVModel(String jd_name, int jd_id, String jd_content, String jd_img, float jd_price, String jd_city, String jd_adress, float jd_rating, String jd_type) {
        this.jd_name = jd_name;
        this.jd_adress = jd_adress;
        this.jd_city = jd_city;
        this.jd_content = jd_content;
        this.jd_id = jd_id;
        this.jd_img = jd_img;
        this.jd_price = jd_price;
        this.jd_rating = jd_rating;
        this.jd_type = jd_type;
    }
}
