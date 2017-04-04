package androidlab.database;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Mühlenstädt on 23.01.2017.
 */
@XmlRootElement
public class Response {

    public boolean status;

    public Response() {
        status = false;
    }

    public Response(boolean status) {
        this.status = status;
    }
}