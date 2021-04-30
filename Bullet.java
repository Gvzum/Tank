import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.nio.file.Paths;

public class Bullet {
    
    private Tank tank; 
    private Circle circle;
    private Pane pane;
    private Line lineBullet = new Line();
    private MediaPlayer shootAudio = new MediaPlayer(new Media(Paths.get("vystrel-tanka.mp3").toUri().toString()));

    public Bullet(Tank tank, Pane pane) {
        this.tank = tank; 
        this.pane = pane;
        this.circle = new Circle(9);
        this.circle.setFill(Color.ORANGERED);

    }

    public Circle getCircle()
    {
        return circle;
    }

    public void shootRight(Rectangle tank)
    {
        shootAudio.play();

        int times = (int)(tank.getX() / 64);

        int a = times;
        boolean isWall = true;

        for (int i = times; i < times + 3; i++) {



            if (i == Solution.nodes.length - 1)
            {
                lineBullet.setStartY(tank.getY() + 32);
                lineBullet.setStartX(tank.getX() + 64);

                lineBullet.setEndY(tank.getY() + 32);
                lineBullet.setEndX(tank.getX() + (times + i - 1) * 64);

                isWall = true;

                break;
            }
            a++;
            if (Solution.nodes[(int)tank.getY() / 64][a].getId().equals("wall"))
            {
                lineBullet.setStartY(tank.getY() + 32);
                lineBullet.setStartX(tank.getX() + 64);

                lineBullet.setEndY(tank.getY() + 32);
                lineBullet.setEndX((a) * 64);

                isWall = true;
                break;
            }

            if (Solution.nodes[(int)tank.getY() / 64][a].getId().equals("brwall"))
            {
                lineBullet.setStartY(tank.getY() + 32);
                lineBullet.setStartX(tank.getX() + 64);

                lineBullet.setEndY(tank.getY() + 32);
                lineBullet.setEndX((a) * 64);

                Solution.livesOfWall.put(Solution.nodes[(int)tank.getY() / 64][a], Solution.livesOfWall.get(Solution.nodes[(int)tank.getY() / 64][a]) - 1);

                isWall = true;

                if (Solution.livesOfWall.get(Solution.nodes[(int)tank.getY() / 64][a]) == 0)
                {
                    Solution.nodes[(int)tank.getY() / 64][a].setId("space");
                    Solution.pane.getChildren().remove(Solution.nodes[(int)tank.getY() / 64][a]);
                }
                break;
            }
            if (tank.getX() + (64 * a) == Solution.bot.getTANK_GUI().getX()) {

                lineBullet.setStartY(tank.getY() + 32);
                lineBullet.setStartX(tank.getX() + 64);

                lineBullet.setEndY(tank.getY() + 32);
                lineBullet.setEndX((a) * 64);

                Solution.LIVE_PLAYER2 -= 1;

                isWall = true;

                if (Solution.LIVE_PLAYER2 == 0) {
                    Solution.pane.getChildren().remove(Solution.bot.getTANK_GUI());
                }
            }
            else
            {
                this.circle.toFront();
                isWall = false;
            }

        }

        for (int i = 0; i < Solution.trees.size(); i++) {
            Solution.trees.get(i).toFront();
        }
        if (!isWall)
        {
            lineBullet.setStartY(tank.getY() + 32);
            lineBullet.setStartX(tank.getX() + 64);

            lineBullet.setEndY(tank.getY() + 32);
            lineBullet.setEndX(tank.getX() + 4 * 64);
        }

    }

    public void shootLeft(Rectangle tank)
    {
        shootAudio.play();

        int times = (int)(tank.getX() / 64);

        int a = times;
        boolean isWall = true;
        int start = -64;

        for (int i = times; i > times - 3; i--) {



            if (i == 0)
            {
                lineBullet.setStartY(tank.getY() + 32);
                lineBullet.setStartX(tank.getX());

                lineBullet.setEndY(tank.getY() + 32);
                lineBullet.setEndX(tank.getX() - (times + 1) * 64);
                isWall = true;

                break;
            }
            a--;
            if (Solution.nodes[(int)tank.getY() / 64][a].getId().equals("wall"))
            {
                lineBullet.setStartY(tank.getY() + 32);
                lineBullet.setStartX(tank.getX());

                lineBullet.setEndY(tank.getY() + 32);
                lineBullet.setEndX((a) * 64 + 64);

                isWall = true;
                break;
            }

            if (Solution.nodes[(int)tank.getY() / 64][a].getId().equals("brwall"))
            {
                lineBullet.setStartY(tank.getY() + 32);
                lineBullet.setStartX(tank.getX());

                lineBullet.setEndY(tank.getY() + 32);
                lineBullet.setEndX((a) * 64 + 64);

                Solution.livesOfWall.put(Solution.nodes[(int)tank.getY() / 64][a], Solution.livesOfWall.get(Solution.nodes[(int)tank.getY() / 64][a]) - 1);

                isWall = true;

                if (Solution.livesOfWall.get(Solution.nodes[(int)tank.getY() / 64][a]) == 0)
                {
                    Solution.nodes[(int)tank.getY() / 64][a].setId("space");
                    Solution.pane.getChildren().remove(Solution.nodes[(int)tank.getY() / 64][a]);
                }
                break;
            }
//            if (tank.getX() - (64 * a) == Solution.bot.getTANK_GUI().getX() && tank.getY() == Solution.bot.getTANK_GUI().getY()) {
//
//                lineBullet.setStartY(tank.getY() + 32);
//                lineBullet.setStartX(tank.getX());
//
//                lineBullet.setEndY(tank.getY() + 32);
//                lineBullet.setEndX((a) * 64 - 64);
//                System.out.println((a) * 64 + 64);
//
//                Solution.LIVE_PLAYER2 -= 1;
//
//                System.out.println(Solution.LIVE_PLAYER2);
//
//                isWall = true;
//
//                if (Solution.LIVE_PLAYER2 == 0) {
//                    Solution.pane.getChildren().remove(Solution.bot.getTANK_GUI());
//
//                    System.out.println("destroy");
//                }
//                break;
//            }
            else
            {
                this.circle.toFront();
                isWall = false;
            }

        }
        for (int i = 0; i < Solution.trees.size(); i++) {
            Solution.trees.get(i).toFront();
        }
        if (!isWall)
        {
            lineBullet.setStartY(tank.getY() + 32);
            lineBullet.setStartX(tank.getX());

            lineBullet.setEndY(tank.getY() + 32);
            lineBullet.setEndX(tank.getX() - 3 * 64);
        }

        for (int i = 0; i < 3; i++) {
            if (tank.getX() - (64 * a) == Solution.bot.getTANK_GUI().getX() && tank.getY() == Solution.bot.getTANK_GUI().getY()) {
                
            }
        }
    }

