package game.bosses.ogre;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import game.Game;
import game.entity.TotemEnemy;

/**
 * A totem pole for the ogre's boss fight.
 * @author jcpen
 *
 */
public class TotemPole extends TotemEnemy {

	private Ogre4 ogre;
	
	public TotemPole(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 60, 200, 6, OgreFight.OGRE_TOTAL_HP * 0.075, 
				"bossfights/ogre/totem_resize.png", 50, 0, 7, "weapons/lightGreenBall.png");
	}

	@Override
	protected void dropLoot() {
		ogre.adjustHealth(999);
	}
	
	public TotemPole setOgre(Ogre4 ogre) {
		this.ogre = ogre;
		return this;
	}
	
	@Override
	public void draw(Graphics g) {
    	Rectangle2D bBox = getBoundingBox();
    	g.drawImage(getImage(), (int) bBox.getX(), (int) bBox.getY(), 
    			(int) (bBox.getX() + bBox.getWidth()), (int) (bBox.getY() + bBox.getHeight()), 
    			30 * ((shotCooldown * 4) / maxCooldown), 0, 
    			30 * ((shotCooldown * 4) / maxCooldown + 1), 100, Game.INSTANCE);
    	drawHealthBar(g);
	}
}
