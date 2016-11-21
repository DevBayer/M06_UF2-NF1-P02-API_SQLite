package api;

import utils.Database;

/**
 * Created by Lluís Bayer Soler on 14/11/16.
 */
public class Cast {
    String character;
    String credit_id;
    int id;
    int people_id;
    String name;
    int movie_id;

    public Cast() {
    }

    public Cast(String character, String credit_id, int people_id, String name, int movie_id) {
        this.character = character;
        this.credit_id = credit_id;
        this.people_id = people_id;
        this.name = name;
        this.movie_id = movie_id;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getPeople_id() {
        return people_id;
    }

    public void saveOnDB() {
        if(!existsOnDB()) {
            Database.query("insert",
                    "INSERT INTO casting (people_id, character, credit_id, name, movie_id) VALUES (?, ?, ?, ?, ?);",
                    new String[]{String.valueOf(this.people_id),this.character, this.credit_id, this.name, String.valueOf(this.movie_id)});
        }
    }

    public boolean existsOnDB(){
        String g = Database.query("count", "SELECT COUNT(*) FROM casting where id="+this.id, null);
        if(g.equals("0")){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Cast{" +
                "character='" + character + '\'' +
                ", credit_id='" + credit_id + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", movie_id=" + movie_id +
                '}';
    }

    public String prettyPrint(){
        return name + " hace de "+ character;
    }

}
