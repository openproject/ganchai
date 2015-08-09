package org.ganchai.extend.gank;

import retrofit.http.GET;
import retrofit.http.Path;

public interface ExtendGankJsonInterface {

    @GET("/data/Android/{limit}/{page}")
    ExtendGankModelListJson getGankList(@Path("limit") int limit, @Path("page") int page);

}
