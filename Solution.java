import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Solution extends Application {
    static Map map;
    static String filename;
    static Node[][] nodes;
    static Pane pane;
    static HashMap<Node, Integer> livesOfWall = new HashMap<>();
    static ArrayList<Node> trees = new ArrayList<>();

    //First player
    static Player player = new MyPlayer();
    static int LIVE_PLAYER1 = 100;

    //Bot or Second Player
    static int LIVE_PLAYER2 = 4;
    static Bot bot = new Bot();


    static int WON1 = 0;
    static int WON2 = 0;

    //Connecting to client
    ServerSocket serverSocket;
    DataInputStream fromClient = null;
    DataOutputStream toClient = null;


    public static void main(String[] args) {
        filename = args[0]; //cmd
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Scanner input = new Scanner(new File(filename));

        pane = new Pane();
        ImageView block;



        Tank tank = new Tank();
        Rectangle space;

        Game game = null;
        // try{
        map = new Map(input);
        game = new Game(map);
        map.print();
        System.out.println(map.getMap()[0].length);

        // }
        // catch(InvalidMapException e){ // custom exception
        //     System.out.println(e.getMessage());
        //     System.exit(0);
        // }
        nodes = new Node[map.getSize()][map.getSize()];

        //==============
        //GUI of the MAP
        //==============

        game.addPlayer(player);
        game.addPlayer(bot);


        for (int i = 0; i < map.getMap().length; i++) {
            for (int j = 0; j < map.getMap()[i].length; j++) {

                if (map.getMap()[i][j] == 'P')
                {
                    tank.getTANK_GUI().setX(j * 64);
                    tank.getTANK_GUI().setY(i * 64);

                    tank.getTANK_GUI().setId("tank");
                    tank.getTANK_GUI().toBack();

                    pane.getChildren().add(tank.getTANK_GUI());


                    nodes[i][j] = tank.getTANK_GUI();


                }

                else if (map.getMap()[i][j] == 'R')
                {
                    bot.getTANK_GUI().setX(j * 64);
                    bot.getTANK_GUI().setY(i * 64);

                    bot.getTANK_GUI().setId("bot");
                    bot.getTANK_GUI().toBack();

                    pane.getChildren().add(bot.getTANK_GUI());


                    nodes[i][j] = bot.getTANK_GUI();
                }
                else if (map.getMap()[i][j] == 'S')
                {
                    block = new ImageView(new Image("wall.png"));
                    block.setFitHeight(64);
                    block.setFitWidth(64);

                    block.setId("wall");

                    block.setY(i * 64);
                    block.setX(j * 64);

                    pane.getChildren().add(block);
//                    block.toBack();

                    nodes[i][j] = block;
                }
                else if (map.getMap()[i][j] == 'B')
                {
                    block = new ImageView(new Image("brickWall.png"));
                    block.setFitHeight(64);
                    block.setFitWidth(64);

                    block.setId("brwall");

                    block.setY(i * 64);
                    block.setX(j * 64);

                    pane.getChildren().add(block);
//                    block.toBack();
                    livesOfWall.put(block, 4);


                    nodes[i][j] = block;
                }
                else if (map.getMap()[i][j] == 'T')
                {
                    block = new ImageView(new Image("trees.png"));
                    block.setFitHeight(64);
                    block.setFitWidth(64);

                    block.setId("tree");
                    block.setY(i * 64);
                    block.setX(j * 64);

                    pane.getChildren().add(block);
                    trees.add(block);
                    nodes[i][j] = block;
                }
                else if (map.getMap()[i][j] == 'W')
                {
                    block = new ImageView(new Image("water.png"));
                    block.setFitHeight(64);
                    block.setFitWidth(64);

                    block.setId("water");

                    block.setY(i * 64);
                    block.setX(j * 64);

                    pane.getChildren().add(block);

                    nodes[i][j] = block;
                }
                else
                {
                    space = new Rectangle(64, 64);
                    space.setY(i * 64);
                    space.setX(j * 64);



                    pane.getChildren().add(space);
                    space.toBack();
                    space.setId("space");

                    nodes[i][j] = space;
                }
            }
        }
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                if (nodes[i][j].getId().equals("tree"))
                {
                    nodes[i][j].toFront();
                }
            }
        }

        // Red Table
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: red; -fx-weight: bold");
        grid.setTranslateX(map.getSize() * 64);
        grid.setPadding(new Insets(20,20,20,20));
        grid.setTranslateY(0);
        grid.setPrefSize(200, map.getSize() * 64);

        Label wonPlayer1 = new Label("Player 1: ");
        Label wonPlayer2 = new Label("Player 2: ");

        Label count1 = new Label("0");
        Label count2 = new Label("0");

        grid.add(wonPlayer1, 0,0);
        grid.add(wonPlayer2, 0,1);

        grid.add(count1, 1, 0);
        grid.add(count2, 1, 1);

        pane.getChildren().add(grid);




        //Actions
        pane.setOnKeyPressed(e -> {

            switch (e.getCode())
            {
                case UP:
                    tank.moveUp();
                    break;

                case DOWN:

                    tank.moveDown();
                    break;

                case RIGHT:

                    tank.moveRight();
                    break;

                case LEFT:

                    tank.moveLeft();
                    break;

                case SPACE:
                    Bullet bullet = new Bullet(tank, pane);


                    pane.getChildren().add(bullet.getCircle());





                    switch (tank.getDirection())
                    {
                        case 1:

                            bullet.shootRight(tank.getTANK_GUI());
                            break;

                        case 2:

                            bullet.shootLeft(tank.getTANK_GUI());
                            break;

                        case 3:

                            bullet.shootUp(tank.getTANK_GUI());
                            break;

                        case 4:

                            bullet.shootDown(tank.getTANK_GUI());
                            break;
                    }
                    PathTransition animation = new PathTransition();
                    animation.setNode(bullet.getCircle());
                    animation.setDuration(Duration.millis(500));
                    animation.setPath(bullet.getLine());
                    animation.setCycleCount(1);
                    animation.play();
                    animation.setOnFinished(s -> pane.getChildren().remove(bullet.getCircle()));


            }

//            if (e.getCode() == KeyCode.L) {
//                botPlayer(bot);
//            }

        });



