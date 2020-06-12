package com.company;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Food extends Circle {
    private Position pos;
    public Food(Map map){
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
    public Position getPos() {
        return pos;
    }
}
