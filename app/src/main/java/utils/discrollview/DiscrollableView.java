package utils.discrollview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

/**
 * Created by TF on 2017/5/20.
 */

public class DiscrollableView extends FrameLayout implements DiscrollableInterface {
    private static final int TRANSLATION_FROM_TOP = 0X01;
    private static final int TRANSLATION_FROM_BOTTOM = 0X02;
    private static final int TRANSLATION_FROM_LEFT = 0X04;
    private static final int TRANSLATION_FROM_RIGHT = 0X08;

    private static ArgbEvaluator sArgbEvaluation = new ArgbEvaluator();
    /*
    *   自定义属性的一些接受的变量
    * */
    private int mDiscrollveFromBgColor;
    private int mDiscrollveToBgColor;
    private boolean mDiscrollveAlpha;
    private int mDiscrollveTranslation;
    private boolean mDiscrollveScaleX;
    private boolean mDiscrollveScaleY;
    private int mHeight;
    private int mWidth;

    public static void setsArgbEvaluation(ArgbEvaluator sArgbEvaluation) {
        DiscrollableView.sArgbEvaluation = sArgbEvaluation;
    }

    public void setmDiscrollveFromBgColor(int mDiscrollveFromBgColor) {
        this.mDiscrollveFromBgColor = mDiscrollveFromBgColor;
    }

    public void setmDiscrollveToBgColor(int mDiscrollveToBgColor) {
        this.mDiscrollveToBgColor = mDiscrollveToBgColor;
    }

    public void setmDiscrollveAlpha(boolean mDiscrollveAlpha) {
        this.mDiscrollveAlpha = mDiscrollveAlpha;
    }

    public void setmDiscrollveTranslation(int mDiscrollveTranslation) {
        this.mDiscrollveTranslation = mDiscrollveTranslation;
    }

    public void setmDiscrollveScaleX(boolean mDiscrollveScaleX) {
        this.mDiscrollveScaleX = mDiscrollveScaleX;
    }

    public void setmDiscrollveScaleY(boolean mDiscrollveScaleY) {
        this.mDiscrollveScaleY = mDiscrollveScaleY;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        onResetDiscrollve();
    }


    public DiscrollableView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onDiscrollce(float ratio) {
        if (mDiscrollveAlpha) {
            setAlpha(ratio);
        }
        if (mDiscrollveScaleX) {
            setScaleX(ratio);
        }
        if (mDiscrollveScaleY) {
            setScaleY(ratio);
        }

        if (isDiscrollTranslationFrom(TRANSLATION_FROM_BOTTOM)) {
            setTranslationY(mHeight * (1 - ratio));
        }
        if (isDiscrollTranslationFrom(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight * (1 - ratio));
        }
        if (isDiscrollTranslationFrom(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mHeight * (1 - ratio));
        }
        if (isDiscrollTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mHeight * (1 - ratio));
        }
        if (mDiscrollveFromBgColor != -1 && mDiscrollveToBgColor != -1) {
            setBackgroundColor((Integer) sArgbEvaluation.evaluate(ratio, mDiscrollveFromBgColor, mDiscrollveToBgColor));
        }
    }

    @Override
    public void onResetDiscrollve() {
        //控制自身的动画属性
        if (mDiscrollveAlpha) {
            setAlpha(0);
        }
        if (mDiscrollveScaleX) {
            setScaleX(0);
        }
        if (mDiscrollveScaleY) {
            setScaleY(0);
        }

//		int mDisCrollveTranslation 有很多种枚举的情况

        //判断到底是哪一种值：fromTop,fromBottom,fromLeft,fromRight
        //fromBottom
        if (isDiscrollTranslationFrom(TRANSLATION_FROM_BOTTOM)) {
            setTranslationY(mHeight);//mHeight-->0(代表原来的位置)
        }
        if (isDiscrollTranslationFrom(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight);//-mHeight-->0(代表原来的位置)
        }
        if (isDiscrollTranslationFrom(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mWidth);//-width-->0(代表原来的位置)
        }
        if (isDiscrollTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth);//width-->0(代表原来的位置)
        }
    }

    private boolean isDiscrollTranslationFrom(int translationMask) {
        if (mDiscrollveTranslation == -1) {
            return false;
        }
        return (mDiscrollveTranslation & translationMask) == translationMask;
    }
}
