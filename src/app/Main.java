package app;

import utils.Database;
import utils.Utils;

import java.util.Scanner;

/**
 * Created by Lluís Bayer Soler on 14/11/16.
 */
public class Main {

    public static void main(String[] args) {
        Database.init();
        while(true){
            System.out.println("-----------------------------------------------");
            System.out.println("--------------- MOVIE API + SQL ---------------");
            System.out.println("-------------- LLUÍS BAYER SOLER --------------");
            System.out.println("-------------------- MENÚ ---------------------");
            System.out.println("1. Listado de películas");
            System.out.println("2. Ficha de película");
            System.out.println("3. Listado de actores");
            System.out.println("4. Ficha de actor");
            System.out.println("5. Sincronizar con la API");
            System.out.println("6. Truncate tables");
            System.out.println("7. Salir del programa");
            System.out.println("- Qué opción quieres realizar? :");
            int option = Utils.getScannerInt();
            switch(option){
                case 1:
                    Menu.getAllMovies();
                    break;

                case 2:
                    Menu.getMovie();
                    break;

                case 3:
                    Menu.getAllPeoples();
                    break;

                case 4:
                    Menu.getPeople();
                    break;

                case 5:
                    Menu.doRequestToAPI();
                    break;

                case 6:
                    Menu.doTruncateDB();
                    break;

                case 7:
                    System.exit(0);
                    break;
            }
        }
    }
}
