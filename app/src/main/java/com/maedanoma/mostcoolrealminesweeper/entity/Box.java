package com.maedanoma.mostcoolrealminesweeper.entity;

import android.util.Log;
import android.widget.ImageView;

import com.maedanoma.mostcoolrealminesweeper.boundary.BoxView;
import com.maedanoma.mostcoolrealminesweeper.controller.BoxManager;

/**
 * マスです。
 * @author mmaeda
 */
public class Box {
    public static final String TAG = "Box";
    private final BoxManager mManager;
    private final BoxView mBoxView;
    public final int mRow;
    public final int mColumn;
    private final boolean mHasBomb;
    private int mAroundBombNumber;
    private boolean isFlag;
    private boolean isDug;

    public Box(BoxManager manager, ImageView boxView, int column, int row, boolean hasBomb) {
        mManager = manager;
        mHasBomb = hasBomb;
        mAroundBombNumber = 0;
        mColumn = column;
        mRow = row;
        mBoxView = (BoxView) boxView;
    }

    public void incrementAroundBombNumber() {
        Log.i(TAG, "increment. box[" + mColumn + "][" + mRow + "]");
        mAroundBombNumber++;
    }

    public boolean hasBomb() {
        return mHasBomb;
    }

    public void onItemClick(boolean isTapBox) {
        if (isFlag || isDug) return;
        if (mHasBomb) {
            if (!isTapBox) return;
            mBoxView.setBomb();
            mManager.notifyExplosion();
            isDug = true;
            return;
        }
        isDug = true;
        if (mAroundBombNumber != 0) {
            mBoxView.setNumber(mAroundBombNumber);
            return;
        }
        mBoxView.setEmpty();
        mManager.digAroundBox(mColumn, mRow);
    }

    public void onItemLongClick() {
        if (isDug) return;
        isFlag = !isFlag;
        if (isFlag) {
            mBoxView.setFlag();
            return;
        }
        mBoxView.unsetFlag();
    }
}
