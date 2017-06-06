package com.example.tf.travelbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class WelcomePagerActivity extends AppCompatActivity {
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_pager);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        enter = (Button) findViewById(R.id.welcome_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sp = getSharedPreferences("first", MODE_PRIVATE).edit();
                sp.putBoolean("first", true);
                sp.commit();
                Intent i = new Intent();
                i.setClass(WelcomePagerActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
