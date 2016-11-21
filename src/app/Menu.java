package app;

import api.Cast;
import api.Movie;
import api.People;
import api.TheMovieDB;
import org.json.simple.JSONObject;
import utils.Database;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static utils.Database.query;

/**
 * Created by Lluís Bayer Soler on 21/11/16.
 */
public class Menu {

    public static void getAllMovies(){
        List<Movie> mm = Database.getMovies(0);
        System.out.println("Total películas: "+mm.size());
        for (Movie m: mm) {
            System.out.println(m.prettyPrint());
        }
    }

    public static void getAllPeoples(){
        List<People> pp = Database.getPeoples(0);
        System.out.println("Total películas: "+pp.size());
        for (People p: pp) {
            System.out.println(p.prettyPrint());
        }
    }

    public static void getPeople(){
        System.out.println("ID de la persona: ");
        int id = Utils.getScannerInt();

        List<People> pp = Database.getPeoples(id);
        if(pp.size() > 0) {
            People p = pp.get(0);
            System.out.println(p.prettyPrint());
            System.out.println("Sale en: ");
            List<String> mm = Database.getMoviesByPeople(p.getId());
            System.out.println("Total películas: "+mm.size());
            for (String m: mm) {
                System.out.println(m);
            }
        }else{
            System.out.println("No hay ningúna ficha de persona con ese identificador.");
        }
    }

    public static void getMovie(){
        System.out.println("ID de la película: ");
        int id = Utils.getScannerInt();

        List<Movie> mm = Database.getMovies(id);
        if(mm.size() > 0) {
            Movie m = mm.get(0);
            System.out.println(m.prettyPrint());
            System.out.println("Casting de la película: ("+m.getCast().size()+") :");
            for (Cast c : m.getCast()) {
                System.out.println(c.prettyPrint());
            }

        }else{
            System.out.println("No hay ningúna ficha de película con ese identificador.");
        }


    }

    public static void doRequestToAPI(){
        System.out.println("Dime que rango de películas quieres obtener: ");
        System.out.println("Mínimo: ");
        int min = Utils.getScannerInt();
        System.out.println("Máximo: ");
        int max = Utils.getScannerInt();
        for (int i = min; i < max; i++) {
            JSONObject film_details = Utils.doRequest("movie", i, null);
            Movie movie = TheMovieDB.getMovieDetails(film_details);

            JSONObject film_credits = Utils.doRequest("movie", i, "/credits");
            ArrayList<Cast> casting = TheMovieDB.getMovieCasting(film_credits, i);
            movie.setCast(casting);
            System.out.println(movie.toString());
            for (Cast c : movie.getCast()) {
                JSONObject people_details = Utils.doRequest("person", c.getPeople_id(), null);
                People people = TheMovieDB.getPeopleDetails(people_details);
                people.saveOnDB();
            }
            movie.saveOnDB();
        }
    }

    public static void doTruncateDB(){
        query("execute", "DELETE FROM casting", null);
        query("execute", "DELETE FROM movie", null);
        query("execute", "DELETE FROM people", null);
    }

}
