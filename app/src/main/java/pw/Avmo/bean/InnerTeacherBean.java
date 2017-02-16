package pw.Avmo.bean;

import android.graphics.Bitmap;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/16.
 */

public class InnerTeacherBean {
    String Teachername;
    Bitmap TeacherIMG;

    public void setTeachername(String teachername) {
        Teachername = teachername;
    }

    public void setTeacherIMG(Bitmap teacherIMG) {
        TeacherIMG = teacherIMG;
    }

    public String getTeachername() {

        return Teachername;
    }

    public Bitmap getTeacherIMG() {
        return TeacherIMG;
    }
}
