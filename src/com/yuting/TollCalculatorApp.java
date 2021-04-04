package com.yuting;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class TollCalculatorApp {
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static double rate = 0.25;

    public static void main(String[] args) throws IOException {

        try {
            HashMap<String, JSONObject> map = CalculatorUtils.convertToMap("./resources/interchanges.json");
            double distance = CalculatorUtils.getTotalDistance("QEW", "Salem Road");
            double cost = Double.parseDouble(df.format(distance * rate));

            JSONObject result = new JSONObject();
            result.put("distance", distance);
            result.put("tripCharge", cost);

            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
