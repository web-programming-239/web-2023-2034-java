import com.sun.net.httpserver.Headers;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseTemplateHandler extends BaseHandler {
    protected final String folder;

    private static final Map<String, String> MIME_MAP = new HashMap<>();

    static {
        MIME_MAP.put("appcache", "text/cache-manifest");
        MIME_MAP.put("css", "text/css");
        MIME_MAP.put("gif", "image/gif");
        MIME_MAP.put("html", "text/html");
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

    ;
    private final Pattern pattern = Pattern.compile("[$][{](\\w+)}");

    public BaseTemplateHandler(String folder) {
        this.folder = folder;
    }

    /**
     * Используйте этот метод, чтобы сформировать ответ из HTML шаблона
     *
     * @param filename название файла с шаблоном
     * @param params   значения, которые нужно подставить в шаблон
     * @param code     код ответа
     */
    protected void sendTemplate(String filename, Map<String, Object> params, int code) throws IOException {
        String html = format(readFile(filename), params);
        response(code, html);
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

    /**
     * Используйте этот метод, чтобы послать файл в качестве ответа на запрос
     *
     * @param filename название файла
     * @param code     код ответа
     */
    protected void sendStaticFile(String filename, int code) throws IOException {
        var t = httpExchange;
        File file = getFilePath(filename);

        String mime = getMime(file.getPath());

        Headers h = t.getResponseHeaders();
        h.set("Content-Type", mime);
        if (mime.equals("text/html")) {
            httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        }
        t.sendResponseHeaders(code, 0);

        OutputStream os = t.getResponseBody();
        FileInputStream fs = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int count;
        while ((count = fs.read(buffer)) >= 0) {
            os.write(buffer, 0, count);
        }
        fs.close();
        os.close();
    }

    /**
     * Используйте этот метод, чтобы послать файл в качестве ответа на запрос
     *
     * @param filename название файла
     */
    protected void sendStaticFile(String filename) throws IOException {
        sendStaticFile(filename, 200);
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

    protected File getFilePath(String filename) {
        return Paths.get(folder, filename).toFile();
    }

    private static String getMime(String path) {
        String ext = getExt(path).toLowerCase();
        return MIME_MAP.getOrDefault(ext, "application/octet-stream");
    }

    private String format(String template, Map<String, Object> parameters) {
        StringBuilder newTemplate = new StringBuilder(template);
        List<Object> valueList = new ArrayList<>();

        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String key = matcher.group(1);

            String paramName = "${" + key + "}";
            int index = newTemplate.indexOf(paramName);
            if (index != -1) {
                newTemplate.replace(index, index + paramName.length(), "%s");
                valueList.add(parameters.get(key));
            }
        }

        return String.format(newTemplate.toString(), valueList.toArray());
    }

    private String readFile(String filename) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader objReader = new BufferedReader(new FileReader(getFilePath(filename)))) {
            String str;
            while ((str = objReader.readLine()) != null) {
                builder.append(str);
            }
        }
        return builder.toString();
    }

}
