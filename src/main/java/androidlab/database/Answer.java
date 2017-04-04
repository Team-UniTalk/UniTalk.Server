package androidlab.database;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Mühlenstädt on 06.12.2016.
 */
@XmlRootElement
public class Answer {

    public int id;
    public int postId;
    public int userId;
    public String username;
    public String content;

    public Answer() {
        id = 0;
        postId = 0;
        userId = 0;
        username = "y0000000";
        content = "Toller Test";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
