package com.example.tf.travelbook;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import utils.HttpUtils;

public class EnrollActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText tel, nickname, firstPwd, secondPwd;
    private Button btn;
    private FloatingActionButton fab;
    private CardView cardView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (msg.obj.equals("success")) {
                    Toast.makeText(EnrollActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EnrollActivity.this, "未知原因注册失败，请重新注册", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initViews();
        btn.setOnClickListener(this);
        fab.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showAnimation();
        }
    }

    private void showAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.in);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(transition);
        }
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cardView.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                startAnimation();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnimation();
            }
        });
    }

    private void closeAnimation() {
        Animator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth() / 2, 0, cardView.getHeight(), cardView.getWidth() / 2);
        }
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                cardView.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                EnrollActivity.super.onBackPressed();
            }
        });
        animator.start();
    }

    private void startAnimation() {
        Animator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth() / 2, 0, fab.getWidth() / 2, cardView.getHeight());
        }
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cardView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        animator.start();
    }

    private void initViews() {
        fab = (FloatingActionButton) findViewById(R.id.register_fab);
        tel = (EditText) findViewById(R.id.register_tel);
        firstPwd = (EditText) findViewById(R.id.register_firstpwd);
        secondPwd = (EditText) findViewById(R.id.register_secondpwd);
        nickname = (EditText) findViewById(R.id.register_username);
        btn = (Button) findViewById(R.id.register_sure);
        cardView = (CardView) findViewById(R.id.register_cardview);
    }

    boolean NULL() {
        return firstPwd.getText().toString().isEmpty()
                || tel.getText().toString().isEmpty()
//                || (!firstPwd.getText().toString().equals(secondPwd.getText().toString()))
                || secondPwd.getText().toString().isEmpty()
                || nickname.getText().toString().isEmpty()
                || firstPwd.getText().toString().isEmpty()
                || secondPwd.getText().toString().isEmpty();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_sure:
                if (NULL()) {
                    Toast.makeText(this, "信息未填写完整，请检查", Toast.LENGTH_SHORT).show();
                } else if (!TRUE()) {

                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "http://123.206.60.236/travelbook/enroll.php?nickname=" + nickname.getText().toString() + "&pwd=" + firstPwd.getText().toString() + "&tel=" + tel.getText().toString();
                    HttpUtils.HttpUtilsConnection(url, new HttpUtils.HttpCallBackListener() {
                        @Override
                        public void onFinish(String request) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = request;
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
                break;
            case R.id.register_fab:
                closeAnimation();
                break;
        }
    }

    private boolean TRUE() {
        return (firstPwd.getText().toString().equals(secondPwd.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        closeAnimation();
    }
}
