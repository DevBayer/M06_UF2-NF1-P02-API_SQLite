package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.HTTPClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Lluís Bayer Soler on 14/11/16.
 */
public class TheMovieDB {

    private static final String API_KEY = "API_KEY";
    private static final String API_URI = "https://api.themoviedb.org/3/";
    private static final String API_LANGUAGE = "es";

    public static JSONObject Request(String object, int id, String path){
        if(path == null) path = "";
        String url = formatURL(object+"/"+id+path);
        try {
            return parseJSON(HTTPClient.request(url));
        }catch(IOException e){
            return null;
        }
    }

    private static String formatURL(String path){
        return API_URI+path+"?api_key="+API_KEY+"&language="+API_LANGUAGE;
    }

    private static JSONObject parseJSON(String html){
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(html);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Movie getMovieDetails(JSONObject json){
        int id = Integer.parseInt(json.get("id").toString());
        String title = json.getOrDefault("title", "no-title").toString();
        String released_date = json.getOrDefault("release_date", "").toString();

        Movie movie = new Movie(id, title, released_date);
        return movie;
    }

    public static ArrayList<Cast> getMovieCasting(JSONObject json, int movie_id){
        JSONArray arr = (JSONArray) json.get("cast");
        Iterator i = arr.iterator();
        ArrayList<Cast> casting = new ArrayList<>();
        while(i.hasNext()){
            JSONObject character = (JSONObject) i.next();
            String actor_character = character.getOrDefault("character", "").toString();
            String credit_id = character.getOrDefault("credit_id", 0).toString();
            int id = Integer.parseInt( character.getOrDefault("id", 0).toString() );
            String name = character.getOrDefault("name", "").toString();
            casting.add(new Cast(actor_character, credit_id, id, name, movie_id ));
        }
        return casting;
    }

    public static People getPeopleDetails(JSONObject json){
        int id = Integer.parseInt(json.get("id").toString());
        String biography = "";
        String birthday = "";
        String deathday = "";
        String homepage = "";
        if(json.getOrDefault("biography", "") != null){
            biography = json.get("biography").toString();
        }
        if(json.getOrDefault("birthday", "") != null){
            birthday = json.get("birthday").toString();
        }
        if(json.getOrDefault("deathday", "") != null){
            biography = json.get("deathday").toString();
        }
        if(json.getOrDefault("homepage", "") != null){
            biography = json.get("homepage").toString();
        }
        int gender = Integer.parseInt(json.get("gender").toString());
        String name = json.getOrDefault("name", "").toString();

        People people = new People(id, biography, birthday,deathday, gender, homepage, name);
        return people;
    }

}
