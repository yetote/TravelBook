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

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_long_trip.*

class LongTrip : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_trip)
        click()
    }

    internal fun click() {
        long_trip_start.setOnClickListener(this)
        long_trip_finish.setOnClickListener(this)
        long_trip_sure.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            1 -> {
                val city = data.getStringExtra("city")
                long_trip_start.text = city
                if (long_trip_start.text.toString() != "请选择" && long_trip_finish.text.toString() != "请选择" && long_trip_finish.text.toString() != long_trip_start.text.toString()) {
                    long_trip_sure.visibility = View.VISIBLE
                }
            }
            2 -> {
                val long_trip_finish_city = data.getStringExtra("city")
                long_trip_finish.text = long_trip_finish_city
                if (long_trip_start.text.toString() != "请选择" && long_trip_finish.text.toString() != "请选择" && long_trip_finish.text.toString() != long_trip_start.text.toString()) {
                    long_trip_sure.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.long_trip_start -> {
                val i = Intent()
                i.setClass(this, LongTripView::class.java)
                startActivityForResult(i, 1)
            }
            R.id.long_trip_finish -> {
                val i1 = Intent()
                i1.setClass(this, LongTripView::class.java)
                startActivityForResult(i1, 2)
            }
            R.id.long_trip_sure -> {
                val ad = AlertDialog.Builder(this)
                ad.setTitle("请为您的行程命名")
                ad.setView(EditText(this))
                ad.setPositiveButton("确定") { dialog, which ->
                    //执行添加语句
                }
                ad.setNegativeButton("取消", null)
                ad.show()
            }
        }
    }
}
