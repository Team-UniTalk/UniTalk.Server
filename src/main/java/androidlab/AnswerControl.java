package androidlab;

import androidlab.database.Answer;
import androidlab.database.Response;
import androidlab.utils.SqlManager;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * User class for alter answer database
 *
 * @author Mühlenstädt
 */

@Path("/answer")
public class AnswerControl {

    @POST
    @Path("/add/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAnswer(@PathParam("postId") String postId, String in) {

        JSONObject message = new JSONObject(in);

        SqlManager.addAnswer(message.getString("user"), postId, message.getString("content"));

        System.out.println("[INFO] User added new Answer to Post: " + postId);
        return new Response(true);
    }

    @GET
    @Path("/get/{post}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Answer> getAnswers(@PathParam("post") int post) {
        System.out.println("[INFO] Devices asked for Answers from Post with id: " + post + " !");
        return SqlManager.getAnswersByPost(post);
    }


}