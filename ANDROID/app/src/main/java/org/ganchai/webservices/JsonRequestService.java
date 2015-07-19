package org.ganchai.webservices;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import org.ganchai.config.WebConfig;

public class JsonRequestService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(JsonInterface.class);
    }

    @Override
    protected String getServerUrl() {
        return WebConfig.SERVER_API;
    }
}
