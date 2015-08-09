package org.ganchai.extend.gank;

import java.util.List;

/**
 * {
 "error": false,
 "results": [
 {
 "url": "https://git.oschina.net/way/PinnedHeaderListView",
 "type": "Android",
 "desc": "Android Lollipop联系人之PinnedListView简单使用",
 "who": "有时放纵",
 "used": true,
 "objectId": "55c415c060b2d140ca882eac",
 "createdAt": "2015-08-07T02:19:44.342Z",
 "updatedAt": "2015-08-07T03:57:48.142Z"
 },
 {
 "url": "https://github.com/tom91136/Akatsuki",
 "type": "Android",
 "desc": "使用注解来处理Activity的状态恢复",
 "who": "鲍永章",
 "used": true,
 "objectId": "55c3769660b2750766971ce6",
 "createdAt": "2015-08-06T15:00:38.350Z",
 "updatedAt": "2015-08-07T03:57:48.076Z"
 }
 ]
 }
 */
public class ExtendGankModelListJson {

    private boolean error;
    private List<ExtendGankModel> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ExtendGankModel> getResults() {
        return results;
    }

    public void setResults(List<ExtendGankModel> results) {
        this.results = results;
    }
}
