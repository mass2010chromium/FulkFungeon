package game.bosses;

/**
 * Implementing this: Constructor should take 3 params: x, y
 * @author jcpen
 *
 */
public interface ISpawnable {
	public void setSpawner(EnemySpawner spawner);
	
	public EnemySpawner getSpawner();
}
