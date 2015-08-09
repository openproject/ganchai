package org.ganchai.service;

import com.octo.android.robospice.GsonGoogleHttpClientSpiceService;

public class HttpClientSpiceService extends GsonGoogleHttpClientSpiceService {

    @Override
    public int getThreadCount() {
        return 4;
    }

}
