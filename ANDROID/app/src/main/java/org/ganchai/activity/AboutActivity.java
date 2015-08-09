package org.ganchai.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.ganchai.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initToolbar();
    }
}
