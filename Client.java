import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.*;
import java.io.*;

public class Client extends Application {

    Socket socket;
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane();


    }

    public static void main(String[] args) {


    }
}