package androidlab;

import androidlab.database.Lecture;
import androidlab.database.Response;
import androidlab.database.User;
import androidlab.utils.LdapAuthenticator;
import androidlab.utils.SqlManager;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static androidlab.Main.mServer;

/**
 * User class for alter user database
 *
 * @author Mühlenstädt
 */

@Path("/user")
public class UserControl {

    private static int getUserCount() {
        int result = SqlManager.getCount("id", "users");
        return result;
    }

    @POST
    @Path("/add/{user}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@PathParam("user") String user, String in) {
        boolean result;

        JSONObject message = new JSONObject(in);
        result = LdapAuthenticator.authenticate(message.getString("username"),message.getString("password"));
        if(result) SqlManager.addUser(message.getString("username"));

        return new Response(result);
    }

    @GET
    @Path("/get/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Lecture> getPosts(@PathParam("username") String username) {
        System.out.println("[INFO] The user "+ username +" asked for his/her Lectures!");
        return SqlManager.getLecturesByUsername(username);
    }

    @GET
    @Path("/getid/{userid}")
    @Produces({MediaType.APPLICATION_JSON})
    public User getPosts(@PathParam("userid") int userId) {
        System.out.println("[INFO] The user "+ userId +" asked for his Data");
        return SqlManager.getUserByID(userId);
    }

}
