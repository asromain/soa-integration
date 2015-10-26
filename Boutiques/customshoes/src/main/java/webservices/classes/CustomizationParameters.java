package webservices.classes;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CustomizationParameters {

    public static Map<String, String> cleats = new HashMap<String, String>();

    public static Map<String, String> colors = new HashMap<String, String>();

    public static Map<String, String> sizes = new HashMap<String, String>();

    public static JSONObject getCleats() {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, String> entry : cleats.entrySet()) {
            object.append(entry.getKey(), entry.getValue());
        }
        return object;
    }

    public static JSONObject getColors() {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, String> entry : colors.entrySet()) {
            object.append(entry.getKey(), entry.getValue());
        }
        return object;
    }

    public static JSONObject getSizes() {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, String> entry : sizes.entrySet()) {
            object.append(entry.getKey(), entry.getValue());
        }
        return object;
    }

    public static JSONObject getDetailsOnCleats(String cramponId) {
        if (cleats.containsKey(cramponId)) {
            //return cramponId + " " + cleats.get(cramponId);
            return new JSONObject().append(cramponId, cleats.get(cramponId));
        } else {
            return null;
        }
    }

    public static JSONObject getDetailsOnColors(String colorId) {
        if (colors.containsKey(colorId)) {
            //return cramponId + " " + cleats.get(cramponId);
            return new JSONObject().append(colorId, colors.get(colorId));
        } else {
            return null;
        }
    }

    public static JSONObject getDetailsOnSizes(String sizeId) {
        if (sizes.containsKey(sizeId)) {
            //return cramponId + " " + cleats.get(cramponId);
            return new JSONObject().append(sizeId, sizes.get(sizeId));
        } else {
            return null;
        }
    }

    static {
        cleats.put("0", "FG : Firm Ground");
        cleats.put("1", "SG : Soft Ground");
        cleats.put("2", "H : Hybrid");
        cleats.put("3", "HG : Hard ground");
        cleats.put("4", "AG : Artificial ground");
        cleats.put("5", "AT : Astro turf");

        colors.put("0", "Black");
        colors.put("1", "White");
        colors.put("2", "Grey");
        colors.put("3", "Blue");
        colors.put("4", "Yellow");
        colors.put("5", "Green");
        colors.put("6", "Red");
        colors.put("7", "Pink");
        colors.put("8", "Orange");
        colors.put("9", "Brown");

        sizes.put("0", "41");
        sizes.put("1", "41.5");
        sizes.put("2", "42");
        sizes.put("3", "42.5");
        sizes.put("4", "43");
        sizes.put("5", "43.5");
        sizes.put("6", "44");
        sizes.put("7", "44.5");
        sizes.put("8", "45");
        sizes.put("9", "45.5");
    }
}
