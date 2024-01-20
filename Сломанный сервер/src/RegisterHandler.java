import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class RegisterHandler extends BaseTemplateHandler{
    public RegisterHandler(String folder) {
        super(folder);
    }

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        sendStaticFile("register.html");
    }
}
