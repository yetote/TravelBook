package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by TF on 2017/1/26.
 */

public class HttpUtils {
    private final int MSG_WHAT = 0;

    public static void HttpUtilsConnection(final String url, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn;
                try {
                    URL uri = new URL(url);
                    conn = (HttpURLConnection) uri.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(8000);
                    conn.setConnectTimeout(8000);
                    InputStream is = conn.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader bf = new BufferedReader(reader);
                    StringBuilder request = new StringBuilder();
                    String line = null;
                    while ((line = bf.readLine()) != null) {
                        request.append(line);
                    }
                    listener.onFinish(request.toString());
                    bf.close();
                    reader.close();
                    is.close();
                } catch (IOException e) {
                    listener.onError(e);
                    e.printStackTrace();
                }


            }
        }).start();

    }

    public interface HttpCallBackListener {
        void onFinish(String request);

        void onError(Exception e);
    }
}
