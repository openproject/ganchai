package org.ganchai.webservices.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import org.ganchai.webservices.JsonInterface;
import org.ganchai.webservices.json.DigestJson;

public class DigestListRequest extends RetrofitSpiceRequest<DigestJson, JsonInterface> {

    public DigestListRequest() {
        super(DigestJson.class, JsonInterface.class);
    }

    @Override
    public DigestJson loadDataFromNetwork() throws Exception {
        return getService().getDigestList(1, 50);
    }
}
