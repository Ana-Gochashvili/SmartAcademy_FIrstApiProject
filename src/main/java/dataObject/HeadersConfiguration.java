package dataObject;

import java.util.HashMap;
import java.util.Map;

public class HeadersConfiguration {
    public Map<String, String> header;
    public HeadersConfiguration() {
        header = new HashMap<>();
        header.put("connection", "keep-alive");
        header.put("content-type", "application/json; charset=utf-8");
    }

    public Map<String, String> getHeaders() {
        return header;
    }

}
