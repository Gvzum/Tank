import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Tester extends Application {

    private static final String[] sport = {"PUSH", "RUN", "MUSCLE", "BICEPS", "BAR"};
    private static final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int next = 0;
    private int counter = 0;
    private LinkedList<Integer> index = new LinkedList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: black");
        startDrow(pane);

        Button enter = new Button("START");

        pane.getChildren().add(enter);
        enter.setOnAction(e -> {
            pane.setStyle("-fx-background-color: white");
        });


        Label label = new Label("Guess the word: ");
        label.setTranslateX(700);
        label.setTranslateY(500);
        pane.getChildren().add(label);

        Label guess = new Label(sport[0].replaceAll("[A-Z]", "*"));

        guess.setTranslateX(850);
        guess.setTranslateY(500);
        pane.getChildren().add(guess);

        Label misses = new Label("Missed letters: ");
        misses.setTranslateX(700);
        misses.setTranslateY(650);
        pane.getChildren().add(misses);

        GridPane grid = new GridPane();
        int count = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (count == 26) {
                    break;
                }
                Button bt = creat_button(alphabet[count]);
                bt.setOnAction(e -> {
                    if (sport[next].indexOf(bt.getText().charAt(0)) == -1) {
                        misses.setText(misses.getText() + bt.getText());

                    }
                    else {
                        guess.setText("");
                        index.add(sport[next].indexOf(bt.getText().charAt(0)));
                        int sum = 0;
                        for (int k = 0; k < sport[next].length(); k++) {
                            if (sum < index.size() && index.get(sum) == k) {
                                guess.setText(guess.getText() + sport[next].charAt(index.get(sum)));
                                sum++;
                            }
                            else {
                                guess.setText(guess.getText() + "*");
                            }
                        }
                        counter++;
                    }

                    if (counter == sport[next].length() - 1) {
                        next++;
                        counter = 0;
                    }
                });
                bt.setPickOnBounds(false);
                grid.add(bt, i, j);
                count++;
            }
        }

        pane.getChildren().add(grid);
        grid.setTranslateX(800);
        grid.setTranslateY(200);



        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(pane, 1200, 900));
        primaryStage.setTitle("Hangover");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Button creat_button(String ch) {
        return new Button(ch);
    }

    public void startDrow(Pane pane) {
        //Bottom line
        Line bottomLine = new Line();
        bottomLine.setStartX(50);
        bottomLine.setStartY(750);

        bottomLine.setEndX(250);
        bottomLine.setEndY(750);

        bottomLine.setStrokeWidth(5);

        pane.getChildren().add(bottomLine);


        // Vertical line
        Line verLine = new Line();
        verLine.setStartX(150);
        verLine.setStartY(750);

        verLine.setEndX(150);
        verLine.setEndY(150);

        verLine.setStrokeWidth(5);

        pane.getChildren().add(verLine);

        //Horizontal line

        Line horLine = new Line();
        horLine.setStartX(150);
        horLine.setStartY(150);

        horLine.setEndX(350);
        horLine.setEndY(150);

        horLine.setStrokeWidth(5);

        pane.getChildren().add(horLine);

        //====================================
    }
}
