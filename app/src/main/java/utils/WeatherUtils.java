package utils;

import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TF on 2017/3/25.
 */

public class WeatherUtils {
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public WeatherUtils(String city) {
        this.city = city;
    }

    public Message weatherData() {
        String url = "http://wthrcdn.etouch.cn/weather_mini?city=" + city;
        final Message msg=new Message();
        HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {

            @Override
            public void onFinish(String request) {
                msg.what=1;
                msg.obj=request;
                Log.e("weatherutils", "onFinish: "+msg );
            }

            @Override
            public void onError(Exception e) {

            }
        });
        return msg;
    }
}
