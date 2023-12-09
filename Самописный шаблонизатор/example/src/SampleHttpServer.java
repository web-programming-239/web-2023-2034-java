import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SampleHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/static", new StaticHandler("static", "/static"));
        server.createContext("/favicon.ico", new FaviconHandler("static"));
        server.createContext("/buttons", new ButtonsHandler("templates"));
        server.createContext("/stats", new StatsHandler("templates"));

        server.setExecutor(null);
        server.start();
    }
}
