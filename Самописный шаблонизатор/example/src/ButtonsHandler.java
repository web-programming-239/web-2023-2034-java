import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class ButtonsHandler extends BaseTemplateHandler{
    public ButtonsHandler(String folder) {
        super(folder);
    }

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        sendStaticFile("buttons.html");
    }
}
