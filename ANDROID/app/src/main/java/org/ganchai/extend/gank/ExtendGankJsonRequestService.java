package org.ganchai.extend.gank;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

public class ExtendGankJsonRequestService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(ExtendGankJsonInterface.class);
    }

    @Override
    protected String getServerUrl() {
        return "http://gank.avosapps.com/api";
    }
}
