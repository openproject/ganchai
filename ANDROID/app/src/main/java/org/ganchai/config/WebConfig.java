package org.ganchai.config;

public class WebConfig {

    public final static int STATUS_FAILURE = 0;
    public final static int STATUS_SUCCESS = 1;


    public static final String SERVER_API = "http://ganchai.sinaapp.com/api";

    /**
     * *********************************************************************************************
     * server api list
     * *********************************************************************************************
     */

    public static String getDigestList(int page, int size) {
        return SERVER_API + "/digest?p=" + page + "&size=" + size;
    }
}
