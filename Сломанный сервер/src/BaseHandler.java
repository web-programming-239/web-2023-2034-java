import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseHandler implements HttpHandler {
    protected HttpExchange httpExchange;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.httpExchange = exchange;
        System.out.println("Получен %s запрос на %s".formatted(
                httpExchange.getRequestMethod(), httpExchange.getRequestURI()
        ));
        try {
            handleImpl(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            response(500, "Сервер сгорел :(");
        }
        if (exchange.getResponseCode() == -1) {
            response(500, "Сервер ничего не вернул, вероятно, он сгорел (или вы забыли вызвать метод response())");
        }
        this.httpExchange = null;
    }

    /**
     * Используйте этот метод, чтобы сформировать ответ сервера.
     *
     * @param code код ответа
     * @param text текст ответа
     * @apiNote Например, response(200, "Hello, world")
     */
    protected void response(int code, String text) throws IOException {
        httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        httpExchange.sendResponseHeaders(code, 0);

        OutputStream os = httpExchange.getResponseBody();
        os.write(text.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    /**
     * Используйте этот метод, чтобы перенаправить пользователя на другую страницу
     *
     * @param to адрес прененаправления
     * @apiNote Например, redirect("/login")
     */
    protected void redirect(String to) throws IOException {
        httpExchange.getResponseHeaders().add("Location", to);
        httpExchange.sendResponseHeaders(301, -1);

    }

    /**
     * Используйте этот метод, чтобы получить параметры, переданные вам в GET или POST запросе
     */
    protected Map<String, String> params() throws IOException {
        checkContext();
        if (httpExchange.getRequestMethod().equalsIgnoreCase("GET")) {
            return parseGetParams();
        } else if (httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            return parsePostParams();
        }
        throw new RuntimeException("Не поддерживаемый тип запроса: %s".formatted(httpExchange.getRequestMethod()));
    }

    /**
     * Используйте этот метод, чтобы получить тело запроса в виде строки
     */
    protected String body() throws IOException {
        checkContext();
        var stream = httpExchange.getRequestBody();
        String res = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        stream.close();
        return res;
    }


    private Map<String, String> parsePostParams() throws IOException {
        return parseParams(body());
    }

    private Map<String, String> parseParams(String query) {
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

    private Map<String, String> parseGetParams() {
        String[] q = httpExchange.getRequestURI().toString().split("\\?");
        if (q.length != 2) {
            return Map.of();
        }
        return parseParams(q[1]);
    }


    protected abstract void handleImpl(HttpExchange t) throws IOException;
}
