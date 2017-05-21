package com.asuscomm.zhbee.avmoo_app.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/16.
 */

public class YulantuBean implements Serializable {
    private String Dayulantu;
    private String Xiaoyulantu;


    public void setDayulantu(String dayulantu) {
        Dayulantu = dayulantu;
    }

    public void setXiaoyulantu(String xiaoyulantu) {
        Xiaoyulantu = xiaoyulantu;
    }

    public String getDayulantu() {

        return Dayulantu;
    }

    public String getXiaoyulantu() {
        return Xiaoyulantu;
    }
}
