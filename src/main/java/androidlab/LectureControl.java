package androidlab;

import androidlab.database.Lecture;
import androidlab.utils.SqlManager;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Lecturecontrol, this class controls all requests for lectures
 *
 * @author Mühlenstädt
 */
@Path("/lecture")
public class LectureControl {

    @POST
    @Path("/add/{lectureId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Lecture addLecture(@PathParam("lectureId") String name, String in) {

        JSONObject message = new JSONObject(in);

        System.out.println("[INFO] User added new lecture with name: " + name);

        Lecture lecture = SqlManager.addnewLecture(name);
        SqlManager.addUserToLecture(message.getString("user"),lecture);
        return lecture;
    }

    @POST
    @Path("/get/{lectureCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Lecture getLectures(@PathParam("lectureCode") int code, String in) {
        System.out.println("[INFO] User asked for Lecture with code: " + code);
        JSONObject message = new JSONObject(in);
        Lecture lecture = SqlManager.getLectureByCode(code);
        SqlManager.addUserToLecture(message.getString("user"),lecture);
        return lecture;
    }

}
