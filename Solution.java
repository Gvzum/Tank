import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    Game game = null;

//    for bot datas
    int bot_mind = 0;



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

        Player player = new MyPlayer();
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
                else if (map.getMap()[i][j] == 'S')
                {
                    block = new ImageView(new Image("wall.png"));
                    block.setFitHeight(64);
                    block.setFitWidth(64);

                    block.setId("wall");

                    block.setY(i * 64);
                    block.setX(j * 64);

                    pane.getChildren().add(block);

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





                    switch (Tank.direction)
                    {
                        case 1:

                            bullet.shootRight();
                            break;

                        case 2:

                            bullet.shootLeft();
                            break;

                        case 3:

                            bullet.shootUp();
                            break;

                        case 4:

                            bullet.shootDown();
                            break;
                    }
                    PathTransition animation = new PathTransition();
                    animation.setNode(bullet.getCircle());
                    animation.setDuration(Duration.millis(500));
                    animation.setPath(bullet.getLine());
                    animation.setCycleCount(1);
                    animation.play();
                    animation.setOnFinished(s -> pane.getChildren().remove(bullet.getCircle()));

                case ENTER:
                    Bot bot = new Bot();
                    game.addPlayer(bot);
                    pane.getChildren().add(bot.getTANK_GUI());
                    bot.getTANK_GUI().setX(0);
                    bot.getTANK_GUI().setY(0);

                    for (int i = 0; i < 10; i++) {
                        Bullet bullet1 = new Bullet(bot, pane);
                        bullet1.shootDown();
                    }
                    break;
            }
        });


        game.addPlayer(player);
        Position playerPosition = player.getPosition();


        pane.setStyle("-fx-background-color: black");

        Scene scene = new Scene(pane, map.getSize() * 64,map.getSize() * 64);




        primaryStage.setScene(scene);
        primaryStage.setTitle("TANK");
        primaryStage.show();
        pane.requestFocus();

        Bot bot = new Bot();
        pane.getChildren().add(bot.getTANK_GUI());
        bot.getTANK_GUI().setX(0);
        bot.getTANK_GUI().setY(0);
        game.addPlayer(bot);

        Random random = new Random();

        new Thread(() -> {
            try {
                while (true) {
                    bot_mind = random.nextInt(4);
                    Platform.runLater(() -> {

                        if (bot_mind == 1) {
                            bot.moveRight();
                        }
                        else if (bot_mind == 2) {
                            bot.moveLeft();
                        }
                        else if (bot_mind == 3) {
                            bot.moveUp();
                        }
                        else {
                            bot.moveDown();
                        }
                    });

                    Thread.sleep(200);
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

    public Map(java.util.Scanner input) throws InvalidMapException, FileNotFoundException {
        try {
            // File file = new File(filename);
            // Scanner input = new Scanner(file);
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