package pw.Avmo.bean;

import android.graphics.Bitmap;

import java.net.URL;

/**
 * Created by Administrator on 2017/2/16.
 */

public class YulantuBean {
    URL Dayulantu;
    Bitmap Xiaoyulantu;


    public void setDayulantu(URL dayulantu) {
        Dayulantu = dayulantu;
    }

    public void setXiaoyulantu(Bitmap xiaoyulantu) {
        Xiaoyulantu = xiaoyulantu;
    }

    public URL getDayulantu() {

        return Dayulantu;
    }

    public Bitmap getXiaoyulantu() {
        return Xiaoyulantu;
    }
}
