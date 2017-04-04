package androidlab.database;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Post datatype needed for json.
 *
 * @author Mühlenstädt
 */
@XmlRootElement
public class Post {

    public int lectureId;
    public int userId;
    public String username;
    public int postId;
    public String content;
    public int likes;

    public Post() {
        lectureId = 0;
        userId = 0;
        postId = 0;
        content = "Hallo Review";
        likes = 2;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String username) {
        this.username = username;
    }
}
