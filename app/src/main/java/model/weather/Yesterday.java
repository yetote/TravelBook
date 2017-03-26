package model.weather;

/**
 * Created by TF on 2017/3/26.
 */


public class Yesterday {
    private String fl;

    private String fx;

    private String high;

    private String type;

    private String low;

    private String date;

    public void setFl(String fl){
        this.fl = fl;
    }
    public String getFl(){
        return this.fl;
    }
    public void setFx(String fx){
        this.fx = fx;
    }
    public String getFx(){
        return this.fx;
    }
    public void setHigh(String high){
        this.high = high;
    }
    public String getHigh(){
        return this.high;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setLow(String low){
        this.low = low;
    }
    public String getLow(){
        return this.low;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }

}

