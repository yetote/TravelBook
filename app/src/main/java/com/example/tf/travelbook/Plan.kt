
package com.example.tf.travelbook

import adapter.PlanAdapterTwo
import android.app.ActionBar
import android.app.Activity
import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_plan.*
import java.util.*

class Plan : Activity(),PlanViewFragment.view_return, View.OnClickListener, ViewTabFragment.list_size {
    override fun viewreturn(view: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        city_tab!!.text = view
        //getFragmentManager().beginTransaction().replace(R.id.plan_fragment, new NearFragment()).commit();
        view_tab!!.select()
    }

    private var city_tab: ActionBar.Tab? = null
    private var view_tab: ActionBar.Tab? = null
    private var all_tab: ActionBar.Tab? = null
    internal var size = 0
    private val TAG = "Plan"
    private var plan_list: MutableList<String>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)
        val bar = actionBar
        bar.navigationMode = ActionBar.NAVIGATION_MODE_TABS

        // plan_list = MutableList<String>()
        city_tab = bar.newTab()
        city_tab!!.text = "全国"
        val city_tab_listener = MyTabListener(PlanViewFragment())
        city_tab!!.setTabListener(city_tab_listener)

        view_tab = bar.newTab()
        view_tab!!.text = "热门景点"
        val view_tab_listener = MyTabListener(ViewTabFragment())
        view_tab!!.setTabListener(view_tab_listener)

        all_tab = bar.newTab()
        all_tab!!.text = "全部景点"
        all_tab!!.setTabListener(city_tab_listener)

        bar.addTab(city_tab)
        bar.addTab(view_tab)
        bar.addTab(all_tab)


        // next = findViewById(R.id.plan_view_fm_next) as Button
        // rv = findViewById(R.id.plan_rv_plan) as RecyclerView
        plan_view_fm_next.setOnClickListener(this)
        plan_view_fm_next.startAnimation()

    }



    override fun list_size(list: MutableList<String>) {
        size = list.size
        plan_list = list
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.plan_view_fm_next -> {
                Log.e(TAG, "onClick: " + size)
                if (size == 0) {
                    Toast.makeText(this, "请您选择景点", Toast.LENGTH_SHORT).show()
                } else {
                    val i = Intent()
                    i.setClass(this, BasicNeed::class.java)
                    Log.e(TAG, "onClick: " + plan_list!!)
                    i.putStringArrayListExtra("plan_list", plan_list as ArrayList<String>?)
                    startActivity(i)
                }
            }
        }
    }


    internal inner class MyTabListener(private val fragment: Fragment) : ActionBar.TabListener {

        override fun onTabSelected(tab: ActionBar.Tab, ft: FragmentTransaction) {
            val bundle = Bundle()
            bundle.putString("city", city_tab!!.text.toString())
            fragment.arguments = bundle
            ft.replace(R.id.plan_fragment, fragment)

        }

        override fun onTabUnselected(tab: ActionBar.Tab, ft: FragmentTransaction) {
            ft.remove(fragment)
        }

        override fun onTabReselected(tab: ActionBar.Tab, ft: FragmentTransaction) {

        }
    }

    fun toast(text: String, time: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, time).show()
    }

}
