package com.maedanoma.mostcoolrealminesweeper.boundary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maedanoma.mostcoolrealminesweeper.R;
import com.maedanoma.mostcoolrealminesweeper.controller.BoxManager;
import com.maedanoma.mostcoolrealminesweeper.entity.Box;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mmaeda
 */
public class MineSweeperView extends RelativeLayout {
    private static final String TAG = "MineView";
    private BoxManager mManager;
    private Context mDialogContext;

    public MineSweeperView(Context context) {
        super(context.getApplicationContext());
        mDialogContext = context;
    }

    public void onCreate(int columnSize, int rowSize) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.mine_sweeper_main, this, false);
        addView(root);

        GridView grid = (GridView) root.findViewById(R.id.grid_view);

        CommonDialog commonDialog = new CommonDialog.Builder(mDialogContext, R.string.reset_dialog_title)
                .button(R.string.reset_game, () -> prepare(grid, rowSize, columnSize)).build();
        mManager = new BoxManager(rowSize, columnSize, commonDialog);

        grid.setNumColumns(rowSize);
        grid.setOnItemClickListener((parent, view, position, id) -> {
            mManager.onItemClick(position, false);
            grid.invalidateViews();
        });
        grid.setOnItemLongClickListener((parent, view, position, id) -> {
            mManager.onItemClick(position, true);
            grid.invalidateViews();
            return true;
        });

        prepare(grid, columnSize, rowSize);

        // スタートダイアログ表示
        CommonDialog startDialog =
                new CommonDialog.Builder(mDialogContext, R.string.start_dialog_title).button(R.string.start_game).build();
        startDialog.show();
    }

    private void prepare(GridView grid, int columnSize, int rowSize) {
        ArrayList<BoxView> items = new ArrayList<>();
        for (int i = 0; i < rowSize * columnSize; i++) {
            items.add(new BoxView(getContext()));
        }
        BoxViewAdapter adapter = new BoxViewAdapter(getContext(), R.layout.box, items);
        grid.setAdapter(adapter);
        mManager.init(items);
    }

    private static class BoxViewAdapter extends BaseAdapter {
        private final List<BoxView> mBoxList;
        private final int mResourceId;
        private final LayoutInflater mInflater;

        public BoxViewAdapter(@NonNull Context context, int resource, List<BoxView> list) {
            super();
            mResourceId = resource;
            mBoxList = list;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mBoxList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView");
            ImageView imageView;

            if(convertView == null){
                convertView = mInflater.inflate(mResourceId, parent, false);
                imageView = convertView.findViewById(R.id.box_view);
            } else {
                imageView = (ImageView) convertView;
            }

            Log.d(TAG, "set current id");
            imageView.setBackgroundResource(mBoxList.get(position).getCurrentBoxId());
            return imageView;
        }
    }
}