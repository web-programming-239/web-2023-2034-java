import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class SampleHttpServer {
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/register", new RegisterHandler("example\\templates"));
        server.createContext("/list", new ListHandler("example\\templates", list));

        server.setExecutor(null);
        server.start();
    }
}
