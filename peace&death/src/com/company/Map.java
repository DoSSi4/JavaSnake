package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
//Creating Map on JFX Panel
public class Map extends Pane {
    private static final int UNIT = 20; // declare 1 Unit on Map as 20 px;
    private int width; 
    private int height;
    private static int[][] map; //2D-array to create map
    private Position startPos;
    //Constructing Map with choosing level
    public Map(String level) {
        Scanner scan = new Scanner(System.in);
        try {
            scan = new Scanner(new File("maps/" + level));
        }catch(FileNotFoundException fnf) {}
        width = scan.nextInt();
        height = scan.nextInt();
        map = new int[height][width];

        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                map[i][j] = scan.nextInt();
                if(map[i][j] == 2) { // 2 in map means startPos of Head
                    startPos = new Position(j, i);
                }
                if(map[i][j] == 1) { // 1 in map means black border
                    Rectangle rec = new Rectangle(j*UNIT, i*UNIT, UNIT, UNIT);
                    getChildren().add(rec);
                }
                if(map[i][j] == 3) { // 3 just avoiding 
                    map[i][j] = 1;
                }
            }
        }

        scan.close();
    }
    //Getters for map
    public int getMapWidth(){
        return width;
    }
    public int getMapHeight(){
        return height;
    }
    public static int getUnit() {
        return UNIT;
    }
    public Position getStartPosition() {
        return startPos;
    }

    //Check method for creating snake
    public static boolean validate(int x, int y) {
        if(map[y][x] != 1) {return true;}
        else {return false;}
    }

}
