package utils.ScrollRemoveRecyclerView;

import android.view.View;

/**
 * Created by TF on 2017/5/26.
 */

public interface OnItemClickListener {
    void onItemClick(View v, int position);
    void onDeleteClick(int position);
}
