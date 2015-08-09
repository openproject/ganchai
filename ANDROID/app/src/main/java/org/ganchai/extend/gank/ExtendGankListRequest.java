package org.ganchai.extend.gank;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import org.ganchai.webservices.JsonInterface;
import org.ganchai.webservices.json.DigestJson;

public class ExtendGankListRequest extends RetrofitSpiceRequest<ExtendGankModelListJson, ExtendGankJsonInterface> {

    private int page;
    private int limit;

    public ExtendGankListRequest(int page, int limit) {
        super(ExtendGankModelListJson.class, ExtendGankJsonInterface.class);
        this.page = page;
        this.limit = limit;
    }

    @Override
    public ExtendGankModelListJson loadDataFromNetwork() throws Exception {
        return getService().getGankList(limit, page);
    }
}
