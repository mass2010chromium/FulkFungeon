package game.bosses;

import game.Game;
import game.entity.GameItem;
import game.entity.GameTickable;
import game.entity.Player;

/**
 * A boss fight. Implement this to create boss fights.
 * @author jcpen
 *
 */
public abstract class BossFight extends GameTickable{
	
	/**
	 * Just extending GameItem to recieve ticks.
	 */
	public BossFight(double width, double height) {
		super(0, 0, width, height, "transparent.png");
	}

	/**
	 * Generate the boss room, moving player, spawning walls and enemies.
	 * @param player
	 * @param game
	 */
	public abstract void generate(Player player, Game game);
	
	/**
	 * Clean up after a boss fight.
	 * @param game
	 */
	public abstract void cleanUp(Game game);
	
	@Override
	public void collideWith(GameItem other) {}
}
