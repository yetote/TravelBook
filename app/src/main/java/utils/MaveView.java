package utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.example.tf.travelbook.R;

import static com.baidu.vi.VMsg.init;

/**
 * Created by TF on 2017/3/28.
 */

public class MaveView extends View {
    private Paint paint, tPaint;
    private int waveViewLenght, waveViewheight;
    private int dx, dy;
    private int width, height;
    private int ducration;
    private boolean rise;
    private int originY;
    private Path path;

    private ValueAnimator animator;
    private String TAG = "MaveView";
    private int schedule;//进度
    private String text;
    private Rect bounds;

    public MaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        waveViewLenght = (int) a.getDimension(R.styleable.WaveView_waveLenght, 800);
        waveViewheight = (int) a.getDimension(R.styleable.WaveView_waveHeight, 50);
        rise = a.getBoolean(R.styleable.WaveView_rise, false);
        ducration = (int) a.getDimension(R.styleable.WaveView_duration, 2000);
        originY = (int) a.getDimension(R.styleable.WaveView_originY, 500);
        schedule = a.getInteger(R.styleable.WaveView_schedule, 1);
        text = "下一步";
//        Log.e(TAG, "initAttrs: "+a );
        a.recycle();

    }

    private void init() {
        paint = new Paint();
        tPaint = new Paint();
        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.orange));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        tPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.orange));
        tPaint.setTextSize(40);
        tPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.tianyi));
        Log.e(TAG, "init: ");
        bounds = new Rect();
        tPaint.getTextBounds(text, 0, text.length(), bounds);
        path = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);

        width = widthsize;
        height = heightsize;
        dy = width / 3 * schedule;
        if (originY == 0) {
            originY = height;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPathData();
        canvas.drawPath(path, paint);
        Paint.FontMetricsInt fontMetrics = tPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(text, width / 2 - bounds.width() / 2, baseline, tPaint);
    }


    private void setPathData() {
        path.reset();

        path.moveTo(dy, -waveViewLenght + dx);

        for (int i = waveViewLenght; i < width + waveViewLenght; i += waveViewLenght) {

            path.rQuadTo(-waveViewheight, waveViewLenght / 4, 0, waveViewLenght / 2);
            path.rQuadTo(waveViewheight, waveViewLenght / 4, 0, waveViewLenght / 2);
            path.rQuadTo(-waveViewheight, waveViewLenght / 4, 0, waveViewLenght / 2);
            path.rQuadTo(waveViewheight, waveViewLenght / 4, 0, waveViewLenght / 2);
        }

        path.lineTo(-width, height);//画出左侧的线
        path.lineTo(0, -height);//画出上访的线
        path.close();
    }


    public void startAnimation() {

        animator = ValueAnimator.ofFloat(0, 1);

        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(ducration);

        animator.setRepeatCount(ValueAnimator.INFINITE);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();

                dx = (int) (waveViewLenght * fraction);

                postInvalidate();

            }
        });
        Log.e(TAG, "startAnimation: " + "44444");
        //GO GO GO！！！
        animator.start();
    }
}
