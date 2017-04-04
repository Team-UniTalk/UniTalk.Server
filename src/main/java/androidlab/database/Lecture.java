package androidlab.database;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Mühlenstädt on 06.12.2016.
 */
@XmlRootElement
public class Lecture {

    public int id;
    public String name;
    public int code;

    public Lecture() {
        id = 0;
        name = "Test Lecture";
        int code = 111;
    }

    public Lecture(int id, String name, int code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
