package com.asuscomm.zhbee.avmoo_app;

import android.os.Build;
import android.support.annotation.RequiresApi;

import android.util.Log;
import com.asuscomm.zhbee.avmoo_app.Bean.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bee on 2016/9/19.
 */
public class Source {
    public Source() {
    }

    public static ArrayList<AllBean> getAllBean(Document doc) {
        doc = Jsoup.parse(doc.html());
        List<String> urllist = new ArrayList<>();
        Elements elementsByClass = doc.select("a.movie-box > div.photo-frame").select("img");
        Elements elements = doc.select("div > a.movie-box ");
        for (Element e : elements) {
            urllist.add(e.attr("href"));
        }
        List<String> titlearry = new ArrayList<>();
        for (Element asdf : elementsByClass) {
            titlearry.add(asdf.attr("title"));

        }
        List<String> fanhaoArry = new ArrayList<>();
        List<String> timeArry = new ArrayList<>();
        int count = 0;
        Elements el = doc.select("span > date");
        for (Element asdf : el) {
            count += 1;
            if (count % 2 == 1) {
                fanhaoArry.add(asdf.text());
            } else if (count % 2 == 0) {
                timeArry.add(asdf.text());
            }
        }
        List<String> urlarry = new ArrayList<>();

        for (Element e : elementsByClass) {
            urlarry.add(e.attr("src"));
        }
        ArrayList<AllBean> list = new ArrayList<>();
        if (titlearry.size() == 0) {
            return list;
        } else {
            for (int i = 0; i < titlearry.size(); i++) {
                AllBean bean = new AllBean();
                bean.setFanhao(fanhaoArry.get(i));
                bean.setImgurl(urlarry.get(i));
                bean.setTime(timeArry.get(i));
                bean.setTitle(titlearry.get(i));
                bean.setUrl(urllist.get(i));
                list.add(bean);
            }
            return list;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static InnerBean getInnerName(Document document) {
        String Title = null;
        String Fenmian;
        String Fanhao;
        String Time = null;
        HashMap<String, String> KaifaShang = new HashMap<>();
        HashMap<String, String> Xilie = new HashMap<>();
        HashMap<String, String> FaxingShang = new HashMap<>();
        String LeiBit = "类别: ";
        String HowLong = null;
        Elements Titele = document.select("div >  h3");
        for (Element f : Titele) {
            Title = f.text();
        }
        Elements FengMian = document.select("a.bigImage");

        Elements FanHao = document.select("p > span");
        Fanhao = "番号:" + FanHao.get(1).text();
        Pattern tp = Pattern.compile("發行日期:</span> (.*?)</p>");
        String doc = document.toString();
        Matcher m = tp.matcher(doc);
        if (m.find()) {
            Time = "发行时间:" + m.group(1);
        }
        Matcher hm = Pattern.compile("長度:</span> (.*?)</p>").matcher(doc);
        if (hm.find()) {
            HowLong = "长度:" + hm.group(1);
        }
        Elements dd = document.select("div > p ");
        for (int i = 0; i < dd.size(); i++) {
            Element element = dd.get(i);
            switch (element.select("span").text()) {
                case "製作商:":
                    KaifaShang.put("name", "製作商: " + element.select("a").text());
                    KaifaShang.put("url", element.select("a").attr("herf"));
                    break;
                case "發行商:":
                    FaxingShang.put("name", "發行商: " + element.select("a").text());
                    FaxingShang.put("url", element.select("a").attr("href"));
                    break;
                case "系列:":
                    Xilie.put("name", "系列: " + element.select("a").text());
                    Xilie.put("url", element.select("a").attr("href"));
                    break;
                default:
                    break;
            }

        }

        Elements gg = document.select("span > a");
        for (Element e : gg) {
            if (e.attr("href").contains("genre")) {
                LeiBit = LeiBit + " " + e.text();
            }

        }
        Fenmian = FengMian.get(0).attr("href");

        InnerBean in = new InnerBean();
        in.setFenmian(Fenmian);
        in.setFanhao(Fanhao);
        in.setFaxingShang(FaxingShang);
        in.setFenmian(Fenmian);
        in.setHowLong(HowLong);
        in.setKaifaShang(KaifaShang);
        in.setLeiBit(LeiBit);
        in.setTime(Time);
        in.setTitle(Title);
        in.setXilie(Xilie);


        return in;
    }


    //List<Bitmap> XiaojyuLantu;
    //List<Bitmap> DajyuLantu;

    public static List<TeacherBean> getTeacherIMG(Document document, int Type) {
        List<TeacherBean> list = new ArrayList<>();
        if (Type == 0) {
            Elements elements = document.select("#waterfall > div ");
            for (Element e : elements) {
                TeacherBean bean = new TeacherBean();
                bean.setName(e.select(" a > div> img").attr("title"));
                bean.setTeacherimg(e.select("div > img").attr("src"));
                bean.setURL(e.select("a").attr("href"));
                list.add(bean);
            }
        } else {
            Elements elements = document.select("#avatar-waterfall > a ");
            for (Element e : elements) {
                TeacherBean bean = new TeacherBean();
                bean.setName(e.select("div > img").attr("title"));
                bean.setTeacherimg(e.select("div > img").attr("src"));
                bean.setURL(e.attr("href"));
                list.add(bean);
            }

        }


        return list;
    }

    public static ArrayList<YulantuBean> getYulanIMG(Document document) {
        ArrayList<YulantuBean> list = new ArrayList<>();
        Elements elements = document.select("a.sample-box");
        Matcher TIM = Pattern.compile("href=\"https://pics.dmm.co.jp/digital/video/(.+?)\\/").matcher(document.toString());
        for (int i = 0; i < elements.size()& TIM.find(); i++) {
            YulantuBean bean = new YulantuBean();
            String da = "https://jp.netcdn.space/digital/video/" + TIM.group(1) + "/" + TIM.group(1) + "jp-" + (i +1) + ".jpg";
            bean.setDayulantu(da);
            bean.setXiaoyulantu(elements.get(i).select("div > img").attr("src"));
            list.add(bean);
        }

        return list;
    }

    public static ArrayList<String> getTeacherInformation(Document document) {
        document = Jsoup.parse(document.toString());
        ArrayList<String> list = new ArrayList<>();
        Elements elements = document.select("div.avatar-box > div.photo-info > p");
        for (Element e : elements) {
            list.add(e.text());
        }

        return list;
    }


    public static List<CategoryBean> getCategory(Document document) {
        document = Jsoup.parse(document.html());
        List<CategoryBean> list = new ArrayList<>();
        Elements elementsByClass = document.select("div > a").select("a.col-lg-2");
        for (Element e : elementsByClass) {
            CategoryBean bean = new CategoryBean();
            bean.setName(e.text());
            bean.setUrl(e.attr("href"));
            list.add(bean);
        }
        return list;
    }

}

