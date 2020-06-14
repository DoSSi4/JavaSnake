package com.company;
// JFX Library
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Construction Game with JFX Application
public class Game extends Application {
    private String codeString = "";
    private String undead = "OPS";

    private long SPEED = 1_000_000_000;
    private final long speed = 10;
    private char ch = 'D';

    private Map map;
    private int currentLevel = 0;

    private static int score = 0;
    private static Game instance;
    private Stage curStage;

    public static Game getInstance() {return instance;}

    @Override
    public void start(Stage stage){
        stage.setResizable(false);
        currentLevel = 0;
        curStage = stage;
        instance = this;
        menu(stage);
    }
    //function creating main menu
    private void menu(Stage stage){
        BorderPane bp = new BorderPane();
        Scene menuScene = new Scene(bp, 480, 480);

        menuScene.setOnKeyPressed(e -> code(e.getCode().toString()));

        stage.setScene(menuScene);

        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        Label name = new Label("SNAKE");
        name.setStyle(" -fx-padding:  100 0 50 0; -fx-font-size: 80;");
        vb.getChildren().add(name);
        bp.setTop(vb);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        Button startBtn = new Button("PLAY");
        Button recordBtn = new Button("RECORD");
        Button quitBtn = new Button("QUIT");
        vBox.getChildren().addAll( startBtn, recordBtn, quitBtn);

        bp.setCenter(vBox);

        startBtn.setOnAction(e -> { startgame(stage);});
        quitBtn.setOnAction(e -> Platform.exit());
        //creating board of Game from Main menu
        stage.setTitle("Snake");
        stage.show();
    }
    // function that start game
    public void startgame(Stage stage){
        stage.setResizable(false);
        currentLevel = 0;
        instance = this;
        Game.score = 0;

        map = new Map("level" + currentLevel);
        Scene scene = new Scene(map, map.getMapWidth() * Map.getUnit(), map.getMapHeight() * Map.getUnit());

        Food food = new Food(map);
        Snake snake = new Snake(map, food);
        //Score counter
        Label scoreLabel = new Label("Score: " + Game.score);
        scoreLabel.relocate(20, 20);
        map.getChildren().add(scoreLabel);

        Label goalLabel = new Label("Goal: " + map.getGoal());
        goalLabel.relocate(map.getMapWidth() * Map.getUnit() - 60, 20);
        map.getChildren().add(goalLabel);
        //Scenario of perssing keys
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                default:
                case D:
                case RIGHT:  ch = 'D'; break;
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
        // creating scene with title executable application
        stage.setScene(scene);
        stage.setTitle("Snake");
        stage.show();
    }

    public static void addScore(int i) {
        Game.score += i;
        getInstance().SPEED -= 500_000;

        if(Map.isPassed(Game.score)) {
            if(getInstance().currentLevel < 4) {
                getInstance().currentLevel++;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void code(String s) {
        if(s.length() == 1) {
            codeString += s;
            if(codeString.indexOf(undead) != -1) {
                codeString = "";
            }
        }
    }
}
