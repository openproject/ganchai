package org.ganchai.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.octo.android.robospice.SpiceManager;
import com.umeng.analytics.MobclickAgent;

import org.ganchai.R;
import org.ganchai.service.HttpClientSpiceService;
import org.ganchai.widget.LoadingDialog;

public class BaseActivity extends AppCompatActivity {

    private SpiceManager spiceManager = new SpiceManager(HttpClientSpiceService.class);

    protected Toolbar toolbar;

    protected LoadingDialog loadingDialog;

    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (isToolbarCanBack()) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected void initLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        onBackPressed();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    protected void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    protected void showLoadingDialog(String loadingText) {
        if (loadingDialog != null) {
            loadingDialog.setLoadingText(loadingText);
        }

        if (loadingDialog != null && !loadingDialog.isShowing() && !isFinishing()) {
            loadingDialog.show();
        }
    }

    protected void cancelLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    public SpiceManager getSpiceManager() {
        return spiceManager;
    }

    protected boolean isToolbarCanBack() {
        return true;
    }

}
