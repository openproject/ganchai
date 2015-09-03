package org.ganchai;

import android.app.Application;
import android.os.Build;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jayfeng.lesscode.core.$;

import org.ganchai.config.Config;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Config.setContext(this);

        Fresco.initialize(this);

        $.getInstance()
                .context(this)
                .log(BuildConfig.DEBUG, "feng")
                .build();
    }
}
