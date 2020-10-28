package game.bosses.ogre;

import java.util.Random;

import game.Factory;
import game.Game;
import game.Items;
import game.entity.CopyPowerUp;
import game.entity.GameBosses;
import game.entity.Portal;
import game.entity.Projectile;
import utils.MathUtils;

/**
 * Ogre final stage.
 * @author jcpen
 *
 */
public class Ogre5 extends GameBosses{
	
	private int shotCooldown;
	
	private Projectile templateProjectile;

	public Ogre5(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 350, 350, 4, OgreFight.OGRE_TOTAL_HP * 0.1, 6, 
				"bossfights/ogre/ogre_rage.png", 50, 60, 10, 6);
		
		templateProjectile = new Projectile(null, 0, 0, 60, 60, 0, 0, 
				5 * Game.INSTANCE.getMap().getDifficulty(), 
				"bossfights/ogre/projectiles/stage_5.png", 400);
	}

	@Override
	protected void dropLoot() {
		for (int i = 0; i < 3; i++) Game.INSTANCE.spawnEntity(new CopyPowerUp(
				getCenter().getX(), getCenter().getY(), Items.randomPowerUp()));
		Game.INSTANCE.spawnEntity(
				Factory.createWeapon(getCenter().getX(), getCenter().getY(), Items.getWeapon("Alkatraz")));
		Game.INSTANCE.getPlayer().addScore(500);
		Game.INSTANCE.spawnEntity(new Portal(0, 0, 100, 100, "portal.png"));
	}

	@Override
	public void tick() {
		moveDirect(MathUtils.angle(player.getCenter(), getCenter()), this.moveSpeed);
		shotCooldown++;
		if (shotCooldown > 100) {
			shotCooldown = 0;
			double startAngle = MathUtils.angle(player.getCenter(), getCenter());
			for (int i = -4; i <= 4; i++) {
				double angle = startAngle + i * (Math.PI / 16);
				Game.INSTANCE.spawnEntity(new Projectile(this, getCenter().getX(), 
						getCenter().getY(), shootSpeed * Math.cos(angle), 
						shootSpeed * Math.sin(angle), templateProjectile));
			}
		}
	}

}
