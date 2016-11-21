package utils;

import api.Cast;
import api.Movie;
import api.People;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lluís Bayer Soler on 14/11/16.
 */
public class Database {

    static Connection c = null;
    static Statement stmt = null;
    static final String DRIVER = "org.sqlite.JDBC";
    static final String CONNECTION = "jdbc:sqlite:moviedb.db";

    public static void init(){
        query("execute","CREATE TABLE IF NOT EXISTS movie (id INT PRIMARY KEY NOT NULL, title TEXT NOT NULL, release_date DATE)", null);
        query("execute","CREATE TABLE IF NOT EXISTS casting (id INTEGER PRIMARY KEY AUTOINCREMENT, character TEXT, credit_id TEXT, name TEXT, people_id INT, movie_id INT, FOREIGN KEY(movie_id) REFERENCES movie(id), FOREIGN KEY(people_id) REFERENCES people(id))", null);
        query("execute","CREATE TABLE IF NOT EXISTS people (id INT PRIMARY KEY, biography DATE, birthday DATE, deathday DATE, gender INT, homepage TEXT, name TEXT)", null);
    }

    public static String query(String type, String sql, String[] params){
        try {
            c = DriverManager.getConnection(CONNECTION);
            stmt = c.createStatement();
            if(type.equals("execute")) {
                stmt.executeUpdate(sql);
            }else if(type.equals("insert")){
                if(params.length > 0){
                    PreparedStatement preparedStmt = c.prepareStatement(sql);
                    for (int i = 0; i < params.length; i++) {
                        preparedStmt.setString (i+1, params[i].toString());
                    }
                    preparedStmt.execute();
                }
            }else if(type.equals("count")){
                ResultSet rs = stmt.executeQuery(sql);
                String response = "";
                while ( rs.next() ) {
                    response += rs.getInt(1);
                }
                return response;
            }else{
                ResultSet rs = stmt.executeQuery(sql);
                String response = "";
                while ( rs.next() ) {
                    response += rs.getRow();
                }
                return response;
            }
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return null;
    }

    public static List<People> getPeoples(int id){
        List<People> peoples = new ArrayList<>();
        String query;
        if(id != 0) {
            query = "select * from people where id = "+id+";";
        }else{
            query = "select * from people;";
        }
        ResultSet rs = doSelect(query);
        try {
            while(rs.next()){
                People people = new People();
                people.setId( rs.getInt("id") );
                people.setName( rs.getString("name") );
                people.setBiography( rs.getString("biography") );
                people.setBirthday( rs.getString("birthday") );
                people.setDeathday( rs.getString("deathday") );
                people.setGender( rs.getInt("gender") );
                people.setHomepage( rs.getString("homepage") );
                peoples.add(people);
            }

            return peoples;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<Movie> getMovies(int id){
        List<Movie> movies = new ArrayList<>();
        String query;
        if(id != 0) {
            query = "select * from movie where id = "+id+";";
        }else{
            query = "select * from movie;";
        }
        ResultSet rs = doSelect(query);
        try {
            while(rs.next()){
                Movie movie = new Movie();
                movie.setTitle( rs.getString("title") );
                movie.setId( rs.getInt("id") );
                movie.setRelease_date( rs.getString("release_date") );
                movie.setCast(getCasting(movie.getId()));
                movies.add(movie);
            }

            return movies;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Cast> getCasting(int movie_id){
        ArrayList<Cast> castings = new ArrayList<>();
        String query = "select * from casting where movie_id = "+movie_id+";";
        ResultSet rs = doSelect(query);
        try {
            while(rs.next()) {
                Cast casting = new Cast();
                casting.setCharacter( rs.getString("character") );
                casting.setMovie_id( rs.getInt("movie_id") );
                casting.setCredit_id( rs.getString("credit_id") );
                casting.setName( rs.getString("name") );
                casting.setId( rs.getInt("id") );
                castings.add(casting);
            }

            return castings;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getMoviesByPeople(int people_id){
        ArrayList<String> movies = new ArrayList<>();
        String query;
        query = "select movie.*, casting.character from movie\n" +
                "join casting ON casting.people_id="+people_id+" AND casting.movie_id = movie.id;";
        ResultSet rs = doSelect(query);
        try {
            while(rs.next()){
                Movie movie = new Movie();
                movie.setTitle( rs.getString("title") );
                movie.setId( rs.getInt("id") );
                movie.setRelease_date( rs.getString("release_date") );
                movies.add(movie.prettyPrint() + " como " + rs.getString("character"));
            }

            return movies;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet doSelect(String query){
        try {
            c = DriverManager.getConnection(CONNECTION);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException e){}
        return  null;
    }

}
