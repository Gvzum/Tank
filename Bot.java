import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bot extends Tank implements Player{

	private Rectangle TANK_GUI;
	private Map map;
	private int direction = 1;


	public Bot() {
		super();
		this.getTANK_GUI().setFill(Color.DARKGREEN);
	}

	@Override
	public int getDirection() {
		return super.getDirection();
	}

	@Override
	public Rectangle getTANK_GUI() {
		return super.getTANK_GUI();
	}

	@Override
	public void setMap(Map map) {
		super.setMap(map);
	}

	@Override
	public void moveRight() {
		super.moveRight();
	}

	@Override
	public void moveLeft() {
		super.moveLeft();
	}

	@Override
	public void moveUp() {
		super.moveUp();
	}

	@Override
	public void moveDown() {
		super.moveDown();
	}

	@Override
	public Position getPosition() {
		return super.getPosition();
	}
}