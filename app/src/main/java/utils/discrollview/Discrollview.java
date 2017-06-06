package utils.discrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by TF on 2017/5/20.
 */

public class Discrollview extends ScrollView {

    private DiscrollViewContent mContent;

    public Discrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View content = getChildAt(0);
        mContent = (DiscrollViewContent) content;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        View first = mContent.getChildAt(0);
        first.getLayoutParams().height = getHeight();
//
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        int scrollHeight = getHeight();
        for (int i = 0; i < mContent.getChildCount(); i++) {
            View child = mContent.getChildAt(i);
            if (!(child instanceof DiscrollableInterface)) {
                continue;
            }

            //ratio 0-1
            DiscrollableInterface discrollableInterface = (DiscrollableInterface) child;
            int discrollvableTop = child.getTop();
            int discrollvableHeight = child.getHeight();

            int discrollvableAbsoluteTop = discrollvableTop - t;

            if (discrollvableAbsoluteTop <= scrollHeight) {
                int visibleGap = scrollHeight - discrollvableAbsoluteTop;

                discrollableInterface.onDiscrollce(clamp(visibleGap / (float)discrollvableHeight,1.0f,0 ));
            } else {

                discrollableInterface.onResetDiscrollve();
            }
        }
    }


    public float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, max), min);
    }

}
