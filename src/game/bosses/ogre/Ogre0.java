package game.bosses.ogre;

import java.util.Random;

import game.Game;
import game.entity.GameBosses;

/**
 * Ogre stage 0
 * @author jcpen
 *
 */
public class Ogre0 extends GameBosses{

	public Ogre0(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 500, 500, 0, 10, 0, "bossfights/ogre/ogre_spawn.png", 85, 85, 4, 1000);
	}

	@Override
	protected void dropLoot() {
		Game.INSTANCE.spawnEntity(new Ogre1(getCenter().getX(), getCenter().getY(), null));
	}
	
	@Override
	public double adjustHealth(double amount) {
		if (amount < 0) {
			curFrame = (curFrame + tickRate) % (frames * tickRate);
		}
		return super.adjustHealth(amount);
	}

	@Override
	public void tick() {}

}
