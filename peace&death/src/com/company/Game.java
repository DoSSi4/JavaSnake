package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {

    private final long SPEED = 1_000_000_000;
    private final long speed = 15;
    private char ch = 'D';

    @Override
    public void start(Stage stage){
        Map map = new Map("level1");
        Scene scene = new Scene(map, map.getMapWidth() * Map.getUnit(), map.getMapHeight() * Map.getUnit());

        Food food = new Food(map);
        Snake snake = new Snake(map, food);


        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                default:
                case D:
                case RIGHT:	ch = 'D'; break;
                case S:
                case DOWN: ch = 'S'; break;
                case A:
                case LEFT: ch = 'A'; break;
                case W:
                case UP: ch = 'W'; break;
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= SPEED / speed) {
                    switch(ch) {
                        default:
                        case 'D': snake.moveRight(); break;
                        case 'S': snake.moveDown(); break;
                        case 'A': snake.moveLeft(); break;
                        case 'W': snake.moveUp(); break;
                    }
                    snake.update();
                    lastUpdate = now;
                }
            }
        };
        timer.start();

        stage.setScene(scene);
        stage.setTitle("Snake");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}