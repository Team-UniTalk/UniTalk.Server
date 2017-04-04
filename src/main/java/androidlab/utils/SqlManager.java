package androidlab.utils;

import androidlab.database.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static androidlab.Main.mServer;

/**
 * The SQL Manager.
 * @author Muehlenstaedt
 */
public class SqlManager {

    static boolean connectionStatus = false;
    static Connection connection = null;
    static Statement statement;

    /**
     * Method to connect with sql server.
     */
    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/dba?serverTimezone=UTC",
                user = "root",
                password = "dahskufhjio32knklhioh(Z(/H§I$H/(§Z$IUHDughiuhsjkfdh";
        try {
            //connection = DriverManager.getConnection("jdbc:mysql://ylhost.de:3306/squirrels");
            connection = DriverManager.getConnection(url, user, password);
            connectionStatus = true;
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
        } catch (SQLException ex) {
            connectionStatus = false;
            System.out.println(ex.getMessage());
            try { //local
                connection = DriverManager.getConnection(url, user, "root");
                connectionStatus = true;
                statement = connection.createStatement();
                statement.setQueryTimeout(30);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to disconnect with sql server.
     */
    public static void disconnect() {
        if (connectionStatus) {
            try {
                statement.close();
                connection.close();
                connectionStatus = false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get a user by his username.
     *
     * @param username the username.
     * @return the wanted user.
     */
    public static User getUserByUsername(String username) {
        if (connectionStatus) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet =
                        statement.executeQuery("SELECT u.id, u.username FROM users AS u " +
                                "WHERE u.username = \"" + username + "\"");
                while (resultSet.next()) {
                    return new User(resultSet.getInt(1), resultSet.getString(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static User getUserByID(int userId) {
        if (connectionStatus) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet =
                        statement.executeQuery("SELECT u.id, u.username FROM users AS u WHERE u.id=" + userId);
                while (resultSet.next())
                    return new User(resultSet.getInt(1), resultSet.getString(2));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Lecture getLectureByID(int lectureId) {
        if (connectionStatus) {
            List<Post> posts;
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet =
                        statement.executeQuery("SELECT l.id, l.title, l.code " +
                                "FROM lecture AS l " +
                                "WHERE l.id=" + lectureId);
                return new Lecture(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Post> getPostsByLecture(int code) {
        if (connectionStatus) {
            List<Post> posts = new ArrayList<>();
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("" +
                        "SELECT p.lectureid, p.userid, p.id, u.username, p.content, p.likes FROM post AS p " +
                        "JOIN lecture AS l ON l.id = p.lectureid " +
                        "JOIN users AS u ON u.id = p.userid " +
                        "WHERE l.code=" + code +
                        " ORDER BY  p.likes DESC");
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setPostId(resultSet.getInt(3));
                    post.setContent(resultSet.getString(5));
                    post.setLikes(resultSet.getInt(6));
                    post.setLectureId(resultSet.getInt(1));
                    post.setUserId(resultSet.getInt(2));
                    post.setUserName(resultSet.getString(4));
                    posts.add(post);
                }
                return posts;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public static Lecture getLectureByCode(int code) {
        if (connectionStatus) {
            try {
                System.out.println("[SQL] Asked for Lecture with code: " + code);
                ResultSet resultSet = statement.executeQuery("SELECT lecture.id,lecture.title,lecture.code " +
                        "FROM lecture " +
                        "WHERE lecture.code = " + code);
                if (resultSet.next()) {
                    return new Lecture(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void addUserToLecture(String username, Lecture lecture) {
        if (connectionStatus) {
            try {
                User user = getUserByUsername(username);
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO user_lecture VALUES (" + user.userid + "," + lecture.id + ")");
                System.out.println("[SQL] Lecture: " + lecture.name + " to " + user.username + " lectures.");
            } catch (SQLException e) {
                System.err.print("[SQL] " + e);
            }
        }
    }

    public static List<Lecture> getLecturesByUsername(String username) {

        if (connectionStatus) {
            List<Lecture> lectures = new ArrayList<>();
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("" +
                        "SELECT l.id, l.title, l.code FROM lecture AS l " +
                        "JOIN user_lecture AS ul ON l.id = ul.lectureid " +
                        "JOIN users AS u ON u.id = ul.userid " +
                        "WHERE u.username =\"" + username + "\"");
                while (resultSet.next()) {
                    Lecture lecture = new Lecture();
                    lecture.setId(resultSet.getInt(1));
                    lecture.setTitle(resultSet.getString(2));
                    lecture.setCode(resultSet.getInt(3));
                    lectures.add(lecture);
                }
                return lectures;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getCount(String attribut, String table) {
        if (connectionStatus) {
            try {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(" + attribut + ") FROM " + table);
                return resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static boolean duplicate(String attribut, String table, String value, String comparison) {
        if (connectionStatus) {
            try {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(" + attribut + ") FROM " +
                        table + " WHERE " + comparison + "='" + value + "'");
                if (resultSet.getInt(1) == 0) return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean createDatabase() {
        try {

            statement.executeUpdate("DROP TABLE IF EXISTS users");
            statement.executeUpdate("DROP TABLE IF EXISTS post");
            statement.executeUpdate("DROP TABLE IF EXISTS answer");
            statement.executeUpdate("DROP TABLE IF EXISTS lecture");
            statement.executeUpdate("DROP TABLE IF EXISTS user_lecture");
            statement.executeUpdate("DROP TABLE IF EXISTS user_likes");
            statement.executeUpdate("DROP TABLE IF EXISTS user_likes_answers");

            statement.executeUpdate("CREATE TABLE users(" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT ," +
                    " username VARCHAR(32))");
            statement.executeUpdate("CREATE TABLE post(" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT , " +
                    "userid INTEGER, " +
                    "lectureid INTEGER, " +
                    "content VARCHAR(1024), " +
                    "likes INTEGER )");
            statement.executeUpdate("CREATE TABLE user_likes(" +
                    "likeId INTEGER PRIMARY KEY AUTO_INCREMENT ," +
                    "userId INTEGER , " +
                    "postId INTEGER)");
            statement.executeUpdate("CREATE TABLE user_likes_answers(" +
                    "likeId INTEGER PRIMARY KEY AUTO_INCREMENT ," +
                    "userId INTEGER , " +
                    "answerId INTEGER)");
            statement.executeUpdate("CREATE TABLE answer(" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "postid INTEGER, " +
                    "userid INTEGER, " +
                    "content VARCHAR(1024))");
            statement.executeUpdate("CREATE TABLE lecture(" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "title VARCHAR(1024), " +
                    "code INTEGER)");
            statement.executeUpdate("CREATE TABLE user_lecture(" +
                    "userid INTEGER NOT NULL," +
                    "lectureid INTEGER NOT NULL)");

            statement.executeUpdate("INSERT INTO users VALUES (NULL,'y0000000')");
            statement.executeUpdate("INSERT INTO lecture VALUES (1,'Mobile Computing Lab',123456)");
            statement.executeUpdate("INSERT INTO users VALUES (NULL ,'y0000001')");
            statement.executeUpdate("INSERT INTO post VALUES (NULL,1,1,'Hallo!',42)");
            statement.executeUpdate("INSERT INTO answer VALUES (NULL,1,2,'Hallo zurück!')");
            statement.executeUpdate("INSERT INTO user_lecture VALUES (1,1)");
            statement.executeUpdate("INSERT INTO user_lecture VALUES (2,1)");
            statement.executeUpdate("INSERT INTO user_likes VALUES (NULL,1,1)");

            System.out.println("[INFO] SQL Database created.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e);
            System.out.println(e.getErrorCode());
        }
        return false;
    }

    public static Lecture addnewLecture(String name) {
        if (connectionStatus) {
            Random r = new Random();
            int code = r.nextInt(100000000);
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO lecture(title,code) " +
                        "VALUES ('" + name + "'," + code + ")");
                System.out.println("[SQL] User added Lecture " + name + " to database.");
                //statement.executeUpdateUpdate("")
                return getLectureByCode(code);
            } catch (SQLException e) {
                System.err.print("[SQL] " + e);
            }
        }
        return null;
    }

    public static User getUserByUserId(int userId) {
        if (connectionStatus) {
            try {
                System.out.println("[SQL] Asked for Lecture with code: " + userId);
                ResultSet resultSet = statement.executeQuery(
                        "SELECT u.id, u.username " +
                                "FROM users AS u " +
                                "WHERE u.id = " + userId);
                if (resultSet.next()) {
                    return new User(resultSet.getInt(1),
                            resultSet.getString(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void addUser(String username) {
        if (connectionStatus && getUserByUsername(username) == null) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO users VALUES (NULL,\"" + username + "\")");
                System.out.println("[SQL] Added user with username: " + username);
            } catch (SQLException e) {
                System.err.print("[SQL] " + e);
            }
        }
    }

    public static void addPost(String username, String lectureCode, String content) {
        if (connectionStatus) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO post VALUES ( " +
                        "NULL, " +
                        getUserByUsername(username).userid + " , " +
                        +getLectureByCode(Integer.parseInt(lectureCode)).id + ",\""
                        + content +
                        "\",0)");
                //System.out.println("[SQL] Added user with username: " + username);
            } catch (SQLException e) {
                System.err.print("[SQL] " + e);
            }
        }
    }

    public static Response likePost(String userName, int postId) {
        if (connectionStatus) {
            try {

                int userId = getUserByUsername(userName).userid;
                if (getUserByUsername(userName) == null) return new Response(false);

                ResultSet resultSet = statement.executeQuery("SELECT COUNT( * ) \n" +
                        "FROM user_likes " +
                        "WHERE postid =" + postId +
                        " AND userid =" + userId);
                while (resultSet.next())
                    if (resultSet.getInt(1) >= 1)
                        return new Response(false);

                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO user_likes VALUES ( " +
                        "NULL, " +
                        userId + " , " +
                        +postId + ")");

                statement = connection.createStatement();
                statement.executeUpdate("UPDATE post SET likes = likes + 1 WHERE id =" + postId);
                return new Response(true);
                //System.out.println("[SQL] Added user with username: " + username);
            } catch (SQLException e) {
                System.err.print("[SQL] " + e);
            }
        }
        return new Response(true);
    }

    public static void addAnswer(String username, String postId, String content) {
        if (connectionStatus) {
            try {
                int userId = getUserByUsername(username).userid;
                int postID = Integer.parseInt(postId);
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO answer VALUES ( " +
                        "NULL, "
                        + postID + "," +
                        userId + " , \"" +
                        content +
                        "\")");
                //System.out.println("[SQL] Added user with username: " + username);
                if (mServer != null) {
                    // send the message to the client
                    mServer.sendMessage(new androidlab.notifications.User(getCreatorOfPost(postID).username, "New Answer!"));
                }

            } catch (SQLException e) {
                System.err.print("[SQL] " + e);
            }
        }
    }

    public static List<Answer> getAnswersByPost(int postid) {
        if (connectionStatus) {
            List<Answer> answers = new ArrayList<>();
            try {
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("" +
                        "SELECT a.id, a.userid, a.postid, a.content, u.username FROM answer AS a " +
                        "JOIN post AS p ON p.id = a.postid " +
                        "JOIN users AS u ON u.id = a.userid " +
                        "WHERE a.postid=" + postid);

                while (resultSet.next()) {
                    Answer answer = new Answer();
                    answer.setId(resultSet.getInt(1));
                    answer.setUserId(resultSet.getInt(2));
                    answer.setPostId(resultSet.getInt(3));
                    answer.setContent(resultSet.getString(4));
                    answer.setUsername(resultSet.getString(5));
                    answers.add(answer);
                }
                return answers;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public static User getCreatorOfPost(int postId) {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT p.userId FROM post AS p WHERE p.id=" + postId);
            while (resultSet.next()) {
                return getUserByID(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User();
    }

}
