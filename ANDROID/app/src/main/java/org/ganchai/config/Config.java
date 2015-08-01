package org.ganchai.config;

import android.content.Context;

import com.jayfeng.lesscode.core.SharedPreferenceLess;

public class Config {

    private static final String PREFERENCE_KEY_OPEN_ENJOY_MODE = "open_enjoy_mode";


    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context sContext) {
        Config.sContext = sContext;
    }

    public static boolean isOpenEnjoyMode() {
        return "1".equals(SharedPreferenceLess.$get(sContext, PREFERENCE_KEY_OPEN_ENJOY_MODE, "0") + "");
    }

    public static void setOpenEnjoyMode(boolean openEnjoyMode) {
        String result = openEnjoyMode ? "1" : "0";
        SharedPreferenceLess.$put(sContext, PREFERENCE_KEY_OPEN_ENJOY_MODE, result);
    }
}
