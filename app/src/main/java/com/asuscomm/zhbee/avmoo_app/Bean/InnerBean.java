package com.asuscomm.zhbee.avmoo_app.Bean;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/2/14.
 */

public class InnerBean {
    private String Title;
    private String Fenmian;
    private String Fanhao;
    private String Time;
    private HashMap<String,String> Xilie;
    private String LeiBit;
    private HashMap<String,String>  KaifaShang;
    private HashMap<String,String>  FaxingShang;
    private String HowLong;

    public InnerBean(){

    }

    public void setFenmian(String fenmian) {
        Fenmian = fenmian;
    }

    public void setFanhao(String fanhao) {
        Fanhao = fanhao;
    }

    public void setTitle(String title) {
        Title = title;
    }



    public void setTime(String time) {
        Time = time;
    }


    public void setHowLong(String howLong) {
        HowLong = howLong;
    }




    public String getFenmian() {
        return Fenmian;
    }

    public String getFanhao() {
        return Fanhao;
    }

    public String getTitle() {
        return Title;
    }


    public String getTime() {
        return Time;
    }


    public String getHowLong() {
        return HowLong;
    }

    public HashMap<String, String> getXilie() {
        return Xilie;
    }

    public void setXilie(HashMap<String, String> xilie) {
        Xilie = xilie;
    }

    public HashMap<String, String> getKaifaShang() {
        return KaifaShang;
    }

    public void setKaifaShang(HashMap<String, String> kaifaShang) {
        KaifaShang = kaifaShang;
    }

    public HashMap<String, String> getFaxingShang() {
        return FaxingShang;
    }

    public void setFaxingShang(HashMap<String, String> faxingShang) {
        FaxingShang = faxingShang;
    }

    public void setLeiBit(String leiBit) {
        LeiBit = leiBit;
    }

    public String getLeiBit() {

        return LeiBit;
    }
}
