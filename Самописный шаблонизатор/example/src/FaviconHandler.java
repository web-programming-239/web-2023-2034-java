import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class FaviconHandler extends BaseTemplateHandler {
    public FaviconHandler(String folder) {
        super(folder);
    }

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        sendStaticFile("favicon.ico");
    }
}
