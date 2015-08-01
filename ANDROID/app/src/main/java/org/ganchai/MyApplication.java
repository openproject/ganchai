package org.ganchai;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.ganchai.config.Config;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Config.setContext(this);

        Fresco.initialize(this);
    }
}
