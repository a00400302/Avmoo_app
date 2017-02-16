package pw.Avmo.bean;

import android.graphics.Bitmap;

/**
 * Created by bee on 2016/9/18.
 */
public class Allbean {
    private String title;
    private String fanhao;
    private Bitmap Imgurl;
    private String time;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Allbean() {
    }


    public String getFanhao() {
        return fanhao;
    }

    public void setFanhao(String fanhao) {
        this.fanhao = fanhao;
    }

    public Bitmap getImgurl() {
        return Imgurl;
    }



    public void setImgurl(Bitmap imgurl) {
        Imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
