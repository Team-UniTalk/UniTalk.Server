package androidlab;

import androidlab.database.Response;
import androidlab.utils.SqlManager;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Mühlenstädt on 29.01.2017.
 */
@Path("/rate")
public class RatingControl {

    @POST
    @Path("/like/{lectureCode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPost(@PathParam("lectureCode") String lectureCode, String in) {

        JSONObject message = new JSONObject(in);
        String userName = message.getString("user");
        int postId = Integer.parseInt(message.getString("postId"));
        Response response = SqlManager.likePost(userName,postId);
        if(response.status)
            System.out.println("[INFO] User liked a Post with id: " + postId);
        else
            System.out.println("[INFO] User already liked a Post with id: " + postId);
        return response;
    }
}
