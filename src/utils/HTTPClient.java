package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Lluís Bayer Soler on 14/11/16.
 */
public class HTTPClient {

    public static String request(String u) throws IOException {
        URL url = new URL(u);
        URLConnection con = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response="";
        String line;
        while((line = br.readLine()) != null){
            response += line;
        }
        br.close();
        return response;
    }

}
