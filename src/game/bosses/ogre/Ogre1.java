package game.bosses.ogre;

import java.util.Random;

import game.Game;
import game.entity.GameBosses;
import game.entity.Projectile;
import utils.MathUtils;

/**
 * Ogre stage 1.
 * @author jcpen
 *
 */
public class Ogre1 extends GameBosses{

	private int ringNum;
	
	private int shotCooldown;
	
	private Projectile templateProjectile;
	
	// 30% of total HP
	public Ogre1(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 300, 300, 2, OgreFight.OGRE_TOTAL_HP * 0.4, 6, 
				"bossfights/ogre/ogre_move.png", 50, 60, 10, 25);
		ringNum = 0;
		shotCooldown = 0;
		templateProjectile = new Projectile(null, 0, 0, 60, 60, 0, 0, 
				7 * Game.INSTANCE.getMap().getDifficulty(), 
				"bossfights/ogre/projectiles/stage_1.png", 400);
	}

	@Override
	protected void dropLoot() {
		Game.INSTANCE.spawnEntity(new Ogre2(getCenter().getX(), getCenter().getY(), null));
	}

	private static final int BULLET_RING_NUM = 12;
	
	@Override
	public void tick() {
		moveDirect(MathUtils.angle(player.getCenter(), getCenter()), this.moveSpeed);
		shotCooldown++;
		if (shotCooldown >= 50) {
			shotCooldown = 0;
			ringNum++;
			ringNum %= 2;
			double initialAngle = (Math.PI * ringNum) / BULLET_RING_NUM; 
			for (int i = 0; i < BULLET_RING_NUM; i++) {
				double angle = initialAngle + (Math.PI * 2 * i) / BULLET_RING_NUM;
				Game.INSTANCE.spawnEntity(new Projectile(this, 
						getCenter().getX(), getCenter().getY(), 
						shootSpeed * Math.cos(angle), shootSpeed * Math.sin(angle), 
						templateProjectile
						));
			}
		}
	}

}
