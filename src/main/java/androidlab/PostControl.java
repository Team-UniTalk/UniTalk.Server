package androidlab;

import androidlab.database.Post;
import androidlab.database.Response;
import androidlab.utils.SqlManager;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * The post control.
 *
 * @author Mühlenstädt
 */

@Path("/post")
public class PostControl {

    @POST
    @Path("/add/{lectureCode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPost(@PathParam("lectureCode") String lectureCode, String in) {

        JSONObject message = new JSONObject(in);
        SqlManager.addPost(message.getString("user"), lectureCode, message.getString("content"));

        System.out.println("[INFO] User added new Post to lecture: " + lectureCode);
        return new Response(true);
    }

    @GET
    @Path("/get/{lectureId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Post> getPosts(@PathParam("lectureId") int code) {
        System.out.println("[INFO] Devices asked for Posts from Lecture with code: " + code + " !");
        return SqlManager.getPostsByLecture(code);
    }

}