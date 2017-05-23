package game.bosses;

import game.Factory;
import game.entity.GameItem;
import game.entity.GameTickable;

public abstract class EnemySpawner extends GameTickable{

	private Class<? extends ISpawnable> spawnTarget;
	protected int maxSpawnCount;
	protected int spawnedNum;
	
	public EnemySpawner(double xCenter, double yCenter, double xSize, double ySize, 
			String imgPath, Class<? extends ISpawnable> enemy, int maxSpawnCount) {
		super(xCenter, yCenter, xSize, ySize, imgPath);
		spawnTarget = enemy;
		this.maxSpawnCount = maxSpawnCount;
		spawnedNum = 0;
	}
	
	/**
	 * Spawns an instance of spawnTarget at this spawner's position.
	 */
	public ISpawnable spawn() {
		if (spawnedNum >= maxSpawnCount) return null;
		spawnedNum++;
		return Factory.instantiate(spawnTarget, getCenter().getX(), getCenter().getY());
	}
	
	/**
	 * Should be called when something spawned by this spawner dies.
	 * @param target : The one that dies
	 */
	public void onDespawn(ISpawnable target) {
		spawnedNum--;
	}

	@Override
	public void collideWith(GameItem other) {}
}
