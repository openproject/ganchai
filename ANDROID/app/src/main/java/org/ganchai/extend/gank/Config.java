package org.ganchai.extend.gank;

public class Config {

    public static String getGankList(int page, int size) {
        return "http://gank.avosapps.com/api" + "/data/Android/" + size + "/" + page;
    }
}
