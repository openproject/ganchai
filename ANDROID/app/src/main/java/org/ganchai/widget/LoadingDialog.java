package org.ganchai.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.TextView;

import com.jayfeng.lesscode.core.DisplayLess;

import org.ganchai.R;

public class LoadingDialog extends Dialog {

    private TextView loadingText;

    public LoadingDialog(Context context) {
        this(context, R.style.Theme_LoadingDialog);
        init();
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        setContentView(R.layout.widget_dialog_loading);
        loadingText = (TextView) findViewById(R.id.text);
    }

    public void setLoadingText(String text) {
        loadingText.setText(text);
    }

    @Override
    public void show() {
        super.show();
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = DisplayLess.$dp2px(160);
        lp.height = DisplayLess.$dp2px(160);
        getWindow().setAttributes(lp);
    }
}
