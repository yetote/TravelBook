package com.example.tf.travelbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView clear;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initViews();
        clear.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    private void initViews() {
        clear = (TextView) findViewById(R.id.setting_clear);
        exit = (Button) findViewById(R.id.setting_exit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_exit:
                MyAppliation.TEL = "";
                MyAppliation.IS_LOGIN = false;
                Intent i = new Intent();
                i.setClass(this, MainActivity.class);
                setResult(RESULT_OK, i);
                finish();
                break;
        }
    }
}
