
package com.example.tf.travelbook

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import utils.HttpUtils

class NearView : AppCompatActivity() {
    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            html(msg.obj.toString())

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_view)
        val i = intent
        val name = i.getStringExtra("title")
        //        Log.e(TAG, name);
        if (name == "金石滩") {
            val url = "http://123.206.60.236/travelbook/jd_jst.html"
            HttpUtils.HttpUtilsConnection(url, object : HttpUtils.HttpCallBackListener {
                override fun onFinish(request: String) {
                    val msg = Message()
                    msg.what = 1
                    msg.obj = request.toString()
                    handler.sendMessage(msg)
                }

                override fun onError(e: Exception) {

                }
            })
        } else {
            val ad = AlertDialog.Builder(this)
            ad.setTitle("请您原谅")
            ad.setMessage("")
            ad.setPositiveButton("确定", null)
            ad.show()
            //finish();
        }


    }

    private fun html(url: String) {
        Log.e(TAG, "html: " + url)
        //        RichText.from(url).imageClick(new OnImageClickListener() {
        //            @Override
        //            public void imageClicked(List<String> imageUrls, int position) {
        //                Toast.makeText(NearView.this, "我是图片", Toast.LENGTH_SHORT).show();
        //
        //            }
        //        }).imageLongClick(new OnImageLongClickListener() {
        //            @Override
        //            public boolean imageLongClicked(List<String> imageUrls, int position) {
        //                Toast.makeText(NearView.this, "你还按？", Toast.LENGTH_SHORT).show();
        //                return true;
        //            }
        //        }).into(tv);
    }

    companion object {
        private val TAG = "NearView"
    }


}
