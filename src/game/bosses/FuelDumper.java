package game.bosses;

import java.awt.geom.Point2D;
import java.util.Random;

import game.Game;
import game.entity.GameItem;
import utils.MathUtils;

public class FuelDumper extends EnemySpawner{

	private int spawnTimer;
	public FuelDumper(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 50, 50, "transparent.png", FuelEnemy.class, 50);
		spawnTimer = 0;
	}

	@Override
	public void tick() {
		if (spawnTimer >= 2900) {
			if (spawnTimer % 5 == 0) {
				ISpawnable result = spawn();
				if (result != null) {
					Point2D accel = MathUtils.rotate(new Point2D.Double(15, 0), 
							new Point2D.Double(), Math.random() * 2 * Math.PI);
					((FuelEnemy) result).accelerate(accel.getX(), Math.abs(accel.getY()));
					result.setSpawner(this);
					Game.INSTANCE.spawnEntity((GameItem) result);
				}
			}
			if (spawnTimer >= 3000) {
				spawnTimer = 0;
			}
		}
		spawnTimer++;
	}

}
