package com.maedanoma.mostcoolrealminesweeper.boundary;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maedanoma.mostcoolrealminesweeper.R;

/**
 * @author mmaeda
 */
public class CommonDialog extends Dialog {
    private final Button mButton;
    private CommonDialog(@NonNull Context context, @IdRes int titleId) {
        super(context);
        setContentView(R.layout.common_dialog);
        ((TextView) findViewById(R.id.title)).setText(titleId);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(v -> dismiss());
    }

    private void setButton(@IdRes int buttonTitle, Runnable task) {
        mButton.setText(buttonTitle);
        mButton.setOnClickListener(v -> {
            task.run();
            dismiss();
        });
    }

    private void setButton(@IdRes int buttonTitle) {
        mButton.setText(buttonTitle);
    }

    public static class Builder {
        private final CommonDialog mDialog;

        public Builder(@NonNull Context context, @IdRes int titleId) {
            mDialog = new CommonDialog(context, titleId);
        }

        public Builder button(@IdRes int buttonTitle, Runnable task) {
            mDialog.setButton(buttonTitle, task);
            return this;
        }

        public Builder button(@IdRes int buttonTitle) {
            mDialog.setButton(buttonTitle);
            return this;
        }

        public CommonDialog build() {
            return mDialog;
        }
    }
}
