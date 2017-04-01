package pw.Avmo.Bean;

/**
 * Created by bee on 2016/9/29.
 */

public class TeacherBean {
    private String Name;
    private String Teacherimg;
    private String URL;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getTeacherimg() {
        return Teacherimg;
    }

    public void setTeacherimg(String teacherimg) {
        Teacherimg = teacherimg;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
