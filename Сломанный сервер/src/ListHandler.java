import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ListHandler extends BaseTemplateHandler {

    ArrayList<String> list;
    public ListHandler(String folder, ArrayList<String> list) {
        super(folder);
        this.list = list;
    }

    @Override
    protected void handleImpl(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equalsIgnoreCase("GET")) {
            sendTemplate("list.html", Map.of("list", list.toString()));
        } else {
            redirect("/list");
        }
    }
}
