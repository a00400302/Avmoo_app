package com.asuscomm.zhbee.avmoo_app.Bean;

import java.io.Serializable;

/**
 * Created by bee on 2017/5/14 0014.
 */
public class AllBean implements Serializable{
    private String title;
    private String fanhao;
    private String Imgurl;
    private String time;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFanhao() {
        return fanhao;
    }

    public void setFanhao(String fanhao) {
        this.fanhao = fanhao;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String imgurl) {
        Imgurl = imgurl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
