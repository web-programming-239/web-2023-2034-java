import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class StaticHandler extends BaseTemplateHandler {
    String urlPrefix;

    public StaticHandler(String folder, String urlPrefix) {
        super(folder);
        this.urlPrefix = urlPrefix;
    }

    private void sendNotFound(File file) throws IOException {
        // можете заменить на красивую html страницу
        response(404, "файл %s не найден".formatted(file.getPath()));
    }


    @Override
    public void handleImpl(HttpExchange t) throws IOException {
        URI uri = t.getRequestURI();
        String filename = uri.getPath().replaceFirst(urlPrefix, "");
        File file = getFilePath(filename);

        if (file.isFile()) {
            sendStaticFile(filename);
        } else {
            sendNotFound(file);
        }
    }
}
