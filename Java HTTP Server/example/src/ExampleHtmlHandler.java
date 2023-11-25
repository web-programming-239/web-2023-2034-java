import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class ExampleHtmlHandler extends BaseHandler {
    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        response(200,
                """
                <h1>test1</h1>
                <h1>test2</h1>
                """
        );
    }
}
