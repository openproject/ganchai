package org.ganchai.webservices;

import org.ganchai.webservices.json.DigestJson;

import retrofit.http.GET;
import retrofit.http.Query;

public interface JsonInterface {

    @GET("/digest")
    DigestJson getDigestList(@Query("p") int page, @Query("size") int size);


    @GET("/digest?p=1&size=10")
    DigestJson getDigestList1();
}
