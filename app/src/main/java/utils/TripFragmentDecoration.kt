package utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlin.jvm.internal.Intrinsics

/**
 * Created by TF on 2017/3/25.
 */
class TripFragmentDecoration(var context: Context) : RecyclerView.ItemDecoration() {
    private val mContext: Context? = null
    private val mDivider: Drawable? = null
    private val mOrientation: Int = 0
    private val ATTRS = intArrayOf(android.R.attr.listDivider)

    init {
        var context = this.mContext
        var ta: TypedArray = context!!.obtainStyledAttributes(ATTRS)
        this.mDivider
        ta.recycle()
    }


    fun drawlines(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        var left: Int = parent.left
        var right = parent.width - parent.right
        var childCounts: Int = parent.childCount
        for (i in 0 until childCounts) {
            var child: View = parent.getChildAt(i)
            var parms: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
            var top: Int = child.bottom + parms.bottomMargin
            var bottom: Int = top + mDivider!!.intrinsicHeight
            mDivider.setBounds(left, right, top, bottom)
            mDivider.draw(c)
        }
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        drawlines(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
    }
}