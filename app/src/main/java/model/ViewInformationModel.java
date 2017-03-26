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

public class ViewInformationModel {
    private String jd_img, jd_adress, jd_type, jd_content;

    public String getJd_img() {
        return jd_img;
    }

    public void setJd_img(String jd_img) {
        this.jd_img = jd_img;
    }

    public String getJd_adress() {
        return jd_adress;
    }

    public void setJd_adress(String jd_adress) {
        this.jd_adress = jd_adress;
    }

    public String getJd_type() {
        return jd_type;
    }

    public void setJd_type(String jd_type) {
        this.jd_type = jd_type;
    }

    public String getJd_content() {
        return jd_content;
    }

    public void setJd_content(String jd_content) {
        this.jd_content = jd_content;
    }

    public ViewInformationModel() {
    }

    public ViewInformationModel(String jd_img, String jd_adress, String jd_content, String jd_type) {
        this.jd_adress = jd_adress;
        this.jd_content = jd_content;
        this.jd_img = jd_img;
        this.jd_type = jd_type;
    }
}
