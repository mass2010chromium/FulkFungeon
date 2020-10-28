package game.bosses;

import game.entity.GameEnemies;
import game.entity.GameItem;
import game.entity.GameTickable;
import game.entity.GameWall;

public class FuelEnemy extends GameEnemies implements ISpawnable{

	private EnemySpawner spawner;
	
	public FuelEnemy(Double xCenter, Double yCenter) {
		super(xCenter, yCenter, 25, 25, 0, 0, 25, "weapons/robot_projectile.png");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void dropLoot() {}

	@Override
	public void tick() {
		
//		tmpVX = 0;
//		tmpVY = 0;
		if (Math.abs(vX) > 15) {
			this.vX = 15 * Math.signum(vX);
		}
		if (Math.abs(vY) > 15) {
			this.vY = 15 * Math.signum(vY);
		}
		accelerate(-getVX() * 0.01, -getVY() * 0.001);
	}
	
	@Override
	public void postTick() {
		super.postTick();
		if (Math.abs(vX) > 15) {
			this.vX = 15 * Math.signum(vX);
		}
		if (Math.abs(vY) > 15) {
			this.vY = 15 * Math.signum(vY);
		}
	}
	
	@Override
	public void setDead(boolean dead) {
		if (!isDead() && dead) {
			spawner.onDespawn(this);
		}
		super.setDead(dead);
	}

	@Override
	public void collideWith(GameItem other) {
		super.collideWith(other);
//		if (other instanceof GameTickable) {
//			GameTickable t = (GameTickable) other;
//			if (other instanceof FuelEnemy) {
//				((FuelEnemy) other).tmpVX += this.getLastVX() / 2;
//				((FuelEnemy) other).tmpVY += this.getLastVY() / 2;
//				tmpVX += t.getLastVX() / 2;
//				tmpVY += t.getLastVY() / 2;
//			}
//			else {
//				tmpVX += t.getLastVX();
//				tmpVY += t.getLastVY();
//			}
//		}
//		else if (other instanceof GameWall) {
//			if (tmpVX == 0) {
//				tmpVX = -0.5 * vX;
//			}
//			if (tmpVY == 0) {
//				tmpVY = -0.5 * vY;
//			}
//		}
	}

	@Override
	public void setSpawner(EnemySpawner spawner) {
		this.spawner = spawner;
	}

	@Override
	public EnemySpawner getSpawner() {
		return spawner;
	}
}
