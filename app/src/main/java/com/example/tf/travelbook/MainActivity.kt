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

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.baidu.mapapi.SDKInitializer

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SDKInitializer.initialize(applicationContext)
        setContentView(R.layout.activity_main)
        onclick()
    }

    fun onclick() {
        mainactivity_menu_map.setOnClickListener(this)
        mainactivity_menu_near.setOnClickListener(this)
        mainactivity_menu_trip.setOnClickListener(this)
        mainactivity_menu_mine.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mainactivity_menu_map -> fragmentManager.beginTransaction().replace(R.id.mainactivity_fragment, MapFragment()).commit()
            R.id.mainactivity_menu_near ->fragmentManager.beginTransaction().replace(R.id.mainactivity_fragment, NearFragment()).commit()
            R.id.mainactivity_menu_trip ->fragmentManager.beginTransaction().replace(R.id.mainactivity_fragment, TripFragment()).commit()
            R.id.mainactivity_menu_mine ->fragmentManager.beginTransaction().replace(R.id.mainactivity_fragment, MineFragment()).commit()
        }
    }

    fun toast(message: String, tag: String = "[$javaClass]", time: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, "[$message][$tag]", time)
    }
}
