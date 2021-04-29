import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Tank extends MyPlayer {
    private Rectangle TANK_GUI;
    private Map map;
    private int direction = 1;

    public Tank()
    {
        this.TANK_GUI = new Rectangle(64,64);
        this.TANK_GUI.setFill(Color.GOLD);
    }

    public int getDirection() {
        return this.direction;
    }

    public Rectangle getTANK_GUI()
    {
        return TANK_GUI;
    }

    @Override
    public void setMap(Map map) {
        super.setMap(map);
    }

    @Override
    public void moveRight() {

        if (TANK_GUI.getX() != (Solution.nodes.length - 1) * 64)
        {

            if (Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64) + 1].getId().equals("wall") ||
                Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64) + 1].getId().equals("brwall") ||
                Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64) + 1].getId().equals("water"))
            {

            }

            else if (Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64) + 1].getId().equals("tree"))
            {
                TANK_GUI.setX(TANK_GUI.getX() + 64);
                Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64)].toFront();
            }

            else 
            {
                TANK_GUI.setX(TANK_GUI.getX() + 64);

            }
        }

        direction = 1;

    }

    @Override
    public void moveLeft() {

        if (TANK_GUI.getX() != 0)
        {

            if (Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64) - 1].getId().equals("wall") ||
                Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64) - 1].getId().equals("brwall") ||
                Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64) - 1].getId().equals("water"))
            {

            }

            else if (Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64) - 1].getId().equals("tree"))
            {
                TANK_GUI.setX(TANK_GUI.getX() - 64);
                Solution.nodes[(int)(TANK_GUI.getY()) / 64][(int)(TANK_GUI.getX() / 64)].toFront();
            }

            else 
            {
               TANK_GUI.setX(TANK_GUI.getX() - 64);
            }
             
        }

        direction = 2;

    }

    @Override
    public void moveUp() {

        if (TANK_GUI.getY() != 0)
        {

            if (Solution.nodes[(int)(TANK_GUI.getY() / 64) - 1][(int)(TANK_GUI.getX()) / 64].getId().equals("wall") ||
                Solution.nodes[(int)(TANK_GUI.getY() / 64) - 1][(int)(TANK_GUI.getX()) / 64].getId().equals("brwall") ||
                Solution.nodes[(int)(TANK_GUI.getY() / 64) - 1][(int)(TANK_GUI.getX()) / 64].getId().equals("water"))
            {

            }

            else if (Solution.nodes[(int)(TANK_GUI.getY() / 64) - 1][(int)(TANK_GUI.getX()) / 64].getId().equals("tree"))
            {
                TANK_GUI.setY(TANK_GUI.getY() - 64);
                Solution.nodes[(int)(TANK_GUI.getY() / 64)][(int)(TANK_GUI.getX()) / 64].toFront();
            }

            else
            {
               TANK_GUI.setY(TANK_GUI.getY() - 64);
            }
            
        }

        direction = 3;

    }

    @Override
    public void moveDown() {

        if (TANK_GUI.getY() != (Solution.nodes.length - 1) * 64)
        {

            if (Solution.nodes[(int)(TANK_GUI.getY() / 64) + 1][(int)(TANK_GUI.getX() / 64)].getId().equals("wall") ||
                Solution.nodes[(int)(TANK_GUI.getY() / 64) + 1][(int)(TANK_GUI.getX() / 64)].getId().equals("brwall") ||
                Solution.nodes[(int)(TANK_GUI.getY() / 64) + 1][(int)(TANK_GUI.getX() / 64)].getId().equals("water"))
            {

            }

            else if (Solution.nodes[(int)(TANK_GUI.getY() / 64) + 1][(int)(TANK_GUI.getX() / 64)].getId().equals("tree"))
            {
                TANK_GUI.setY(TANK_GUI.getY() + 64);
                Solution.nodes[(int)(TANK_GUI.getY() / 64)][(int)(TANK_GUI.getX() / 64)].toFront();
            }

            else 
            {

                TANK_GUI.setY(TANK_GUI.getY() + 64);

            }

        }
        direction = 4;

    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }
}
