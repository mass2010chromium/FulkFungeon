package game.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import game.Game;

/**
 * Walls in the game.
 * @author jpeng988
 *
 */
public class GameFancyWall extends GameWall {

	private Image capImage;
	
	public GameFancyWall(double xCenter, double yCenter, double xSize, double ySize, String imgPath) {
		super(xCenter, yCenter, xSize > ySize ? xSize : xSize, ySize, null);
		this.setImage(imgPath + "_2.png");
		capImage = Game.getImage(imgPath + "_1.png");
	}
	
	@Override
    public void draw(Graphics g) {
    	int max = (int) Math.max(getWidth(), getHeight());
    	int min = (int) Math.min(getHeight(), getWidth());
    	double ratio = Math.max(getWidth() / getHeight(), getHeight() / getWidth());
    	double angle = 0;
    	if (getWidth() > getHeight()) {
    		angle = Math.PI / 2;
    	}
    	g.translate((int) getCenter().getX(), (int) getCenter().getY());
    	((Graphics2D) g).rotate(angle);
    	
    	g.drawImage(getImage(), -min / 2, -max / 2, min / 2, max / 2, 0, 0, 60, (int) (60 * ratio), Game.INSTANCE);
    	
    	g.drawImage(capImage, -min / 2, -max / 2, min / 2, (int) (3.0 / 5 * min), 0, 0, 60, 100, Game.INSTANCE);
    	
    	((Graphics2D) g).rotate(Math.PI);
    	g.drawImage(capImage, -min / 2, -max / 2, min / 2, (int) (3.0 / 5 * min), 0, 0, 60, 100, Game.INSTANCE);
    	((Graphics2D) g).rotate(-Math.PI);
    	
    	((Graphics2D) g).rotate(-angle);
    	g.translate(-(int) getCenter().getX(), -(int) getCenter().getY());
    }
}
