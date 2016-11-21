package api;

import utils.Database;
import java.util.ArrayList;

/**
 * Created by Lluís Bayer Soler on 14/11/16.
 */
public class Movie {
    Integer id;
    String title;
    String release_date;
    ArrayList<Cast> cast = new ArrayList<>();

    public Movie() {
    }

    public Movie(Integer id, String title, String release_date) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public void saveOnDB(){
        if(!existsOnDB()) {
            Database.query("insert", "INSERT INTO movie (id, title, release_date) VALUES (?, ?, ?);", new String[]{String.valueOf(this.id), this.title, this.release_date});
            for (Cast c : cast) {
                c.saveOnDB();
            }
        }
    }

    public boolean existsOnDB(){
        String g = Database.query("count", "SELECT COUNT(*) FROM movie where id="+this.id, null);
        if(g.equals("0")){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    @Override
    public String toString() {
        return "Movie("+id+"){" +
                "title='" + title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", castingSize=" + cast.size() +
                '}';
    }

    public String prettyPrint(){
        return "Movie("+id+") "+title+" ("+release_date+")";
    }

}
