package com.maedanoma.mostcoolrealminesweeper.boundary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IdRes;
import android.util.Log;
import android.widget.ImageView;

import com.maedanoma.mostcoolrealminesweeper.R;

/**
 * @author mmaeda
 */
@SuppressLint("AppCompatCustomView")
public class BoxView extends ImageView {
    enum Number {
        ONE(1, R.drawable.one),
        TWO(2, R.drawable.two),
        THREE(3, R.drawable.three),
        FOUR(4, R.drawable.four),
        FIVE(5, R.drawable.five),
        SIX(6, R.drawable.six),
        SEVEN(7, R.drawable.seven),
        EIGHT(8, R.drawable.eight);

        private final int mNumber;
        private final int mNumberId;

        Number(int number, int id) {
            mNumber = number;
            mNumberId = id;
        }

        static int dispatch(int number) {
            for (Number type : Number.values()) {
                if (type.mNumber != number) continue;
                return type.mNumberId;
            }
            throw new IllegalStateException("unexpected number. number = " + number);
        }
    }
    public static final String TAG = "BoxView";
    private int mCurrentBoxId;

    public BoxView(Context context) {
        super(context);
        mCurrentBoxId = R.drawable.box_default;
    }

    public void setFlag() {
        Log.d(TAG, "setFlag");
        mCurrentBoxId = R.drawable.flag;
    }

    public void unsetFlag() {
        Log.d(TAG, "unsetFlag");
        mCurrentBoxId = R.drawable.box_default;
    }

    public void setBomb() {
        Log.d(TAG, "setBomb");
        mCurrentBoxId = R.drawable.bomb;
    }

    public void setEmpty() {
        Log.d(TAG, "setEmpty");
        mCurrentBoxId = R.drawable.empty;
    }

    public void setNumber(int number) {
        Log.d(TAG, "setNumber");
        mCurrentBoxId = Number.dispatch(number);
    }

    @IdRes
    public int getCurrentBoxId() {
        return mCurrentBoxId;
    }
}