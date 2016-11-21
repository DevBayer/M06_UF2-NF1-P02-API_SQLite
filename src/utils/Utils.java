package utils;

import api.TheMovieDB;
import org.json.simple.JSONObject;

import java.util.Scanner;

/**
 * Created by Lluís Bayer Soler on 21/11/16.
 */
public class Utils {
    public static int getScannerInt(){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            if (sc.hasNextInt()) {
                return sc.nextInt();
            } else {
                System.out.println("Solamente se aceptan carácteres númericos.");
                sc.hasNextLine();
            }
        }
        return 0;
    }

    public static JSONObject doRequest(String object, int id, String path){
        try{
            JSONObject response = TheMovieDB.Request(object, id, path);
            if(response == null){
                Thread.sleep(1000);
                return doRequest(object, id, path);
            }
            return response;
        }catch(InterruptedException e){}
        return null;
    }
}
