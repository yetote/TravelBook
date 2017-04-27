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
    private int waveViewLenght, waveViewheight;//波长，波峰
    private int dx, dy;
    private int width, height;
    private int ducration;//动画时间
    private boolean rise;//是否会有涨水的效果
    private int originY;
    private Path path;
    /*
    * 补件动画 android动画分为三种 视图动画，帧动画，补间动画
    * 视图动画就是对控件进行变化的动画包括旋转，位移，透明，缩放
    * 帧动画是啥，我也不知道，我没用过
    * 补间动画就是今天要用到的
    * */
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

    /** 定义自定义控件中的属性
     * @param context
     * @param attrs
     */
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

    /**
     * 画出贝塞尔曲线
     */
    private void setPathData() {
        path.reset();
        //贝塞尔曲线的起始点，
        /**
         * @floatx 为起始点的x坐标
         * @floaty 为起始点的y坐标
         */
        path.moveTo(dy, -waveViewLenght + dx);
        /**<Enter
         * 进行一波for循环，为什么循环体中会画出4个波形呢？
         * 因为需要，可以调整波长来减少循环体中波形的数量，这里波长4比较合适
         * of course 你也可以通过改变i的范围来减少绘制的波形
         * 二者差不多
         */
        for (int i = waveViewLenght; i < width + waveViewLenght; i += waveViewLenght) {
            /**<Enter
             * rQuadTo是指相对上一个坐标，位置变化多少，
             * 因为这里是二阶贝塞尔曲线，所以需要三个点（包括起始点，也就是说这个方法需要传递两个点的位置进去）
             * ，三阶贝塞尔曲线可以使用cubicTo这个方法，具体可以查看源码or百度
             * @dx1,@dy1 是第一个点的x，y的坐标
             * @dx2,@dy2 是第二个点的坐标
             * 三阶的话就需要传递三个点的坐标，
             */
            path.rQuadTo(-waveViewheight, waveViewLenght / 4, 0, waveViewLenght / 2);
            path.rQuadTo(waveViewheight, waveViewLenght / 4, 0, waveViewLenght / 2);
            path.rQuadTo(-waveViewheight, waveViewLenght / 4, 0, waveViewLenght / 2);
            path.rQuadTo(waveViewheight, waveViewLenght / 4, 0, waveViewLenght / 2);
        }

        path.lineTo(-width, height);//画出左侧的线
        path.lineTo(0, -height);//画出上访的线
        path.close();
    }


    /**
     * 这里就开始装逼了，该方法为添加动画，
     * 水波总是动的是吧，要不然不就成了死水了
     */
    public void startAnimation() {
        /**<Enter
         * 通过ValueAnimtor.ofFloat设置过渡的范围，这里是0~1，当然，该方法可以传递多个值
         * 例如ValueAnimator.ofFloat(0, 1，3,5);表示从0~1~3~5
         * 既然是过渡，那么就会有时间，setDuration表示过渡的时间
         */
        animator = ValueAnimator.ofFloat(0, 1);
        //这是线性计算，什么叫做线性计算呢？
        //我的理解是在二次坐标轴里，为直线就是线性计算
        //当然，你也可以选择百度来详细的了解下
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(ducration);
        //设置运动方式，这里为永久，小树不倒我不倒
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //添加坚挺，没什么说的
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                //计算dx
                dx = (int) (waveViewLenght * fraction);
                //这个方法表示使用handler通知UI线程对屏幕进行重绘
                //简单的说就是刷新ui线程
                postInvalidate();

            }
        });
        Log.e(TAG, "startAnimation: " + "44444");
        //GO GO GO！！！
        animator.start();
    }
}
