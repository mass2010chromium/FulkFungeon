package game.bosses.ogre;

import java.awt.geom.Point2D;
import java.util.Random;

import game.Game;
import game.entity.GameBosses;
import game.entity.Projectile;
import utils.MathUtils;

/**
 * Ogre stage 4.
 * @author jcpen
 *
 */
public class Ogre4 extends GameBosses{

	private int ringNum;
	
	private int shotCooldown;
	
	private Projectile templateProjectile;
	
	// 15% of total HP
	public Ogre4(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 300, 300, 0.2, OgreFight.OGRE_TOTAL_HP * 0.15, 6, 
				"bossfights/ogre/ogre_stasis.png", 50, 60, 1, 100);
		ringNum = 0;
		shotCooldown = 0;
		templateProjectile = new Projectile(null, 0, 0, 60, 60, 0, 0, 
				5 * Game.INSTANCE.getMap().getDifficulty(), 
				"bossfights/ogre/projectiles/stage_4.png", 600);
	}

	@Override
	protected void dropLoot() {
		Game.INSTANCE.spawnEntity(new Ogre5(getCenter().getX(), getCenter().getY(), null));
	}
	
	@Override
	public double adjustHealth(double amount) {
		if (amount == 999) {
			return super.adjustHealth(-this.maxHealth / 4 - 1);
		}
		return this.getHealth();
	}

	private static final int BULLET_RING_NUM = 32;
	
	@Override
	public void tick() {
		this.moveDirect(MathUtils.angle(new Point2D.Double(OgreFight.ARENA_WIDTH / 2, 
				OgreFight.ARENA_HEIGHT / 2), this.getCenter()), moveSpeed);
		shotCooldown++;
		if (shotCooldown >= 100) {
			shotCooldown = 0;
			ringNum++;
			ringNum %= 4;
			double initialAngle = (Math.PI * ringNum / 2) / BULLET_RING_NUM; 
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
