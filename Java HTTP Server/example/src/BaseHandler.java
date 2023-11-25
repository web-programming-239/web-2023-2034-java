import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseHandler implements HttpHandler {
    private HttpExchange httpExchange;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.httpExchange = exchange;
        try {
            handleImpl(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            response(500, "Сервер сгорел :(");
        }
        this.httpExchange = null;
    }

    /**
     * Используйте этот метод, чтобы сформировать ответ сервера.
     *
     * @apiNote  Например, response(200, "Hello, world")
     *
     * @param code код ответа
     * @param text текст ответа
     *
     */
    protected void response(int code, String text) throws IOException {
        httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        httpExchange.sendResponseHeaders(code, 0);

        OutputStream os = httpExchange.getResponseBody();
        os.write(text.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    /**
     * Используйте этот метод, чтобы получить параметры, переданные вам в GET запросе
     */
    protected Map<String, String> params() {
        checkContext();
        String[] q = httpExchange.getRequestURI().toString().split("\\?");
        if (q.length < 2) {
            return null;
        }
        String query = q[1];
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(
                    URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8)
            );
        }
        return query_pairs;
    }

    private void checkContext() {
        if (httpExchange == null) {
            throw new RuntimeException("Кажется, вы вызвали этот метод не внутри hanldeImpl(), так что ваш сервер сгорел :(");
        }
    }


    protected abstract void handleImpl(HttpExchange t) throws IOException;
}
