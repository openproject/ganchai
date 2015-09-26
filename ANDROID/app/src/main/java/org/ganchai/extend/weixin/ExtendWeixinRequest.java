package org.ganchai.extend.weixin;

import android.text.TextUtils;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedContent;
import com.google.gson.Gson;
import com.jayfeng.lesscode.core.FileLess;
import com.jayfeng.lesscode.core.LogLess;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class ExtendWeixinRequest extends GoogleHttpClientSpiceRequest<ExtendWeixinModelList> {

    private String url = null;
    private HashMap<String, String > postParameters;

    private String listSelectPath;
    private String titleSelectPath;
    private String summarySelectPath;
    private String timeSelectPath;
    private String urlSelectPath;
    private String titleSelectAttr;
    private String summarySelectAttr;
    private String timeSelectAttr;
    private String urlSelectAttr;
    private String urlSelectPrefix;

    public ExtendWeixinRequest() {
        super(ExtendWeixinModelList.class);
    }

    @Override
    public ExtendWeixinModelList loadDataFromNetwork() throws Exception {

        HttpRequest request = null;
        GenericUrl genericUrl = new GenericUrl(url);

        if (postParameters == null) {
            request = buildGetRequest(genericUrl);
        } else {
            HttpContent content = new UrlEncodedContent(postParameters);
            request = buildPostRequest(genericUrl, content);
        }

        // format result to json
        String result = FileLess.$read(request.execute().getContent());
        result = result.replace("sogou.weixin.gzhcb(", "");
        int end = result.indexOf("]})");
        result = result.substring(0, end + 2);

        ExtendWeixinModelList extendCsdnModels = new ExtendWeixinModelList();
        ExtendWeixinModel extendWeixinModel;

        SogouWeixinGzhcb sogouWeixinGzhcb = new Gson().fromJson(result, SogouWeixinGzhcb.class);
        for (int i = 0; i < sogouWeixinGzhcb.getItems().size(); i++) {
            extendWeixinModel = new ExtendWeixinModel();
            String item = sogouWeixinGzhcb.getItems().get(i);
            Document doc = Jsoup.parse(item);
            extendWeixinModel.setTitle(doc.select("title").text());
            extendWeixinModel.setSummary(doc.select("content").text());
            extendWeixinModel.setTime(doc.select("date").text());
            extendWeixinModel.setUrl("http://weixin.sogou.com" + doc.select("url").text());

            extendCsdnModels.add(extendWeixinModel);
        }

        return extendCsdnModels;

    }

    private HttpRequest buildGetRequest(GenericUrl url) throws IOException {
        System.setProperty("http.keepAlive", "false");
        HttpRequest request = getHttpRequestFactory().buildGetRequest(url);
        customHttpHeader(request);
        return request;
    }

    private HttpRequest buildPostRequest(GenericUrl url, HttpContent content) throws IOException {
        System.setProperty("http.keepAlive", "false");
        HttpRequest request = getHttpRequestFactory().buildPostRequest(url, content);
        customHttpHeader(request);
        return request;
    }

    private void customHttpHeader(HttpRequest request) {
        request.getHeaders().setAcceptEncoding("gzip");
        request.getHeaders().set("Connection", "close");
        request.getHeaders().setAccept("text/html,application/xhtml+xml,application/xml,application/json");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPostParameters(HashMap<String, String> postParameters) {
        this.postParameters = postParameters;
    }

    public void setListSelectPath(String listSelectPath) {
        this.listSelectPath = listSelectPath;
    }

    public void setTitleSelectPath(String titleSelectPath) {
        this.titleSelectPath = titleSelectPath;
    }

    public void setSummarySelectPath(String summarySelectPath) {
        this.summarySelectPath = summarySelectPath;
    }

    public void setTimeSelectPath(String timeSelectPath) {
        this.timeSelectPath = timeSelectPath;
    }

    public void setUrlSelectPath(String urlSelectPath) {
        this.urlSelectPath = urlSelectPath;
    }

    public void setTitleSelectAttr(String titleSelectAttr) {
        this.titleSelectAttr = titleSelectAttr;
    }

    public void setSummarySelectAttr(String summarySelectAttr) {
        this.summarySelectAttr = summarySelectAttr;
    }

    public void setTimeSelectAttr(String timeSelectAttr) {
        this.timeSelectAttr = timeSelectAttr;
    }

    public void setUrlSelectAttr(String urlSelectAttr) {
        this.urlSelectAttr = urlSelectAttr;
    }

    public void setUrlSelectPrefix(String urlSelectPrefix) {
        this.urlSelectPrefix = urlSelectPrefix;
    }
}
