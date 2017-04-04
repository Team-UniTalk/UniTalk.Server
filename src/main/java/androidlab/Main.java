package androidlab;

import androidlab.notifications.TcpServer;
import androidlab.utils.SqlManager;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;

/**
 * Main class.
 */
public class Main {

    static String address = "localhost";

    // Base URI the Grizzly HTTP server will listen on
    public static String BASE_URI = "http://" + address + ":8080/";
    public static TcpServer mServer;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources
        final ResourceConfig rc = new ResourceConfig().packages("androidlab");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     */
    public static void main(String[] args) throws IOException {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            System.out.println("[INFO] IP: " + ip);
            BASE_URI = "http://" + ip + ":8080/";
        } catch (Exception e) {
            System.err.println(e);
        }
        final HttpServer server = startServer();
        SqlManager.connect();
        SqlManager.createDatabase();
        System.out.println(String.format("[INFO] Jersey app started with WADL available at "
                + "%sapplication.wadl\n[INFO] Hit enter to stop the Server.", BASE_URI));

        mServer = new TcpServer(new TcpServer.OnMessageReceived() {
            @Override
            //this method declared in the interface from TCPServer class is implemented here
            //this method is actually a callback method, because it will run every time when it will be called from
            //TCPServer class (at while)
            public void messageReceived(String message) {
                System.out.println("[PUSH] " + message);
            }
        });
        mServer.start();

        System.in.read();
        SqlManager.disconnect();
        server.shutdown();
        mServer.close();

        System.exit(1);
    }
}

