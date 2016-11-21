package api;

import utils.Database;

/**
 * Created by Lluís Bayer Soler on 14/11/16.
 */
public class People {
    int id;
    String biography;
    String birthday;
    String deathday;
    int gender;
    String homepage;
    String name;

    public People() {
    }

    public People(int id, String biography, String birthday, String deathday, int gender, String homepage, String name) {
        this.id = id;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.gender = gender;
        this.homepage = homepage;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void saveOnDB(){
        if(!existsOnDB()) {
            Database.query("insert", "INSERT INTO people (id, biography, birthday, deathday, gender, homepage, name) VALUES (?, ?, ?, ?, ?, ?, ?);",
                    new String[]{String.valueOf(this.id), this.biography, this.birthday, this.deathday, String.valueOf(this.gender), this.homepage, this.name});
        }
    }

    public boolean existsOnDB(){
        String g = Database.query("count", "SELECT COUNT(*) FROM people where id="+this.id, null);
        if(g.equals("0")){
            return false;
        }else{
            return true;
        }
    }

    public String getAttributeGender(){
        if(this.gender == 0){
            return "Desconocido";
        }else if(this.gender == 1){
            return "Mujer";
        }else{
            return "Hombre";
        }
    }

    public String getAttributeBirthday(){
        if(this.birthday.isEmpty()){
            return "(sin información)";
        }else{
            return this.birthday;
        }
    }


    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", biography='" + biography + '\'' +
                ", birthday='" + birthday + '\'' +
                ", deathday='" + deathday + '\'' +
                ", gender=" + gender +
                ", homepage='" + homepage + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String prettyPrint(){
        return "id: "+id+" - "+name+" nacido en "+getAttributeBirthday()+" de sexo "+getAttributeGender()+" ";
    }

}
