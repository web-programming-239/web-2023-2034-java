import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class StatsHandler extends BaseTemplateHandler {

    int good;
    int bad;
    public StatsHandler(String folder) {
        super(folder);
        good = 0;
        bad = 0;
    }

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equalsIgnoreCase("GET")) {
            sendTemplate("stats.html", Map.of("good", good, "bad", bad));
        } else {
            if (params().get("test").equals("test")) {
                good++;
            } else {
                bad++;
            }
            redirect("/stats");
        }
    }
}
