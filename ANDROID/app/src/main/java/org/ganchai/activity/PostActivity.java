package org.ganchai.activity;

import android.os.Bundle;

import org.ganchai.R;

public class PostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initToolbar();
    }
}
