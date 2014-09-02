package com.smartappservices.gps.geo;

import java.io.*;
import java.net.*;

public class Geocoder {

    private final static String ENCODING = "UTF-8";
    private final static String KEY = "xyz";

    public static class Location {

        public String lon, lat;

        private Location(String lat, String lon) {
            this.lon = lon;
            this.lat = lat;
        }

        public String toString() {
            return " " + lat + "," + lon;
        }
    }

    public static Location getLocation(String address) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://maps.google.com/maps/geo?q=" + URLEncoder.encode(address, ENCODING) + "&output=csv&key=" + KEY).openStream()));
        String line;
        Location location = null;
        int statusCode = -1;
        while ((line = in.readLine()) != null) {
// Format: 200,6,42.730070,-73.690570
            statusCode = Integer.parseInt(line.substring(0, 3));
            if (statusCode == 200) {
                location = new Location(
                        line.substring("200,6,".length(), line.indexOf(',', "200,6,".length())),
                        line.substring(line.indexOf(',', "200,6,".length()) + 1, line.length()));
            }
        }
        if (location == null) {
            switch (statusCode) {
                case 400:
                    throw new IOException("Bad Request");
                case 500:
                    throw new IOException("Unknown error from Google Encoder");
                case 601:
                    throw new IOException("Missing query");
                case 602:
                    return null;
                case 603:
                    throw new IOException("Legal problem");
                case 604:
                    throw new IOException("No route");
                case 610:
                    throw new IOException("Bad key");
                case 620:
                    throw new IOException("Too many queries");
            }
        }
        return location;
    }

    public static void main(String[] argv) throws Exception {
        for (int i = 0; i < 20; i++) {
            System.out.println(Geocoder.getLocation("28.6353080,77.2249600"));
//            System.out.println(Geocoder.getLocation("28.6353080,77.2249600"));
//            System.out.println(Geocoder.getLocation("25.6125000,85.1283333"));
//            System.out.println(Geocoder.getLocation("26.8465108,80.9466832"));
//            System.out.println(Geocoder.getLocation("25.1980090,85.5218960"));
//            System.out.println(Geocoder.getLocation("23.6345010,-102.5527840"));
//            System.out.println(Geocoder.getLocation("28.6691565,77.4537578"));
//            System.out.println(Geocoder.getLocation("28.5355161,28.5355161"));
//            System.out.println(Geocoder.getLocation("21.1950000,72.8194440"));
//            System.out.println(Geocoder.getLocation("29.9456906,78.1642478"));
        }
    }
}
