package game.bosses.ogre;

import java.util.Iterator;

import game.Game;
import game.bosses.BossFight;
import game.entity.GameFancyWall;
import game.entity.GameItem;
import game.entity.GameWall;
import game.entity.Player;

/**
 * Ogre boss fight.
 * @author jcpen
 *
 */
public class OgreFight extends BossFight{

	public static final double ARENA_WIDTH = 2000;
	public static final double ARENA_HEIGHT = 2000;
	private static final double ARENA2_WIDTH = 4000;
	private static final double ARENA2_HEIGHT = 4000;
	private static final double WALL_WIDTH = 100;
	
	public static final double OGRE_TOTAL_HP = 800;
	
	public OgreFight() {
		super(ARENA_WIDTH, ARENA_HEIGHT);
		this.setImage("grass_tile_big.png");
	}

	@Override
	public void generate(Player player, Game game) {
		game.spawnEntity(new Ogre0(ARENA_WIDTH / 2, ARENA_HEIGHT / 2, null));
		
		game.spawnEntity(new GameFancyWall(ARENA_WIDTH / 2, 0,  
				ARENA_WIDTH, WALL_WIDTH, "endwall"));
		
		game.spawnEntity(new GameFancyWall(0, ARENA_HEIGHT / 2,  
				WALL_WIDTH, ARENA_WIDTH, "endwall"));

		game.spawnEntity(new GameFancyWall(ARENA_WIDTH / 2, ARENA_HEIGHT,  
				ARENA_WIDTH, WALL_WIDTH, "endwall"));
		
		game.spawnEntity(new GameFancyWall(ARENA_HEIGHT, ARENA_HEIGHT / 2,  
				WALL_WIDTH, ARENA_WIDTH, "endwall"));
		
		player.moveTo(200, 200);
		game.spawnEntity(player);
	}
	
	/**
	 * Change to a bigger stage for the final stages of the boss fight.
	 * @param ogre : The ogre that is being fought
	 */
	public void stage2(Ogre4 ogre) {
		Game game = Game.INSTANCE;
		Iterator<GameItem> iter = game.getEntities().iterator();
		while (iter.hasNext()) {
			GameItem next = iter.next();
			if (next instanceof GameWall) {
				next.setDead(true);
			}
		}
		this.setWidth(ARENA2_WIDTH);
		this.setHeight(ARENA2_HEIGHT);
		
		game.spawnEntity(new GameFancyWall(ARENA2_WIDTH / 2 - 1000, -1000,  
				ARENA2_WIDTH, WALL_WIDTH, "endwall"));
		
		game.spawnEntity(new GameFancyWall(-1000, ARENA2_HEIGHT / 2 - 1000,  
				WALL_WIDTH, ARENA2_HEIGHT, "endwall"));

		game.spawnEntity(new GameFancyWall(ARENA2_WIDTH / 2 - 1000, ARENA2_HEIGHT - 1000,  
				ARENA2_WIDTH, WALL_WIDTH, "endwall"));
		
		game.spawnEntity(new GameFancyWall(ARENA2_HEIGHT - 1000, ARENA2_HEIGHT / 2 - 1000,
				WALL_WIDTH, ARENA2_HEIGHT, "endwall"));
		
		game.spawnEntity(new TotemPole(-200.0, -200.0, null).setOgre(ogre));
		
		game.spawnEntity(new TotemPole(ARENA_WIDTH + 200.0, -200.0, null).setOgre(ogre));
		
		game.spawnEntity(new TotemPole(ARENA_WIDTH + 200.0, ARENA_WIDTH + 200.0, null).setOgre(ogre));
		
		game.spawnEntity(new TotemPole(-200.0, ARENA_WIDTH + 200.0, null).setOgre(ogre));
		
//		game.getMap().setScaleFactor(0.25);
//		scaling = 1;
	}
	
//	private int scaling = 0;
	
	@Override
	public void cleanUp(Game game) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void tick() {
//		if (scaling == 1) {
//			scaling++;
//		}
//		else if (scaling == 2) {
//			Game.INSTANCE.setSkipTicks(200);
//			scaling++;
//		}
//		else if (scaling == 3) {
//			scaling = 0;
//			Game.INSTANCE.getMap().setScaleFactor(1);
//		}
	}
}
