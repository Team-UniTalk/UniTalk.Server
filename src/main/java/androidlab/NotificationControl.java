package androidlab;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Mühlenstädt
 */

@Path("/notification")
public class NotificationControl {

    @POST
    @Path("/add/{lectureId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addPost(@PathParam("lectureId") String user, String message) {
        /*try {
            SqlManager.insert("users", data);
            System.out.println("[INFO] User " + data + "added to Userdatabase.");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        System.out.println("[INFO] User " + user + "added to Userdatabase." + message);
        return String.format("[INFO] %s sendet '%s'%n", user, message);
    }
}
