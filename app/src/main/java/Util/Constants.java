package Util;

import java.util.Random;

public class Constants {
    public static final  String URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson";
    public static final int LIMIT = 40;

    public static int randomInt(int max, int min) {
            return new Random().nextInt(max-min)+min;
    }
}