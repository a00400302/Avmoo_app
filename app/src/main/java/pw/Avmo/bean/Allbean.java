package pw.Avmo.Bean;

/**
 * Created by bee on 2016/9/18.
 */
public class AllBean {
    private String title;
    private String fanhao;
    private String Imgurl;
    private String time;
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AllBean() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
