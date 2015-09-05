package org.ganchai.extend.html;

import android.text.TextUtils;

import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtendHtmlRequest extends GoogleHttpClientSpiceRequest<ExtendHtmlModelList> {

    private String url = null;

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

    public ExtendHtmlRequest() {
        super(ExtendHtmlModelList.class);
    }

    @Override
    public ExtendHtmlModelList loadDataFromNetwork() throws Exception {
        Document doc = Jsoup.connect(url).get();
        Elements blogList = doc.select(listSelectPath);
        ExtendHtmlModelList extendCsdnModels = new ExtendHtmlModelList();
        ExtendHtmlModel extendHtmlModel;
        for (int i = 0; i < blogList.size(); i++) {
            extendHtmlModel = new ExtendHtmlModel();
            Element blog = blogList.get(i);

            String title = TextUtils.isEmpty(titleSelectAttr) ? blog.select(titleSelectPath).text()
                    : blog.select(titleSelectPath).attr(titleSelectAttr);
            String summary = TextUtils.isEmpty(summarySelectAttr) ? blog.select(summarySelectPath).get(0).text()
                    : blog.select(summarySelectPath).get(0).attr(summarySelectAttr);
            String time = TextUtils.isEmpty(timeSelectAttr) ? blog.select(timeSelectPath).text()
                    : blog.select(timeSelectPath).attr(timeSelectAttr);
            String url = TextUtils.isEmpty(urlSelectAttr) ? blog.select(urlSelectPath).text()
                    : blog.select(urlSelectPath).attr(urlSelectAttr);
            if (!TextUtils.isEmpty(urlSelectPrefix)) {
                url = urlSelectPrefix + url;
            }
            extendHtmlModel.setTitle(title);
            extendHtmlModel.setSummary(summary);
            extendHtmlModel.setTime(time);
            extendHtmlModel.setUrl(url);
            extendCsdnModels.add(extendHtmlModel);
        }

        return extendCsdnModels;

    }

    public void setUrl(String url) {
        this.url = url;
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
