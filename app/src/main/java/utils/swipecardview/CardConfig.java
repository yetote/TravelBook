package utils.swipecardview;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by TF on 2017/4/6.
 */

public class CardConfig {
    public static int MAX_SHOW_COUNT;
    public static float SCALE_GAP;
    public static int TRANS_Y_GAP;
    public static int ROATION_X;

    public static void initConfig(Context context) {
        MAX_SHOW_COUNT = 5;
        SCALE_GAP = 0.05f;
        ROATION_X = 30;
        TRANS_Y_GAP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, context.getResources().getDisplayMetrics());
    }
}
