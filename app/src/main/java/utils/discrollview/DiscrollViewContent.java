package utils.discrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tf.travelbook.R;

/**
 * Created by TF on 2017/5/20.
 */

public class DiscrollViewContent extends LinearLayout {

    public DiscrollViewContent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(), attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        MyLayoutParams p = (MyLayoutParams) params;
        if (!isDiscrollable(p)) {
            super.addView(child, index, params);
        } else {
            //在addview的时候插一脚，往child的外层包裹上一层自己的framelayout
            DiscrollableView discrollableView = new DiscrollableView(getContext());
            discrollableView.setmDiscrollveAlpha(p.mDiscrollveAlpha);
            discrollableView.setmDiscrollveFromBgColor(p.mDiscrollveFromBgColor);
            discrollableView.setmDiscrollveToBgColor(p.mDiscrollveToBgColor);
            discrollableView.setmDiscrollveScaleX(p.mDiscrollveScaleX);
            discrollableView.setmDiscrollveScaleY(p.mDiscrollveScaleY);
            discrollableView.setmDiscrollveTranslation(p.mDiscrollveTranslation);
            discrollableView.addView(child);
            super.addView(discrollableView, index, params);
        }
    }

    private boolean isDiscrollable(MyLayoutParams p) {
        return p.mDiscrollveAlpha ||
                p.mDiscrollveScaleY ||
                p.mDiscrollveScaleX ||
                p.mDiscrollveTranslation != -1 ||
                (p.mDiscrollveToBgColor != -1 &&
                        p.mDiscrollveFromBgColor != -1);
    }

    public static class MyLayoutParams extends LayoutParams {
        private int mDiscrollveFromBgColor;
        private int mDiscrollveToBgColor;
        private boolean mDiscrollveAlpha;
        private int mDiscrollveTranslation;
        private boolean mDiscrollveScaleX;
        private boolean mDiscrollveScaleY;

        public MyLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiscrollView_LayoutParams);
            mDiscrollveAlpha = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_alpha, false);
            mDiscrollveScaleX = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleX, false);
            mDiscrollveScaleY = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleY, false);
            mDiscrollveFromBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_fromBgColor, -1);
            mDiscrollveToBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_toBgColor, -1);
            mDiscrollveTranslation = a.getInt(R.styleable.DiscrollView_LayoutParams_discrollve_translation, -1);
            a.recycle();
        }
    }
}
