package game.bosses;

import java.awt.geom.Point2D;
import java.util.Random;

import game.Game;
import game.entity.GameItem;
import utils.MathUtils;

public class FuelSpawner extends EnemySpawner{

	private int spawnTimer;
	
	public FuelSpawner(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 50, 50, "transparent.png", FuelEnemy.class, 50);
		spawnTimer = 0;
	}

	@Override
	public void tick() {
		if (spawnTimer >= 100) {
			ISpawnable result = spawn();
			if (result != null) {
				Point2D accel = MathUtils.rotate(new Point2D.Double(3, 0), 
						new Point2D.Double(), Math.random() * 2 * Math.PI);
				((FuelEnemy) result).accelerate(accel.getX(), accel.getY());
				result.setSpawner(this);
				Game.INSTANCE.spawnEntity((GameItem) result);
			}
			spawnTimer = 0;
		}
		spawnTimer++;
	}

}
