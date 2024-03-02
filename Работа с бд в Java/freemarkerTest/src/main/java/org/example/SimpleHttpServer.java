package org.example;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

abstract class BaseTemplateHandler extends BaseHandler {
    protected String folder;

    protected Configuration cfg;


    public void setFolder(String folder) {
        this.folder = folder;
    }

    public void setCfg(Configuration cfg) {
        this.cfg = cfg;
    }

    private void validate() {
        if (folder == null || cfg == null) {
            throw new RuntimeException("Не все обязательные поля были проинициализированы");
        }
    }

    /**
     * Используйте этот метод, чтобы сформировать ответ из HTML шаблона
     *
     * @param filename название файла с шаблоном
     * @param params   значения, которые нужно подставить в шаблон
     * @param code     код ответа
     */
    protected void sendTemplate(String filename, Map<String, Object> params, int code) throws IOException {
        validate();
        checkContext();

        Template temp = cfg.getTemplate(filename);

        OutputStreamWriter os = new OutputStreamWriter(httpExchange.getResponseBody());
        httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        httpExchange.sendResponseHeaders(code, 0);

        try {
            temp.process(params, os);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
        os.close();
    }

    /**
     * Используйте этот метод, чтобы сформировать ответ из HTML шаблона
     *
     * @param filename название файла с шаблоном
     * @param params   значения, которые нужно подставить в шаблон
     */
    protected void sendTemplate(String filename, Map<String, Object> params) throws IOException {
        sendTemplate(filename, params, 200);
    }
}

abstract class BaseHandler implements HttpHandler {
    protected HttpExchange httpExchange;
    private Map<String, String> params;

    protected static final Map<String, String> MIME_MAP = new HashMap<>();

    static {
        MIME_MAP.put("appcache", "text/cache-manifest");
        MIME_MAP.put("css", "text/css");
        MIME_MAP.put("gif", "image/gif");
        MIME_MAP.put("html", "text/html; charset=utf-8");
        MIME_MAP.put("js", "application/javascript");
        MIME_MAP.put("json", "application/json");
        MIME_MAP.put("jpg", "image/jpeg");
        MIME_MAP.put("jpeg", "image/jpeg");
        MIME_MAP.put("mp4", "video/mp4");
        MIME_MAP.put("pdf", "application/pdf");
        MIME_MAP.put("png", "image/png");
        MIME_MAP.put("svg", "image/svg+xml");
        MIME_MAP.put("xlsm", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MIME_MAP.put("xml", "application/xml");
        MIME_MAP.put("zip", "application/zip");
        MIME_MAP.put("md", "text/plain");
        MIME_MAP.put("txt", "text/plain");
        MIME_MAP.put("php", "text/plain");
    }

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
        this.params = null;
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
        checkContext();
        httpExchange.getResponseHeaders().add("Location", to);
        httpExchange.sendResponseHeaders(301, -1);

    }

    /**
     * Используйте этот метод, чтобы получить параметры, переданные вам в GET или POST запросе
     */
    protected Map<String, String> params() throws IOException {
        checkContext();
        if (params != null) {
            return params;
        }
        if (httpExchange.getRequestMethod().equalsIgnoreCase("GET")) {
            params = parseGetParams();
        } else if (httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            params = parsePostParams();
        } else {
            throw new RuntimeException("Не поддерживаемый тип запроса: %s".formatted(httpExchange.getRequestMethod()));
        }
        return params;

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


    protected void checkContext() {
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

    /**
     * Используйте этот метод, чтобы послать файл в качестве ответа на запрос
     *
     * @param filename название файла
     * @param code     код ответа
     */
    protected void sendStaticFile(String filename, int code) throws IOException {
        File file = new File(filename);
        String mime = getMime(file.getPath());

        sendStaticFile(new FileInputStream(file), mime, code);
    }

    /**
     * Используйте этот метод, чтобы послать файл в качестве ответа на запрос
     *
     * @param filename название файла
     */
    protected void sendStaticFile(String filename) throws IOException {
        sendStaticFile(filename, 200);
    }

    protected void sendStaticFile(InputStream stream, String mime, int code) throws IOException {
        checkContext();
        var t = httpExchange;

        Headers h = t.getResponseHeaders();
        h.set("Content-Type", mime);
        t.sendResponseHeaders(code, 0);

        OutputStream os = t.getResponseBody();
        final byte[] buffer = new byte[0x10000];
        int count;
        while ((count = stream.read(buffer)) >= 0) {
            os.write(buffer, 0, count);
        }
        stream.close();
        os.close();
    }

    private static String getExt(String path) {
        int slashIndex = path.lastIndexOf('/');
        String basename = (slashIndex < 0) ? path : path.substring(slashIndex + 1);

        int dotIndex = basename.lastIndexOf('.');
        if (dotIndex >= 0) {
            return basename.substring(dotIndex + 1);
        } else {
            return "";
        }
    }

    protected static String getMime(String path) {
        String ext = getExt(path).toLowerCase();
        return MIME_MAP.getOrDefault(ext, "application/octet-stream");
    }


    protected abstract void handleImpl(HttpExchange t) throws IOException;
}

public class SimpleHttpServer {
    Configuration cfg;
    String templatesFolder;
    String staticFolder;

    private final HttpServer server;

    public SimpleHttpServer(int port) throws IOException {
        this.templatesFolder = "/templates";
        this.staticFolder = "/static";

        cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(SimpleHttpServer.class, templatesFolder);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(true);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
        cfg.setCacheStorage(NullCacheStorage.INSTANCE);

        server = HttpServer.create(new InetSocketAddress(port), 0);
        createContext("/favicon.ico", new BaseTemplateHandler() {
            @Override
            protected void handleImpl(HttpExchange t) throws IOException {
                String filename = "favicon.ico";
                InputStream stream = SimpleHttpServer.class.getResourceAsStream(staticFolder + "/" + filename);
                if (stream == null) {
                    System.out.println("Файл %s не найден".formatted(filename));
                    response(404, "%s not found".formatted(filename));
                    return;
                }
                sendStaticFile(stream, getMime(filename), 200);
            }
        });
        createContext("/static", new StaticFilesHandler(filename -> SimpleHttpServer.class.getResourceAsStream(staticFolder + "/" + filename), staticFolder + "/"));
    }

    public SimpleHttpServer() throws IOException {
        this(8000);
    }

    public void createContext(String url, HttpHandler handler) {
        server.createContext(url, handler);
    }

    public void createContext(String url, BaseTemplateHandler handler) {
        handler.setCfg(cfg);
        handler.setFolder(templatesFolder);
        server.createContext(url, handler);
    }

    public void run() {
        server.setExecutor(null);
        server.start();
    }
}

@FunctionalInterface
interface StreamProvider {
    InputStream getStream(String filename);
}

class StaticFilesHandler extends BaseHandler {
    String prefix;
    StreamProvider streamProvider;

    public StaticFilesHandler(StreamProvider streamProvider, String prefix) {
        this.streamProvider = streamProvider;
        this.prefix = prefix;
    }

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        String path = t.getRequestURI().toString().replace(prefix, "");
        sendStaticFile(streamProvider.getStream(path), getMime(path), 200);
    }
}
