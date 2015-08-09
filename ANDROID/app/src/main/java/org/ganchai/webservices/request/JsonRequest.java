package org.ganchai.webservices.request;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedContent;
import com.google.gson.Gson;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import java.io.IOException;
import java.util.HashMap;

public class JsonRequest<T> extends GoogleHttpClientSpiceRequest<T> {

    String url = null;
    HashMap<String, String > postParameters;

    public JsonRequest(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T loadDataFromNetwork() throws Exception {

        HttpRequest request = null;
        GenericUrl genericUrl = new GenericUrl(url);

        if (postParameters == null) {
            request = buildGetRequest(genericUrl);
        } else {
            HttpContent content = new UrlEncodedContent(postParameters);
            request = buildPostRequest(genericUrl, content);
        }

        return new Gson().fromJson(request.execute().parseAsString(), getResultType());
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
}
