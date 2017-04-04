package androidlab;

import androidlab.database.Lecture;
import androidlab.database.Response;
import androidlab.utils.SqlManager;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Muehlenstaedt
 */
@Path("/connection")
public class ConnectionControl {

    @GET
    @Path("/get/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkConnection() {
        return new Response(true);
    }

}
