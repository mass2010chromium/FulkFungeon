package game.entity;

/**
 * Basic wall that does nothing.
 * @author jcpen
 *
 */
public class GameWall extends GameItem implements ICollidable{

	public GameWall(double xCenter, double yCenter, double xSize, double ySize, String imgPath) {
		super(xCenter, yCenter, xSize, ySize, imgPath);
	}

	@Override
	public void collideWith(GameItem other) {}

}
