import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class ExampleParamsHandler extends BaseHandler {
    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        Map<String, String> p = params();
        if (p == null) {
            response(400, "");
        } else {
            String test = p.get("test");
            if (test == null) {
                response(400, "");
            } else {
                response(200, test);
            }
        }
    }
}
