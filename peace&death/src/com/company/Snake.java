package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Snake extends Pane{
    private Circle head;
    private boolean alive = true;
    private final int UNIT;
    private Position headPos;
    private Position direction;
    private Position lastPos;

    private List<Body> bodyParts;
    private Food food;
    private Map map;


    public Snake(Map map, Food food) {
        this(map.getStartPosition());
        this.map = map;
        this.food = food;
        map.getChildren().add(this);
        map.getChildren().add(food);
    }

    public Snake(Position start) {
        headPos = start;
        UNIT = Map.getUnit();
        head = new Circle(headPos.getX() * UNIT + UNIT/2, headPos.getY() * UNIT + UNIT/2, UNIT/2, Color.DARKGREEN);
        direction = new Position(1, 0);
        getChildren().add(head);
        bodyParts = new ArrayList<Body>();
    }
    public void moveRight() {
        if(direction.getX()!= -1) {
            direction = new Position(1, 0);
        }
    }
    public void moveDown() {
        if(direction.getY()!= -1) {
            direction = new Position(0, 1);
        }
    }
    public void moveLeft() {
        if(direction.getX()!= 1) {
            direction = new Position(-1, 0);
        }
    }
    public void moveUp() {
        if(direction.getY()!= 1) {
            direction = new Position(0, -1);
        }
    }
    public void update() {
        if(alive) {
            lastPos = new Position(headPos.getX(), headPos.getY());
            int newX = headPos.getX() + direction.getX();
            int newY = headPos.getY() + direction.getY();
            if(newX < 0)	newX = map.getMapWidth() - 1;
            if(newX >= map.getMapWidth()) newX = 0;
            if(newY < 0)	newY = map.getMapHeight() - 1;
            if(newY >= map.getMapHeight()) newY = 0;
            headPos.setX(newX);
            headPos.setY(newY);
            head.setCenterX(headPos.getX() * Map.getUnit() + Map.getUnit()/2);
            head.setCenterY(headPos.getY() * Map.getUnit() + Map.getUnit()/2);


            for(Body body : bodyParts) {
                Position tmp = new Position(body.getPos().getX(), body.getPos().getY());
                body.setCenterX(lastPos.getX() * Map.getUnit() + Map.getUnit()/2);
                body.setCenterY(lastPos.getY() * Map.getUnit() + Map.getUnit()/2);
                body.setPos(lastPos);
                lastPos = new Position(tmp.getX(), tmp.getY());
            }

            checkColision();
        }
    }



    private void checkColision() {
        if (!Map.validate(headPos.getX(), headPos.getY())) {
            alive = false;
        } else if (food.getPos().getX() == headPos.getX() && food.getPos().getY() == headPos.getY()) {
            map.getChildren().remove(food);
            food = new Food(map);
            map.getChildren().add(food);
            addBodyPart();
        }
        for (Body body : bodyParts) {
            if (body.getPos().getX() == headPos.getX() && body.getPos().getY() == headPos.getY()) {
                alive = false;
            }
        }
        if (alive == false) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost/scoretable" +
                                "user=root&password="
                );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("INSERT INTO `scoretable` (`nickname`, `score`) VALUES ('', '');");

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void addBodyPart() {
        Body body = new Body(lastPos);
        bodyParts.add(body);
        getChildren().add(body);
    }

    private

    class Body extends Circle{
        private Position pos;
        Body(Position pos){
            super(pos.getX() * Map.getUnit() + Map.getUnit()/2, pos.getY() * Map.getUnit() + Map.getUnit()/2, Map.getUnit()/2, Color.BLACK);
            setPos(pos);
        }
        public Position getPos() {
            return pos;
        }
        public void setPos(Position pos) {
            this.pos = new Position(pos.getX(), pos.getY());
        }
    }
}
