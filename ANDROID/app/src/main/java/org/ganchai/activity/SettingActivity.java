package org.ganchai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jayfeng.lesscode.core.ViewLess;

import org.ganchai.R;
import org.ganchai.config.Config;

public class SettingActivity extends BaseActivity {

    private SwitchCompat openEnjoyModeSwitch;
    private RelativeLayout aboutContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initToolbar();

        openEnjoyModeSwitch = ViewLess.$(this, R.id.switch_open_enjoy_mode);
        openEnjoyModeSwitch.setChecked(Config.isOpenEnjoyMode() ? true : false);
        openEnjoyModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Config.setOpenEnjoyMode(isChecked ? true : false);
            }
        });

        aboutContainer = ViewLess.$(this, R.id.about);
        aboutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

}
