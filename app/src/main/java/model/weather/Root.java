package model.weather;

/**
 * Created by TF on 2017/3/26.
 */
public class Root {
    private String desc;

    private int status;

    private Data data;

    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }

}
