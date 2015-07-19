package org.ganchai.activity;

import android.support.v7.app.AppCompatActivity;

import com.octo.android.robospice.SpiceManager;

import org.ganchai.webservices.JsonRequestService;

public class BaseActivity extends AppCompatActivity {

    private SpiceManager spiceManager = new SpiceManager(JsonRequestService.class);

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

}
