package com.company;
// Import libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
//Creating Map in JFX Panel
public class Map extends Pane {
    private static final int UNIT = 20; //One cell in 20 px;
    private int width;
    private int height;
    private static int goal = 0;
    private static int[][] map; //2D array with Map

    private Position startPos;

    public Map(String level) {
        Scanner scan = new Scanner(System.in);
        try {
            scan = new Scanner(new File("maps/" + level));
        }catch(FileNotFoundException fnf) {}
        width = scan.nextInt();
        height = scan.nextInt();
        goal = scan.nextInt();
        map = new int[height][width];

        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                map[i][j] = scan.nextInt();
                if(map[i][j] == 2) {
                    startPos = new Position(j, i);
                }
                if(map[i][j] == 1) {
                    Rectangle rec = new Rectangle(j*UNIT, i*UNIT, UNIT, UNIT);
                    getChildren().add(rec);
                }
                if(map[i][j] == 3) {
                    map[i][j] = 1;
                }
            }
        }
        scan.close();
    }
//Setters and Getters to use in other classes
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

    public static boolean isPassed(int score) {
        if(score >= goal)
            return true;
        return false;
    }
    public int getGoal() {
        return goal;
    }

    public static boolean validate(int x, int y) {
        if(map[y][x] != 1)
            return true;
        return false;
    }

}
