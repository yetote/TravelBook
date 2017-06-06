package utils;

import java.io.Serializable;

/**
 * Created by TF on 2017/2/15.
 */

public class Contact implements Serializable {
    private String mName;
    private int mType;

    public Contact(String name, int type) {
        mName = name;
        mType = type;
    }

    public String getmName() {
        return mName;
    }

    public int getmType() {
        return mType;

    }
}
