import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class Client extends Application {

    Socket socket;
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    private String host = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane pane = new GridPane();
        Socket socket = new Socket(host, 8000);

        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());

        GridPane stackPane = new GridPane();

        Button w = new Button("W");
        pane.add(w, 1, 0);

        Button s = new Button("S");
        pane.add(s, 1,1);

        Button d = new Button("D");
        pane.add(d, 2,1);

        Button a = new Button("A");
        pane.add(a, 0,1);


        w.setOnAction(e -> {
            try {
                toServer.writeUTF(w.getText());
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        s.setOnAction(e -> {
            try {
                toServer.writeUTF(s.getText());
            }
            catch (IOException es) {
                es.printStackTrace();
            }
        });

        d.setOnAction(e -> {
            try {
                toServer.writeUTF(d.getText());
                toServer.flush();
            }
            catch (IOException ed) {
                ed.printStackTrace();
            }
        });

        a.setOnAction(e -> {
            try {
                toServer.writeUTF(a.getText());
            }
            catch (IOException ea) {
                ea.printStackTrace();
            }
        });
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("S");
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) {
        launch(args);
    }
}