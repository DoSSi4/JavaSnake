package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Game extends Application {

    private long SPEED = 1_000_000_000;
    private final long speed = 10;
    private char ch = 'D';

    private Map map;
    private int currentLevel = 0;

    private static int score = 0;
    private static Game instance;

    public static Game getInstance() {return instance;}


    @Override
    public void start(Stage stage){
        stage.setResizable(false);
        currentLevel = 0;
        instance = this;
        Game.score = 0;

        map = new Map("level" + currentLevel);
        Scene scene = new Scene(map, map.getMapWidth() * Map.getUnit(), map.getMapHeight() * Map.getUnit());

        Food food = new Food(map);
        Snake snake = new Snake(map, food);

        Label scoreLabel = new Label("Score: " + Game.score);
        scoreLabel.relocate(20, 20);
        map.getChildren().add(scoreLabel);

        Label goalLabel = new Label("Goal: " + map.getGoal());
        goalLabel.relocate(map.getMapWidth() * Map.getUnit() - 60, 20);
        map.getChildren().add(goalLabel);

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

        scoreLabel.setText("Score: " + Game.score);

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
