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
 * Created by TF on 2017/2/24.
 */

public class ViewTabModel {
    private String jd_name, jd_img;

    public String getJd_name() {
        return jd_name;
    }

    public void setJd_name(String jd_name) {
        this.jd_name = jd_name;
    }

    public String getJd_img() {
        return jd_img;
    }

    public void setJd_img(String jd_img) {
        this.jd_img = jd_img;
    }

    public ViewTabModel(String jd_img, String jd_name) {
        this.jd_img = jd_img;
        this.jd_name = jd_name;
    }

    public ViewTabModel() {
    }
}
