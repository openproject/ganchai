package org.ganchai.activity;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.jayfeng.lesscode.core.ViewLess;

import org.ganchai.R;
import org.ganchai.config.Config;

public class SettingActivity extends BaseActivity {

    private SwitchCompat openEnjoyModeSwitch;

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
    }

}
