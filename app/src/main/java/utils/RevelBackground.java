package utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.example.tf.travelbook.R;

/**
 * Created by TF on 2017/5/18.
 */

public class RevelBackground extends View {
    private Paint paint;
    private int startX, startY, raduis;//圆的圆心的x，y坐标，半径
    private onStateChangeLisener listener;
    public static int ANIMATOR_UNSTART = 0;
    public static int ANIMATOR_STARTING = 1;
    public static int ANIMATOR_FINISH = 2;

    public void setListener(onStateChangeLisener listener) {
        this.listener = listener;
    }

    public void setRaduis(int raduis) {
        this.raduis = raduis;
        //因为半径不断变化，通知onDraw方法进行重绘
        invalidate();
    }

    public RevelBackground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(startX, startY, raduis, paint);
    }

    public void startRevelAnimator(int[] location) {
        listener.onStateChange(ANIMATOR_UNSTART);
        startX = location[0];
        startY = location[1];
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "raduis", 0, getWidth() / 2 + getHeight());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                listener.onStateChange(ANIMATOR_FINISH);

            }
        });
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    public interface onStateChangeLisener {
        void onStateChange(int state);
    }
}
