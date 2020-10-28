package game.bosses.ogre;

import java.util.Random;

import game.Game;
import game.entity.GameBosses;
import game.entity.Projectile;
import utils.MathUtils;

/**
 * Ogre stage 3.
 * @author jcpen
 *
 */
public class Ogre3 extends GameBosses{

	private double ringNum;
	
	private int shotCooldown;
	
	private Projectile templateProjectile;

	//30% of total hp
	public Ogre3(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 300, 300, 2, OgreFight.OGRE_TOTAL_HP * 0.3, 8, 
				"bossfights/ogre/ogre_move.png", 50, 60, 10, 25);
		ringNum = 0;
		shotCooldown = 0;
		templateProjectile = new Projectile(null, 0, 0, 60, 60, 0, 0, 
				7 * Game.INSTANCE.getMap().getDifficulty(), 
				"bossfights/ogre/projectiles/stage_3.png", 400);
	}

	@Override
	protected void dropLoot() {
		Ogre4 ogre = new Ogre4(getCenter().getX(), getCenter().getY(), null);
		Game.INSTANCE.spawnEntity(ogre);	
		((OgreFight) Game.INSTANCE.getMap().getCurrentBossFight()).stage2(ogre);
	}

	@Override
	public void tick() {
		moveDirect(MathUtils.angle(player.getCenter(), getCenter()), this.moveSpeed);
		ringNum += 0.05;
		shotCooldown++;
		
		if (shotCooldown >= 10) {
			shotCooldown = 0;
			Game.INSTANCE.spawnEntity(new Projectile(this, 
					getCenter().getX(), getCenter().getY(), 
					shootSpeed * Math.cos(ringNum), shootSpeed * Math.sin(ringNum), 
					templateProjectile
					));
			Game.INSTANCE.spawnEntity(new Projectile(this, 
					getCenter().getX(), getCenter().getY(), 
					shootSpeed * Math.cos(ringNum + Math.PI), shootSpeed * Math.sin(ringNum + Math.PI), 
					templateProjectile
					));
		}
	}

}
