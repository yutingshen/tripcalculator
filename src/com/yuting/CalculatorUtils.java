package com.yuting;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CalculatorUtils {
    private static final String interchangeJSON = "./resources/interchanges.json";
    private static final String ratesJSON = "./resources/interchanges.json";

    /**
     * Returns distance between two interchanges
     * @param start start interchange
     * @param destination end interchange
     * @return
     * @throws IOException
     */
    public static double getTotalDistance(String start, String destination) throws IOException {
        double distance = 0;
        if (start.equals(destination)) {
            return distance;
        } else {
            HashMap<String, JSONObject> map = CalculatorUtils.convertToMap(interchangeJSON);
            int startId = Integer.parseInt(getId(start, map));
            int destId = Integer.parseInt(getId(destination, map));
            int curr = Math.min(startId, destId);
            while ( curr < Math.max(startId, destId)) {
                JSONObject jsonObj = map.get(Integer.toString(curr));
                JSONArray routes = (JSONArray) jsonObj.get("routes");
                for (Object route : routes) {
                    JSONObject obj = (JSONObject) route;
                    if (Math.toIntExact((Long) obj.get("toId")) > curr) {
                        curr = Math.toIntExact((Long) obj.get("toId"));
                        distance += (double) obj.get("distance");
                    }
                }

            }
            return distance;
        }
    }

    /**
     * Convert JSON file to map for faster searching
     * @param path file path
     * @return map<id, interchange name>
     * @throws IOException
     */
    public static HashMap<String, JSONObject> convertToMap(String path) throws IOException {
        HashMap<String, JSONObject> map = new HashMap<>();
        JSONArray objArr = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader((path))) {
            Object obj = jsonParser.parse(reader);
            objArr.add(obj);

            JSONObject jsonObj = (JSONObject) objArr.get(0);
            JSONObject content = (JSONObject) jsonObj.get("locations");

            content.keySet().forEach(key -> {
                JSONObject value = (JSONObject) content.get(key);
                map.put(key.toString(), value);
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Get the id of the JSON object
     * @param name name of interchange
     * @param map  map<id, interchange name>
     * @return
     */
    public static String getId(String name, HashMap<String, JSONObject> map) {
        Iterator it = map.entrySet().iterator();
        String id = "";
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            JSONObject jsonObj = (JSONObject) pair.getValue();
            if (jsonObj.get("name").equals(name)) {
                id = (String) pair.getKey();
                break;
            }
        }
        return id;
    }




}
