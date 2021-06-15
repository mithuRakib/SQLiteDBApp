package com.example.practiceproject.InternetConnection;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class InternetUtil {
//    https://www.superheroapi.com/api.php/1527878337347767/10

    final static String PLAYER_BASE_URL = "https://www.superheroapi.com/api.php";
    final static String API_KEY = "1527878337347767";

    public static Uri builtUri(String heroId) {
        Uri builtUri = Uri.parse(PLAYER_BASE_URL).buildUpon()
                .appendPath(API_KEY).appendPath(heroId).build();
        return builtUri;
    }

    public static URL builtUrl(Uri builtUri) {
        URL builtUrl = null;

        try {
            builtUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return builtUrl;
    }

    public static String getResponseFromHTTPUrl(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = connection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            connection.disconnect();
        }
    }

}