//        game.addPlayer(player);
//        game.addPlayer(bot);


        pane.setStyle("-fx-background-color: black");

        Scene scene = new Scene(pane, (map.getSize() * 64) + 200,map.getSize() * 64);

        primaryStage.setScene(scene);
        primaryStage.setTitle("TANK");
        primaryStage.show();
        pane.requestFocus();
        primaryStage.setResizable(false);

//        botPlayer(tank);

        Random random = new Random();


        new Thread(() -> {

            try {

                while (true) {
                    BulletBot bul = new BulletBot(bot.getTANK_GUI(), pane);

                    int rand = random.nextInt(5);
                    Platform.runLater(() -> {

                        pane.getChildren().add(bul.getCircle());

                        for (int i = 0; i < 4; i++) {

                            for (int j = 0; j < 3; j++) {

                                switch (i) {
                                    case 0:
                                        if (bot.getTANK_GUI().getX() + j * 64 == tank.getTANK_GUI().getX() &&
                                                bot.getTANK_GUI().getY() == tank.getTANK_GUI().getY()) {
                                            bul.shootRight();
                                            LIVE_PLAYER1 -= 1;
                                        }
                                        break;

                                    case 1:

                                        if (bot.getTANK_GUI().getX() - j * 64 == tank.getTANK_GUI().getX() &&
                                                bot.getTANK_GUI().getY() == tank.getTANK_GUI().getY()) {
                                            bul.shootLeft();
                                            LIVE_PLAYER1 -= 1;

                                        }
                                        break;

                                    case 2:

                                        if (bot.getTANK_GUI().getY() + j * 64 == tank.getTANK_GUI().getY() &&
                                                bot.getTANK_GUI().getX() == tank.getTANK_GUI().getX()) {
                                            bul.shootDown();
                                            LIVE_PLAYER1 -= 1;

                                        }
                                        break;

                                    case 3:

                                        if (bot.getTANK_GUI().getY() - j * 64 == tank.getTANK_GUI().getY() &&
                                                bot.getTANK_GUI().getX() == tank.getTANK_GUI().getX()) {
                                            bul.shootUp();
                                            LIVE_PLAYER1 -= 1;

                                        }
                                        break;
                                }
                            }
                        }
                        PathTransition animation = new PathTransition();
                        animation.setNode(bul.getCircle());
                        animation.setDuration(Duration.millis(300));
                        animation.setPath(bul.getLine());
                        animation.setCycleCount(1);
                        animation.play();
                        animation.setOnFinished(s -> pane.getChildren().remove(bul.getCircle()));
                        System.out.println(bot.getDirection());
                        if (rand == 1) {
                            bot.moveRight();
                        } else if (rand == 2) {
                            bot.moveLeft();

                        } else if (rand == 3) {
                            bot.moveUp();
                        } else {
                            bot.moveDown();
                        }




                    });
                    Platform.runLater(() -> {
                        try {
                            if (LIVE_PLAYER2 == 0) {
                                WON1 += 1;
                                LIVE_PLAYER1 = 3;
                                LIVE_PLAYER2 = 3;
                                System.out.println("Player 2 dead");

                                count1.setText(Integer.toString(WON1));
//                                Thread.sleep(2000);
                                bot.getTANK_GUI().setDisable(true);
                                pane.setDisable(true);
                                tank.getTANK_GUI().setDisable(true);
                            } else if (LIVE_PLAYER1 == 0) {
                                WON2 += 1;
                                LIVE_PLAYER1 = 3;
                                LIVE_PLAYER2 = 3;
                                System.out.println("Player 1 dead");

                                count2.setText(Integer.toString(WON2));
                                bot.getTANK_GUI().setDisable(true);
                                pane.setDisable(true);
                                tank.getTANK_GUI().setDisable(true);
                            }
                        }
                        catch (Exception es) {
                            es.printStackTrace();
                        }
                    });
                    if (WON1 == 3 ^ WON2 == 3) {
                        break;
                    }

                    Thread.sleep(500);
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(8000);

                Socket socket = serverSocket.accept();

                toClient = new DataOutputStream(socket.getOutputStream());
                fromClient = new DataInputStream(socket.getInputStream());


            }
            catch (IOException ex) {
                ex.printStackTrace();
            }


        }).start();
    }
    public void botPlayer(Tank tank) {
        Random random = new Random();

        new Thread(() -> {

            try {

                while (true) {
                    BulletBot bul = new BulletBot(bot.getTANK_GUI(), pane);

                    int rand = random.nextInt(5);
                    Platform.runLater(() -> {

                        pane.getChildren().add(bul.getCircle());

                        for (int i = 0; i < 4; i++) {

                            for (int j = 0; j < 3; j++) {

                                switch (i) {
                                    case 0:
                                        if (bot.getTANK_GUI().getX() + j * 64 == tank.getTANK_GUI().getX() &&
                                                bot.getTANK_GUI().getY() == tank.getTANK_GUI().getY()) {
                                            bul.shootRight();
                                            LIVE_PLAYER1 -= 1;
                                        }
                                        break;

                                    case 1:

                                        if (bot.getTANK_GUI().getX() - j * 64 == tank.getTANK_GUI().getX() &&
                                                bot.getTANK_GUI().getY() == tank.getTANK_GUI().getY()) {
                                            bul.shootLeft();
                                            LIVE_PLAYER1 -= 1;

                                        }
                                        break;

                                    case 2:

                                        if (bot.getTANK_GUI().getY() + j * 64 == tank.getTANK_GUI().getY() &&
                                                bot.getTANK_GUI().getX() == tank.getTANK_GUI().getX()) {
                                            bul.shootDown();
                                            LIVE_PLAYER1 -= 1;

                                        }
                                        break;

                                    case 3:

                                        if (bot.getTANK_GUI().getY() - j * 64 == tank.getTANK_GUI().getY() &&
                                                bot.getTANK_GUI().getX() == tank.getTANK_GUI().getX()) {
                                            bul.shootUp();
                                            LIVE_PLAYER1 -= 1;

                                        }
                                        break;
                                }
                            }
                        }
                        PathTransition animation = new PathTransition();
                        animation.setNode(bul.getCircle());
                        animation.setDuration(Duration.millis(300));
                        animation.setPath(bul.getLine());
                        animation.setCycleCount(1);
                        animation.play();
                        animation.setOnFinished(s -> pane.getChildren().remove(bul.getCircle()));
                        System.out.println(bot.getDirection());
                        if (rand == 1) {
                            bot.moveRight();
                        }

                        else if (rand == 2) {
                            bot.moveLeft();

                        }

                        else if (rand == 3) {
                            bot.moveUp();
                        }

                        else {
                            bot.moveDown();
                        }


                    });

                    if (LIVE_PLAYER2 == 0) {
                        break;
                    }
                    Thread.sleep(500);
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}

class Position {
    private int x;
    private int y;
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getX()
    {
        return x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getY()
    {
        return y;
    }
    public boolean equals(Position position)
    {
        boolean equality = (x == position.getX() && y == position.getY());
        return equality;
    }
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }
}
class Map {
    private java.util.Scanner input;
    private char[][] map;
    private int size;

    public Map(java.util.Scanner input)  {
        try {
            size = input.nextInt();
            map = new char[size][size];
            if (size == 0) {
                throw new InvalidMapException("Map size can not be zero");
            }
            String skip = input.nextLine();
            ArrayList<String> array = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                skip = input.nextLine();
                if (skip.isEmpty())
                {
                    throw new InvalidMapException("Not enough map elements");
                }
                else
                {
                    map[i] = skip.replaceAll(" ", "").toCharArray();
                }

                if (map[i].length != size)
                {
                    throw new InvalidMapException("Not enough map elements");
                }

            }
        }
        catch (InvalidMapException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    public void print() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
    public int getSize() {
        return size;
    }
    public char getValueAt(int row, int col) {
        return map[row][col];
    }
    public char[][] getMap() {
        return map;
    }
}
class InvalidMapException extends Exception
{
    public InvalidMapException(String ex)
    {
        super(ex);
    }
}
interface Player
{
    public void setMap(Map map);
    public void moveRight();
    public void moveLeft();
    public void moveUp();
    public void moveDown();
    public Position getPosition();
}
class MyPlayer implements Player {
    private Map map;
    private Position position;
    @Override
    public void setMap(Map map) {
        this.map = map;
        for (int i = 0; i < map.getMap().length; i++) {
            for (int j = 0; j < map.getMap()[i].length; j++) {
                if (map.getMap()[i][j] == 'P') {
                    position = new Position(j, i);
                }
            }
        }
    }
    @Override
    public void moveRight() {
        if (position.getX() == map.getSize() - 1) {
            position.setX(position.getX());
        }
        else if (map.getMap()[position.getY()][position.getX() + 1] == '1') {
            position.setX(position.getX());
        }
        else {
            position.setX(position.getX() + 1);
        }
    }
    @Override
    public void moveLeft() {
        if (position.getX() == 0) {
            position.setX(position.getX());
        }
        else if (map.getMap()[position.getY()][position.getX() - 1] == '1') {
            position.setX(position.getX());
        }
        else {
            position.setX(position.getX() - 1);
        }
    }
    @Override
    public void moveUp() {
        if (position.getY() == 0) {
            position.setY(position.getY());
        }
        else if (map.getMap()[position.getY() - 1][position.getX()] == '1') {
            position.setY(position.getY());
        }
        else {
            position.setY(position.getY() - 1);
        }
    }
    @Override
    public void moveDown() {
        if (position.getY() == map.getSize() - 1) {
            position.setY(position.getY());
        }
        else if (map.getMap()[position.getY() + 1][position.getX()] == '1') {
            position.setY(position.getY());
        }
        else {
            position.setY(position.getY() + 1);
        }
    }
    @Override
    public Position getPosition() {
        return position;
    }
}
class Game {
    private Map map;
    private Player player;
    public Game(Map map) {
        this.map = map;
    }
    public void setMap(Map map) {
        this.map = map;
    }
    public void addPlayer(Player player) {
        this.player = player;
        player.setMap(this.map);
    }
}