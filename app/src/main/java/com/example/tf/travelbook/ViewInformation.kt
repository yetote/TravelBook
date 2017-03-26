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
package com.example.tf.travelbook

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_information.*
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide

import java.util.ArrayList

import model.ViewInformationModel
import utils.HttpUtils

class ViewInformation : AppCompatActivity() {
    //    private var img: ImageView? = null
//    private var type: TextView? = null
//    private var contnet: TextView? = null
//    private var adress: TextView? = null
    internal var url: String? = null
    internal var city: String? = null
    internal var TAG = "[$javaClass]"
    private var list: List<ViewInformationModel>? = null
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                Toast.makeText(this@ViewInformation, "对不起，您的选项未被查询到", Toast.LENGTH_SHORT).show()
            } else if (msg.what == 1) {

                val list = JSON.parseArray(msg.obj.toString(), ViewInformationModel::class.java)
                for (model in list) {
                    list.add(ViewInformationModel(model.jd_img, model.jd_adress, model.jd_content, model.jd_type))
                    Log.e(TAG, "handleMessage: " + list.size)
                    view_information_viewtype.text = list[0].jd_type
                    view_information_viewcontent.text = list[0].jd_content
                    view_information_adress.text = list[0].jd_adress
                    Glide.with(this@ViewInformation).load(list[0].jd_img).into(view_information_img)
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_information)
        val i = intent
        city= i.getStringExtra("city")
        list = ArrayList()
        http()
    }

    private fun http() {
        url = "http://123.206.60.236/travelbook/view_in_sc.php?name=" + city
        HttpUtils.HttpUtilsConnection(url, object : HttpUtils.HttpCallBackListener {
            override fun onFinish(request: String) {
                val msg = Message()
                if (request == "null") {
                    msg.what = 0
                } else {
                    msg.what = 1
                }
                msg.obj = request
                handler.sendMessage(msg)
            }

            override fun onError(e: Exception) {

            }
        })
    }
}