    public void shootUp(Rectangle tank)
    {
        shootAudio.play();

        int times = (int)(tank.getY() / 64);

        int a = times;
        boolean isWall = true;

        for (int i = times; i > times - 3; i--) {

            if (i == 0)
            {
                lineBullet.setStartY(tank.getY());
                lineBullet.setStartX(tank.getX() + 32);

                lineBullet.setEndY(tank.getY() - (times + 1) * 64);
                lineBullet.setEndX(tank.getX() + 32);

                isWall = true;

                break;
            }
            a--;
            if (Solution.nodes[a][(int)tank.getX() / 64].getId().equals("wall"))
            {
                lineBullet.setStartY(tank.getY());
                lineBullet.setStartX(tank.getX() + 32);

                lineBullet.setEndY((a) * 64 + 64);
                lineBullet.setEndX(tank.getX() + 32);

                isWall = true;
                break;
            }

            if (Solution.nodes[a][(int)tank.getX() / 64].getId().equals("brwall"))
            {
                lineBullet.setStartY(tank.getY());
                lineBullet.setStartX(tank.getX() + 32);

                lineBullet.setEndY((a) * 64 + 64);
                lineBullet.setEndX(tank.getX() + 32);

                Solution.livesOfWall.put(Solution.nodes[a][(int)tank.getX() / 64], Solution.livesOfWall.get(Solution.nodes[a][(int)tank.getX() / 64]) - 1);

                isWall = true;

                if (Solution.livesOfWall.get(Solution.nodes[a][(int)tank.getX() / 64]) == 0)
                {
                    Solution.nodes[a][(int)tank.getX() / 64].setId("space");
                    Solution.pane.getChildren().remove(Solution.nodes[a][(int)tank.getX() / 64]);
                }
                break;
            }
            else
            {
                this.circle.toFront();
                isWall = false;
            }

        }

        for (int i = 0; i < Solution.trees.size(); i++) {
            Solution.trees.get(i).toFront();
        }
        if (!isWall)
        {
            lineBullet.setStartY(tank.getY());
            lineBullet.setStartX(tank.getX() + 32);

            lineBullet.setEndY(tank.getY() - 3 * 64);
            lineBullet.setEndX(tank.getX() + 32);
        }
    }

    public void shootDown(Rectangle tank)
    {
        shootAudio.play();

        int times = (int)(tank.getY() / 64);

        int a = times;
        boolean isWall = true;

        for (int i = times; i < times + 3; i++) {

            if (i == Solution.nodes.length - 1)
            {
                lineBullet.setStartY(tank.getY() + 64);
                lineBullet.setStartX(tank.getX() + 32);

                lineBullet.setEndY(tank.getY() + (times + i - 1) * 64);
                lineBullet.setEndX(tank.getX() + 32);

                isWall = true;

                break;
            }
            a++;
            if (Solution.nodes[a][(int)tank.getX() / 64].getId().equals("wall"))
            {
                lineBullet.setStartY(tank.getY() + 64);
                lineBullet.setStartX(tank.getX() + 32);

                lineBullet.setEndY((a) * 64);
                lineBullet.setEndX(tank.getX() + 32);

                isWall = true;
                break;
            }

            if (Solution.nodes[a][(int)tank.getX() / 64].getId().equals("brwall"))
            {
                lineBullet.setStartY(tank.getY() + 64);
                lineBullet.setStartX(tank.getX() + 32);

                lineBullet.setEndY((a) * 64);
                lineBullet.setEndX(tank.getX() + 32);

                Solution.livesOfWall.put(Solution.nodes[a][(int)tank.getX() / 64], Solution.livesOfWall.get(Solution.nodes[a][(int)tank.getX() / 64]) - 1);

                isWall = true;

                if (Solution.livesOfWall.get(Solution.nodes[a][(int)tank.getX() / 64]) == 0)
                {
                    Solution.nodes[a][(int)tank.getX() / 64].setId("space");
                    Solution.pane.getChildren().remove(Solution.nodes[a][(int)tank.getX() / 64]);
                }
                break;
            }
            else
            {
                this.circle.toFront();
                isWall = false;
            }

        }

        for (int i = 0; i < Solution.trees.size(); i++) {
            Solution.trees.get(i).toFront();
        }
        if (!isWall)
        {
            lineBullet.setStartY(tank.getY() + 64);
            lineBullet.setStartX(tank.getX() + 32);

            lineBullet.setEndY(tank.getY() + 4 * 64);
            lineBullet.setEndX(tank.getX() + 32);
        }

    }

    public Line getLine()
    {
        return lineBullet;
    }
}
