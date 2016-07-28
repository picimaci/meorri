package utils;

import play.libs.Json;

import java.util.List;
import java.util.Map;

public class RequestUtils {
    public static String getParam(Map<String, String[]> map, String key) {
        return map.containsKey(key) && (map.get(key)).length > 0 ? (map.get(key))[0] : null;
    }

    @SuppressWarnings("unchecked")
    public static List<String> toStringList(String input) {
        return (List<String>) Json.fromJson(Json.parse(input), List.class);
    }
}
