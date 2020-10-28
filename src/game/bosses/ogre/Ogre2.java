package game.bosses.ogre;

import java.awt.geom.Point2D;
import java.util.Random;

import game.Game;
import game.entity.GameBosses;
import game.entity.Projectile;
import utils.MathUtils;

/**
 * Ogre stage 2.
 * @author jcpen
 *
 */
public class Ogre2 extends GameBosses{
	
	private int shotCooldown;
	
	private Projectile templateProjectile;

	// 20% of total HP
	public Ogre2(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 300, 300, 2, OgreFight.OGRE_TOTAL_HP * 0.3, 6, 
				"bossfights/ogre/ogre_move.png", 50, 60, 10, 25);
		shotCooldown = 0;
		templateProjectile = new Projectile(null, 0, 0, 30, 30, 0, 0, 
				10 * Game.INSTANCE.getMap().getDifficulty(), 
				"bossfights/ogre/projectiles/stage_2_shot1.png", 100) {
			@Override
			public void tick(Projectile inst) {
				super.tick(inst);
				if (inst.getLife() >= getMaxLife()) {
					Point2D leftVel = MathUtils.scale(
							MathUtils.rotate(
									new Point2D.Double(inst.getVX(), inst.getVY()), 
									new Point2D.Double(), Math.PI / 4), 
							new Point2D.Double(), 1);
					Point2D rightVel = MathUtils.rotate(leftVel, new Point2D.Double(), -Math.PI / 2);
					Game.INSTANCE.spawnEntity(new Projectile(inst.getShooter(), 
							inst.getCenter().getX(), inst.getCenter().getY(), 15, 15, 
								leftVel.getX(), leftVel.getY(), 5 * Game.INSTANCE.getMap().getDifficulty(), 
								"bossfights/ogre/projectiles/stage_2_shot2.png", 600
							));
					Game.INSTANCE.spawnEntity(new Projectile(inst.getShooter(), 
							inst.getCenter().getX(), inst.getCenter().getY(), 15, 15, 
								rightVel.getX(), rightVel.getY(), 5 * Game.INSTANCE.getMap().getDifficulty(), 
								"bossfights/ogre/projectiles/stage_2_shot2.png", 600
							));
				}
			}
		};
	}

	@Override
	protected void dropLoot() {
		Game.INSTANCE.spawnEntity(new Ogre3(getCenter().getX(), getCenter().getY(), null));
	}

	@Override
	public void tick() {
		moveDirect(MathUtils.angle(player.getCenter(), getCenter()), this.moveSpeed);
		shotCooldown++;
		if (shotCooldown > 100) {
			shotCooldown = 0;
			double startAngle = MathUtils.angle(player.getCenter(), getCenter());
			for (int i = -2; i <= 2; i++) {
				double angle = startAngle + i * (Math.PI / 8);
				Game.INSTANCE.spawnEntity(new Projectile(this, getCenter().getX(), 
						getCenter().getY(), shootSpeed * Math.cos(angle), 
						shootSpeed * Math.sin(angle), templateProjectile));
			}
		}
	}

}
