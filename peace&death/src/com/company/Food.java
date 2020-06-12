package com.company;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
//Here we use Colors and Circles from JFX

public class Food extends Circle { //Food gonna be small point on plane
    private Position pos;
    public Food(Map map){ //Creating our Food-point in different positions with color Red
        int x, y;
        do {
            y = (int)(Math.random() * map.getMapHeight());
            x = (int)(Math.random() * map.getMapWidth());
        }while(!Map.validate(x, y));

        pos = new Position(x, y);

        setCenterX(x * Map.getUnit() + Map.getUnit()/2);
        setCenterY(y * Map.getUnit() + Map.getUnit()/2);
        setRadius(Map.getUnit()/4);
        setFill(Color.RED);
    }
   //getter of new Position
    public Position getPos() {
        return pos;
    }
}
