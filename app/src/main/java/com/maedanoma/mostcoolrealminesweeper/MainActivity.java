package com.maedanoma.mostcoolrealminesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maedanoma.mostcoolrealminesweeper.boundary.MineSweeperView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MineSweeperView view = new MineSweeperView(this);
        setContentView(view);
        view.onCreate(6, 6);
    }
}
