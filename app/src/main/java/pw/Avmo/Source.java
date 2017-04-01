package pw.Avmo;

import android.os.Build;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pw.Avmo.Bean.AllBean;
import pw.Avmo.Bean.CategoryBean;
import pw.Avmo.Bean.InnerBean;
import pw.Avmo.Bean.TeacherInfomationBean;
import pw.Avmo.Bean.TeacherBean;
import pw.Avmo.Bean.YulantuBean;
import pw.Avmo.Fragment.CategoryFragment;

/**
 * Created by bee on 2016/9/19.
 */
public class Source {
    public Source() {
    }

    public static List<AllBean> getAllBean(Document doc) {
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
        List<AllBean> list = new ArrayList<>();
        if (titlearry.size() == 0 ){
            return list;
        }else {
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


//    public static List<String> getTeacherName(Document document) {
//        ArrayList<String> Name = new ArrayList<>();
//        Elements elements = document.select("div > span");
//        for (int i = 0; i < elements.size(); i++) {
//            Name.add(elements.get(i).text());
//        }
//        return Name;
//    }
//    public static ArrayList<Bitmap> getTeacherUrl (Document document){
//        ArrayList<Bitmap> TeacherIMG = new ArrayList<>();
//        Elements element = document.select("img");
//        for (Element asdf : element){
//            Bitmap mBitmap;
//            URL  url = null;
//            try {
//                url = new URL(asdf.attr("src"));
//                Log.d("TeacherBean", "getTeacherlistIMG: " + url.toString());
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


    public static InnerBean getInnerName(Document document) {
        String Fenmian;
        String Fanhao;
        String Title = null;
        String KaifaShang;
        String Xilie;
        String Time = null;
        String Daoyan;
        String HowLong = null;
        String FaxingShang;
        String LeiBit = "";
        InnerBean innerbean = new InnerBean();
        Elements Titele = document.select("div >  h3");
        for (Element f : Titele) {
            Title = f.text();
        }
        Elements FengMian = document.select("a > img");

        Elements FanHao = document.select("p > span");
        Fanhao = FanHao.get(1).text();
        Pattern tp = Pattern.compile("发行时间:</span> (.*?)</p>");
        String doc = document.toString();
        Matcher m = tp.matcher(doc);
        if (m.find()) {
            Time = m.group(1);
        }
        Matcher hm = Pattern.compile("长度:</span> (.*?)</p>").matcher(doc);
        if (hm.find()) {
            HowLong = hm.group(1);
        }
        Elements dd = document.select("div > p > a");
        Daoyan = dd.get(0).text();
        KaifaShang = dd.get(1).text();
        FaxingShang = dd.get(2).text();
        Xilie = dd.get(3).text();
        Elements gg = document.select("span > a");
        for (Element e : gg) {
            LeiBit = LeiBit + " " + e.text();
        }
        Fenmian = FengMian.get(0).attr("src");
        Log.d("Source", Fenmian);

        InnerBean in = new InnerBean();
        in.setFenmian(Fenmian);
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


        return in;
    }


    //List<Bitmap> XiaojyuLantu;
    //List<Bitmap> DajyuLantu;

    public static List<TeacherBean> getTeacherIMG(Document document, int Type) {
        List<TeacherBean> list = new ArrayList<>();
        String doc = document.toString();
        Matcher TM = Pattern.compile("<span>(.+?)<\\/span>").matcher(doc);
        Matcher TIM = Pattern.compile("src=\"(.+?)\" title=\"\">").matcher(doc);
        Matcher URLS = Pattern.compile("text-center\" href=\"(.+?)\">").matcher(doc);
        for (int i; TM.find() | TIM.find() | URLS.find(); ) {
            TeacherBean bean = new TeacherBean();
            bean.setName(TM.group(1));
            if (Type == 0) {
                bean.setURL(URLS.group(1));
            } else {
                bean.setURL(document.getElementsByClass("avatar-box").attr("href"));
            }
            bean.setTeacherimg(TIM.group(1));
            list.add(bean);
        }
        return list;
    }

    public static List<YulantuBean> getYulanIMG(Document document) {
        List<YulantuBean> list = new ArrayList<>();
        String doc = document.toString();
        Matcher TIM = Pattern.compile("<img src=\"https://jp.netcdn.space/digital/video/(.+?)\\/").matcher(doc);
        for (int i = 1; TIM.find() & i <= 10; i++) {
            YulantuBean bean = new YulantuBean();
            String xiao = "https://jp.netcdn.space/digital/video/" + TIM.group(1) + "/" + TIM.group(1) + "-" + i + ".jpg";
            String da = "https://jp.netcdn.space/digital/video/" + TIM.group(1) + "/" + TIM.group(1) + "jp-" + i + ".jpg";
            bean.setXiaoyulantu(xiao);
            bean.setDayulantu(da);
            list.add(bean);
        }
        return list;
    }

    public  static TeacherInfomationBean getTeacherInformation(Document document) {
        document = Jsoup.parse(document.toString());
        TeacherInfomationBean teacherInfomationBean = new TeacherInfomationBean();
        Elements elements = document.select("div.photo-info > p");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(elements.get(0).text(), "更多无码影片")) {
                teacherInfomationBean.setCup("没有详细信息");
            } else {
                try {
                    teacherInfomationBean.setBirthday(Objects.equals(elements.get(0).text(), "更多无码影片") ? null : elements.get(0).text() == null ? null : elements.get(0).text());
                    teacherInfomationBean.setAge(Objects.equals(elements.get(1).text(), "更多无码影片") ? null : elements.get(1).text() == null ? null : elements.get(1).text());
                    teacherInfomationBean.setHeight(Objects.equals(elements.get(2).text(), "更多无码影片") ? null : elements.get(2).text() == null ? null : elements.get(2).text());
                    teacherInfomationBean.setCup(Objects.equals(elements.get(3).text(), "更多无码影片") ? null : elements.get(3).text() == null ? null : elements.get(3).text());
                    teacherInfomationBean.setBust(Objects.equals(elements.get(4).text(), "更多无码影片") ? null : elements.get(4).text() == null ? null : elements.get(4).text());
                    teacherInfomationBean.setWaist(Objects.equals(elements.get(5).text(), "更多无码影片") ? null : elements.get(5).text() == null ? null : elements.get(5).text());
                    teacherInfomationBean.setHip(Objects.equals(elements.get(6).text(), "更多无码影片") ? null : elements.get(6).text() == null ? null : elements.get(6).text());
                    teacherInfomationBean.setPlaceToBirth(Objects.equals(elements.get(7).text(), "更多无码影片") ? null : elements.get(7).text() == null ? null : elements.get(7).text());
                    teacherInfomationBean.setHobbies(Objects.equals(elements.get(8).text(), "更多无码影片") ? null : elements.get(8).text() == null ? null : elements.get(8).text());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return teacherInfomationBean;
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
