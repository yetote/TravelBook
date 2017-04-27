package utils.swipecardview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by TF on 2017/4/6.
 */

public class SwipeCardViewLayoutManger extends LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if (itemCount < 1) {
            return;
        }
        int bottomPosition;
        if (itemCount < CardConfig.MAX_SHOW_COUNT) {
            bottomPosition = 0;
        } else {
            bottomPosition = itemCount - CardConfig.MAX_SHOW_COUNT;
        }
        for (int position = bottomPosition; position < itemCount; position++) {
            View view = recycler.getViewForPosition(position);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            layoutDecorated(view,
                    widthSpace / 2,
                    heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));
            int level = itemCount - position - 1;
            if (level > 0) {
                view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * level);
                    view.setScaleY(1 - CardConfig.SCALE_GAP * level);
                } else {
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level - 1));
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level - 1));
                }
            }


        }

    }
}
