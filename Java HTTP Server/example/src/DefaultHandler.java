import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class DefaultHandler extends BaseHandler{

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        response(404, "не нашли");
    }
}
