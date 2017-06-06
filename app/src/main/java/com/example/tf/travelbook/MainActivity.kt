
package com.example.tf.travelbook

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import com.baidu.mapapi.SDKInitializer

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SDKInitializer.initialize(applicationContext)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.title = "地图"
        toolbar.setTitleTextColor(Color.WHITE)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        onclick()
    }

    fun onclick() {
        mainactivity_menu_map.setOnClickListener(this)
        mainactivity_menu_near.setOnClickListener(this)
        mainactivity_menu_trip.setOnClickListener(this)
        mainactivity_menu_mine.setOnClickListener(this)
        sees.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mainactivity_menu_map -> {
                fragmentManager.beginTransaction().replace(R.id.mainactivity_fragment, MapFragment()).commit()
                toolbar.title = "地图"
            }
            R.id.mainactivity_menu_near -> {
                fragmentManager.beginTransaction().replace(R.id.mainactivity_fragment, NearFragment()).commit()
                toolbar.title = "附近"
            }
            R.id.mainactivity_menu_trip -> {
                if (MyAppliation.IS_LOGIN == false) {
                    var i = Intent()
                    i.setClass(this, LoginActivity::class.java)
                    startActivity(i)
                } else {
                    fragmentManager.beginTransaction().replace(R.id.mainactivity_fragment, TripFragment()).commit()
                    toolbar.title = "行程"
                }
            }
            R.id.mainactivity_menu_mine -> {
                if (MyAppliation.IS_LOGIN == false) {
                    var i = Intent()
                    i.setClass(this, LoginActivity::class.java)
                    startActivity(i)
                } else {
                    fragmentManager.beginTransaction().replace(R.id.mainactivity_fragment, MineFragment()).commit()
                    toolbar.title = "我的"
                }
            }
            R.id.sees -> {
                var i = Intent()
                if (MyAppliation.IS_LOGIN == false) {
                    i.setClass(this, LoginActivity::class.java)
                } else {
                    i.setClass(this, FriendCircleActivity::class.java)
                    var location: IntArray = intArrayOf(0, 0)
                    var startX: Int = v.width
                    location[0] += startX
                    v.getLocationOnScreen(location)
                    i.putExtra("location", location)
                }
                startActivity(i)

                overridePendingTransition(0, 0)

            }
        }

        fun toast(message: String, tag: String = "[$javaClass]", time: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(this, "[$message][$tag]", time)
        }
    }
}
