//          三清在上     代码永无BUG
//  佛曰：
//        写字楼里写字间,写字间里程序员;
//        程序人员写程序,又拿程序换酒钱;
//        酒醒只在网上坐,酒醉还来网下眠;
//        酒醉酒醒日复日,网上网下年复年;
//        但愿老死电脑间,不愿鞠躬老板前;
//        奔驰宝马贵者趣,公交自行程序员;
//        别人笑我忒疯癫,我笑自己命太贱;
//        不见满街漂亮妹,哪个归得程序员.

package com.example.tf.travelbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

public class MainActivity extends AppCompatActivity {
    private RadioButton map, near, trip, mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        map = (RadioButton) findViewById(R.id.mainactivity_menu_map);
        near = (RadioButton) findViewById(R.id.mainactivity_near);
        trip = (RadioButton) findViewById(R.id.mainactivity_trip);
        mine = (RadioButton) findViewById(R.id.mainactivity_mine);


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_fragment, new MapFragment()).commit();
                Toast.makeText(MainActivity.this, "a", Toast.LENGTH_SHORT).show();
            }

        });
        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_fragment, new NearFragment()).commit();
                Toast.makeText(MainActivity.this, "b", Toast.LENGTH_SHORT).show();
            }
        });
        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_fragment, new TripFragment()).commit();

            }
        });
        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_fragment, new MineFragment()).commit();
            }
        });
    }
}
