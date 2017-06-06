package utils.ScrollRemoveRecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by TF on 2017/5/25.
 */

public class MyRv extends RecyclerView {
    private Context mContext;
    private int mLastX, mLastY;//上一次的触摸点
    private int mPosition;//当前触摸的item的位置
    private View mItemLayout;//item对应的布局
    private Button mDelete;//删除按钮
    private int mMaxLength;//最大滑动距离(即删除按钮的宽度)
    private boolean isDragging;//是否在垂直滑动列表
    private boolean isItemMoving;//item是在否跟随手指移动
    private boolean isStartScroll;//item是否开始自动滑动

    private int mDeleteBtnState;
    /*删除按钮状态
     *0：关闭
     *1：将要关闭
     *2：将要打开
     *3：打开
     **/

    private VelocityTracker mVelocityTracker;//检测手指在滑动过程中的速度
    private Scroller mScroller;
    private OnItemClickListener mListener;

    public MyRv(Context context) {
        this(context, null);
    }

    public MyRv(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRv(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mScroller = new Scroller(context, new LinearInterpolator());
        mVelocityTracker = VelocityTracker.obtain();
    }


//    public MyRv(Context context) {
//        super(context);
//        this.context = context;
//
//    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mVelocityTracker.addMovement(e);

        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mDeleteBtnState == 0) {
                    View v = findChildViewUnder(x, y);
                    if (v == null) {
                        return false;
                    }
                    MyViewHolder vh = (MyViewHolder) getChildViewHolder(v);
                    mItemLayout = vh.layout;
                    mPosition = vh.getAdapterPosition();
                    mDelete = vh.getBtn();
                    mMaxLength = mDelete.getWidth();
                    mDelete.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(mContext, "aaa", Toast.LENGTH_SHORT).show();
                            mListener.onDeleteClick(mPosition);
                            mItemLayout.scrollTo(0, 0);
//                            Toast.makeText(mContext, "asfdf", Toast.LENGTH_SHORT).show();
                            mDeleteBtnState = 0;
                        }
                    });

                } else if (mDeleteBtnState == 3) {
                    mScroller.startScroll(mItemLayout.getScrollX(), 0, -mMaxLength, 0, 200);
                    invalidate();
                    mDeleteBtnState = 0;
                    return false;
                } else {
                    return false;
                }
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                int dx = mLastX - x;
                int dy = mLastY - y;
                int scrollX = mItemLayout.getScrollX();
                if (Math.abs(dx) > Math.abs(dy)) {//左边界检测
                    isItemMoving = true;
                    if (scrollX + dx <= 0) {
                        mItemLayout.scrollTo(0, 0);
                        return true;
                    } else if (scrollX + dx >= mMaxLength) {
                        mItemLayout.scrollTo(mMaxLength, 0);
                        return true;
                    }
                    mItemLayout.scrollBy(dx, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && mListener != null) {
                    mListener.onItemClick(mItemLayout, mPosition);
                }
                isItemMoving = false;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                float yVelocity = mVelocityTracker.getYVelocity();
                int deltaX = 0;
                int upScrollX = mItemLayout.getScrollX();
                if (Math.abs(xVelocity) > 100 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
                    if (xVelocity <= -100) {
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;
                    } else if (xVelocity > 100) {
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    }
                } else {
                    if (upScrollX >= mMaxLength / 2) {
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;
                    } else if (upScrollX <= mMaxLength / 2) {
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    }
                }
                mScroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                isStartScroll = true;
                invalidate();
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            mItemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else if (isStartScroll) {
            isStartScroll = false;
            if (mDeleteBtnState == 1) {
                mDeleteBtnState = 0;
            }
            if (mDeleteBtnState == 2) {
                mDeleteBtnState = 3;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isDragging = state == SCROLL_STATE_DRAGGING;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
