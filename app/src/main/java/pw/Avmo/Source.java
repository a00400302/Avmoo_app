package pw.Avmo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pw.Avmo.bean.InnerBean;
import pw.Avmo.bean.Teacherbean;
import pw.Avmo.bean.YulantuBean;

/**
 * Created by bee on 2016/9/19.
 */
public class Source {
    public Source(){
    }

    public  static ArrayList<String> getURL(Document doc){
        ArrayList<String> urllist = new ArrayList<>();
        Elements elements = doc.select("div > a.movie-box ");
        for (Element e : elements){
            Log.d("innerF", "getURL: " + e.attr("href"));
            urllist.add(e.attr("href"));
        }
        return urllist;

    }

    public static ArrayList<String> getTitle(Document Doc) {
        ArrayList<String> titlearry = new ArrayList<String>();
        Elements elements = Doc.select("img");
        for(Element asdf : elements){
            titlearry.add(asdf.attr("title"));
        }
        return titlearry;
    }
    public static ArrayList<String> getFanhao(Document Doc){
        ArrayList<String> fanhaoarry = new ArrayList<String>();
        int count = 0;
        Elements el = Doc.select("span > date");
        for (Element Fanhao:el){
            count += 1;
            if (count % 2 == 1){
                fanhaoarry.add(Fanhao.text());
            }
        }
        return fanhaoarry;
    }

    public  static ArrayList<String> getTime(Document Doc){
        ArrayList<String> Timearry = new ArrayList<String>();
        int count = 0;
        Elements el = Doc.select("span > date");
        for (Element Time:el){
            count += 1;
            if (count % 2 ==0){
                Timearry.add(Time.text());
            }
        }
        return Timearry;
    }

    public static ArrayList<String> getImgUrl(Document Doc){
        ArrayList<String> Urlarry = new ArrayList<>();
        Elements elements = Doc.select("img");

        for(Element asdf : elements) {
                String url =asdf.attr("src");
                Urlarry.add(url);
        }
        return Urlarry;
    }

    public static ArrayList<String> getTeacherName (Document document){
        ArrayList<String>  Name = new ArrayList<>();
        Elements elements =document.select("div > span");
        for(int i = 0;i < elements.size();i++){
            Name.add(elements.get(i).text());
        }
        return Name;
    }
//    public static ArrayList<Bitmap> getTeacherUrl (Document document){
//        ArrayList<Bitmap> TeacherIMG = new ArrayList<>();
//        Elements element = document.select("img");
//        for (Element asdf : element){
//            Bitmap mBitmap;
//            URL  url = null;
//            try {
//                url = new URL(asdf.attr("src"));
//                Log.d("Teacherbean", "getTeacherlistIMG: " + url.toString());
//                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//                InputStream is = connection.getInputStream();
//                mBitmap = BitmapFactory.decodeStream(is);
//                TeacherIMG.add(mBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return TeacherIMG;
//    }


    public static InnerBean getInnerName(Document document){
        Bitmap Fenmian = null;
        String Fanhao;
        String Title = null;
        String KaifaShang;
        String Xilie;
        String Time = null;
        String  Daoyan;
        String HowLong = null;
        String FaxingShang;
        String LeiBit = "";
        InnerBean innerbean = new InnerBean();
        Elements Titele = document.select("div >  h3");
        for (Element f : Titele){
            Title = f.text();
        }
        Elements FengMian = document.select("a > img");
        Bitmap mBitmap;
        URL  url = null;

        Elements FanHao = document.select("p > span");
        Fanhao =  FanHao.get(1).text();
        Pattern tp = Pattern.compile("发行时间:</span> (.*?)</p>");
        String doc = document.toString();
        Matcher m = tp.matcher(doc);
        if (m.find()){
            Time = m.group(1);
        }
        Matcher hm = Pattern.compile("长度:</span> (.*?)</p>").matcher(doc);
        if (hm.find()){
            HowLong =  hm.group(1);
        }
        Elements dd = document.select("div > p > a");
        Daoyan = dd.get(0).text();
        KaifaShang =  dd.get(1).text();
        FaxingShang = dd.get(2).text();
        Xilie =  dd.get(3).text();
        Elements gg = document.select("span > a");
        for ( Element e : gg){
            LeiBit = LeiBit +  " " + e.text();
        }

        try {
            url = new URL(FengMian.get(0).attr("src"));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream is = connection.getInputStream();
            Fenmian = BitmapFactory.decodeStream(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        InnerBean in = new InnerBean();
        in.setDaoyan(Daoyan);
        in.setFanhao(Fanhao);
        in.setFaxingShang(FaxingShang);
        in.setFenmian(Fenmian);
        in.setHowLong(HowLong);
        in.setKaifaShang(KaifaShang);
        in.setLeiBit(LeiBit);
        in.setTime(Time);
        in.setTitle(Title);
        in.setXilie(Xilie);


        return  in;
    }


    //List<Bitmap> XiaojyuLantu;
    //List<Bitmap> DajyuLantu;

    public  static List<Teacherbean>  getTeacherIMG(Document document){
        List<Teacherbean> list = new ArrayList<Teacherbean>();
        String doc = document.toString();
        Matcher TM = Pattern.compile("<span>(.+?)<\\/span>").matcher(doc);
        Matcher TIM = Pattern.compile("src=\"(.+?)\" title=\"\">").matcher(doc);

        for (int i ; TM.find()|TIM.find();){
            Teacherbean  bean = new Teacherbean();
            bean.setName(TM.group(1));
            bean.setTeacherimg(TIM.group(1));
            list.add(bean);
        }
        return list;
    }
    public  static  List<YulantuBean> getYulanIMG(Document document){
        List<YulantuBean> list = new ArrayList<YulantuBean>();
        String doc = document.toString();
        Matcher TIM = Pattern.compile("<img src=\"https://jp.netcdn.space/digital/video/(.+?)\\/").matcher(doc);
        for (int i = 1 ;TIM.find() & i <= 10;i++ ){
            YulantuBean bean = new YulantuBean();
                String xiao = "https://jp.netcdn.space/digital/video/" + TIM.group(1) + "/" + TIM.group(1) + "-" + i + ".jpg";
                String da = "https://jp.netcdn.space/digital/video/" + TIM.group(1) + "/" + TIM.group(1) + "jp-" + i + ".jpg";
                bean.setXiaoyulantu(xiao);
                bean.setDayulantu(da);
                list.add(bean);
        }
        return  list;
    }



}
