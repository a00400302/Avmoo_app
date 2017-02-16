package pw.Avmo.bean;

import android.graphics.Bitmap;

/**
 * Created by bee on 2016/9/29.
 */

public class Teacherbean {
    String Name;
    Bitmap Teacherimg;

    public Bitmap getTeacherimg() {
        return Teacherimg;
    }

    public void setTeacherimg(Bitmap teacherimg) {
        Teacherimg = teacherimg;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
