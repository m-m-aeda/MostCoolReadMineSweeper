package com.maedanoma.mostcoolrealminesweeper.controller;

import android.util.Log;

import com.maedanoma.mostcoolrealminesweeper.boundary.BoxView;
import com.maedanoma.mostcoolrealminesweeper.boundary.CommonDialog;
import com.maedanoma.mostcoolrealminesweeper.entity.Box;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * マスを管理します。
 * @author mmaeda
 */
public class BoxManager {
    interface Task {
        void apply(Box box);
    }
    public static final String TAG = "BoxMngr";
    private static final int BOMB_RATIO = 5;
    private final int mMax_Random_Number;
    private final int mColumnSize;
    private final int mRowSize;
    private final Box[][] mBoxes;
    private Map<String, CommonDialog> mDialogs;
    private int mNonBombBoxCount;
    private int mDugBoxCount;

    public BoxManager(int columnSize, int rowSize, Map<String, CommonDialog> dialogs) {
        mColumnSize = columnSize;
        mRowSize = rowSize;
        mMax_Random_Number = (columnSize * rowSize) / BOMB_RATIO;
        mBoxes = new Box[mColumnSize][mRowSize];
        mDialogs = dialogs;
        mNonBombBoxCount = 0;
        mDugBoxCount = 0;
    }

    public void init(ArrayList<BoxView> items) {
        mDugBoxCount = 0;
        Random r = new Random();
        // MainSweeperViewとBoxViewの紐付、爆弾の用意
        for (int i = 0; i < mColumnSize; i++) {
            for (int j = 0; j < mRowSize; j++) {
                boolean isBomb = r.nextInt(mMax_Random_Number) == (mMax_Random_Number - 1);
                int index = (mRowSize * i) + j;
                mBoxes[i][j] = new Box(this, items.get(index), i, j, isBomb);
                Log.i(TAG, "box column = " + i + ", row = " + j + ", isBomb = " + isBomb);
            }
        }

        // 爆弾の周りのBoxに数字を設定する。
        mNonBombBoxCount = 0;
        for (int i = 0; i < mColumnSize; i++) {
            for (int j = 0; j < mRowSize; j++) {
                if (!mBoxes[i][j].hasBomb()) {
                    mNonBombBoxCount++;
                    continue;
                }
                Log.i(TAG, "bomb = box[" + i + "][" + j + "]");
                updateAroundBox(i, j, box -> {
//                    Log.i(TAG, "target = box[" + box.mColumn + "][" + box.mRow + "]");
                    box.incrementAroundBombNumber();
                });
            }
        }
    }


    public void digAroundBox(int column, int row) {
        if (++mDugBoxCount >= mNonBombBoxCount) {
            mDialogs.get(CommonDialog.COMPLETE).show();
        }
//        updateAroundBox(column, row, box -> box.onItemClick(false));
    }

    private void updateAroundBox(int column, int row, Task task) {
        int upperColumn = column - 1;
        int leftRow = row - 1;
        int underColumn = column + 1;
        int rightRow = row + 1;

        if (upperColumn >= 0) {
            // 左上
            if (leftRow >= 0) {
                task.apply(mBoxes[upperColumn][leftRow]);
            }
            // 上
            task.apply(mBoxes[upperColumn][row]);
            // 右上
            if (rightRow < mRowSize) {
                task.apply(mBoxes[upperColumn][rightRow]);
            }
        }
        // 左
        if (leftRow >= 0) {
            task.apply(mBoxes[column][leftRow]);
        }
        // じぶん
        // 右
        if (rightRow < mRowSize) {
            task.apply(mBoxes[column][rightRow]);
        }

        if (underColumn < mColumnSize) {
            // 左下
            if (leftRow >= 0) {
                task.apply(mBoxes[underColumn][leftRow]);
            }
            // 下
            task.apply(mBoxes[underColumn][row]);
            // 右上
            if (rightRow < mRowSize) {
                task.apply(mBoxes[underColumn][rightRow]);
            }
        }
    }

    public void notifyExplosion() {
        mDialogs.get(CommonDialog.RESET).show();
    }

    public boolean onItemClick(int position, boolean isLongClick) {
        Box box = mBoxes[position / mRowSize][position % mRowSize];
        Log.i(TAG, "onClick. box[" + position / mRowSize + "][" + position % mRowSize + "]");
        Log.i(TAG, "onClick. box[" + box.mColumn + "][" + box.mRow + "]");
        if (isLongClick) {
            box.onItemLongClick();
            return false;
        }
        box.onItemClick(true);
        return false;
    }
}
