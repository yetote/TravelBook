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
import android.os.Message
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_short_trip_view.*
import utils.HttpUtils

class ShortTripView : AppCompatActivity(), View.OnClickListener {
//    private var shorttripview: ConstraintLayout? = null
//    private var sure: Button? = null
//    private var choose: Button? = null
//    private var insert: Button? = null
    internal var a: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_trip_view)
//        sure = findViewById(R.id.short_trip_view_sure) as Button
//        insert = findViewById(R.id.short_trip_view_insert) as Button
//        choose = findViewById(R.id.short_trip_view_btn) as Button
//        shorttripview = findViewById(R.id.activity_short_trip_view) as ConstraintLayout
       // a = 0
        choose_event()
        sure_event()
        insert_event()

    }

    private fun choose_event() {
        short_trip_view_btn.setOnClickListener(this)
    }

    private fun sure_event() {
        short_trip_view_sure.setOnClickListener(this)

    }

    private fun insert_event() {
        short_trip_view_btn.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.short_trip_view_btn -> {
                val i = Intent()
                i.setClass(this, ShortTrip::class.java)
                startActivityForResult(i, 1)
            }
            R.id.short_trip_view_insert -> {
                val lp = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val constraintLayout = ConstraintLayout(this)
                val tv = TextView(this)
                tv.text = "景点" + a
                //tv.setPadding();
                val btn = Button(this)
                btn.setOnClickListener(this)
                constraintLayout.addView(tv)
                btn.setOnClickListener {
                    val i = Intent()
                    i.setClass(this@ShortTripView, ShortTrip::class.java)
                    startActivityForResult(i, 1)
                }
                btn.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                btn.text = "请选择"
                constraintLayout.addView(btn)
                addContentView(constraintLayout, lp)
            }
            R.id.short_trip_view_sure -> {
                val url = ""
                HttpUtils.HttpUtilsConnection(url, object : HttpUtils.HttpCallBackListener {
                    override fun onFinish(request: String) {
                        val msg = Message()
                        msg.what = 1
                        msg.obj = request.toString()

                    }

                    override fun onError(e: Exception) {


                    }
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                short_trip_view_btn.text = data.getStringExtra("view")
                if (short_trip_view_btn.text.toString() != "请选择") {
                    short_trip_view_insert.visibility = View.VISIBLE
                    short_trip_view_sure.visibility = View.VISIBLE
                }
            }
        }
    }
}
