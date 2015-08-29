package org.ganchai.config;

public class WebConfig {

    public final static int STATUS_FAILURE = 0;
    public final static int STATUS_SUCCESS = 1;


    public static final String SERVER_API = "http://ganchai.sinaapp.com/api";

    public static final int DIGEST_TYPE_ANDROID = 1;
    public static final int DIGEST_TYPE_IOS = 2;
    public static final int DIGEST_TYPE_HTML5 = 3;
    public static final int DIGEST_TYPE_ANY = 4;

    /**
     * *********************************************************************************************
     * server api list
     * *********************************************************************************************
     */

    public static String getDigestList(int page, int size, int type) {
        return SERVER_API + "/digest?t=" + type + "&p=" + page + "&size=" + size;
    }
}
