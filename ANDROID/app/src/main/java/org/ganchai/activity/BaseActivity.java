package org.ganchai.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.octo.android.robospice.SpiceManager;

import org.ganchai.R;
import org.ganchai.webservices.JsonRequestService;

public class BaseActivity extends AppCompatActivity {

    private SpiceManager spiceManager = new SpiceManager(JsonRequestService.class);
    protected Toolbar toolbar;

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
