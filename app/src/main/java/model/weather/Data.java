package model.weather;

import java.util.List;

/**
 * Created by TF on 2017/3/26.
 */
public class Data {
    private String wendu;

    private String ganmao;

    private List<Forecast> forecast ;

    private Yesterday yesterday;

    private String aqi;

    private String city;

    public void setWendu(String wendu){
        this.wendu = wendu;
    }
    public String getWendu(){
        return this.wendu;
    }
    public void setGanmao(String ganmao){
        this.ganmao = ganmao;
    }
    public String getGanmao(){
        return this.ganmao;
    }
    public void setForecast(List<Forecast> forecast){
        this.forecast = forecast;
    }
    public List<Forecast> getForecast(){
        return this.forecast;
    }
    public void setYesterday(Yesterday yesterday){
        this.yesterday = yesterday;
    }
    public Yesterday getYesterday(){
        return this.yesterday;
    }
    public void setAqi(String aqi){
        this.aqi = aqi;
    }
    public String getAqi(){
        return this.aqi;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }

}
