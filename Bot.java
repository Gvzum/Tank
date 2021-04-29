import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bot extends Tank implements Player{

	private Rectangle TANK_GUI;
	private Map map;
	static int direction = 1;


	public Bot() {
		this.TANK_GUI = new Rectangle(64, 64);
		this.TANK_GUI.setFill(Color.DARKGREEN);
		this.TANK_GUI = TANK_GUI;
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