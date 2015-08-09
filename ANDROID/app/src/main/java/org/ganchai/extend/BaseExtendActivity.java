package org.ganchai.extend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.octo.android.robospice.SpiceManager;

import org.ganchai.R;
import org.ganchai.activity.BaseActivity;
import org.ganchai.extend.gank.ExtendGankJsonRequestService;

public class BaseExtendActivity extends BaseActivity {

    public static final String KEY_TITLE = "title";

    protected SpiceManager extendSpiceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_extend);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base_extend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setExtendSpiceManager(SpiceManager spiceManager) {
        this.extendSpiceManager = spiceManager;
    }

    @Override
    protected void onStart() {
        if (extendSpiceManager != null) {
            extendSpiceManager.start(this);
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (extendSpiceManager != null) {
            extendSpiceManager.shouldStop();
        }
        super.onStop();
    }
}
