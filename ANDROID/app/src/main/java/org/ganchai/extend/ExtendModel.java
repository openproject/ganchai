package org.ganchai.extend;

public class ExtendModel {

    private String title;
    private String desc;
    private String homepage;
    private Class<?> intentClass;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Class<?> getIntentClass() {
        return intentClass;
    }

    public void setIntentClass(Class<?> intentClass) {
        this.intentClass = intentClass;
    }
}
