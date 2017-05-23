package game.bosses;

import java.awt.geom.Point2D;

import game.entity.GameLiving;
import game.entity.Projectile;
import game.entity.Weapon;

public class Gear extends Weapon implements ISpawnable{

	private EnemySpawner spawner;
	public Gear(Double xCenter, Double yCenter) {
		super(xCenter, yCenter, 0, 100, 0, "bossfights/cheesypoofs/gear.png", 
				null, "Gear");
		spawner = null;
	}

	@Override
	public void setSpawner(EnemySpawner spawner) {
		this.spawner = spawner;
	}
	
	/**
	 * Don't shoot anything.
	 */
	public void shoot(GameLiving shooter, double angle, Point2D pos, Projectile p, 
			double power) {}

	@Override
	public EnemySpawner getSpawner() {
		return spawner;
	}
}
