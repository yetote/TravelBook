//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      佛祖保佑       永无BUG
package model;

/**
 * Created by TF on 2017/2/16.
 */

public class ViewImgModel {
    private String jd_img;
    private String jd_city;

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

    private ViewImgModel(String jd_img, String jd_city) {
        this.jd_img = jd_img;
        this.jd_city = jd_city;
    }

    private ViewImgModel() {
    }
}
