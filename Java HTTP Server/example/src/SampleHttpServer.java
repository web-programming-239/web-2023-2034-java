import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SampleHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/params", new ExampleParamsHandler());
        server.createContext("/html", new ExampleHtmlHandler());
        server.createContext("/", new DefaultHandler());

        server.setExecutor(null); // просто пишем
        server.start();
    }
}
