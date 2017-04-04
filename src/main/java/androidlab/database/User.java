package androidlab.database;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Mühlenstädt on 06.12.2016.
 */
@XmlRootElement
public class User {

    public int userid;
    public String username;

    public User() {
        userid = -1;
        username = "y0000000";
    }

    public User(int userid, String username) {
        this.userid = userid;
        this.username = username;
    }
}
