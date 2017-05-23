package game.bosses;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import game.Game;
import game.entity.GameWall;
import game.entity.Player;

public class CheesyPoofsFight extends BossFight{

	private static final double ARENA_WIDTH = 3250;
	private static final double ARENA_HEIGHT = 1625;
	
	private static final double WALL_THICKNESS = 200;
	
	private static final double SPACESHIP_POSITION = 750;
	
	public CheesyPoofsFight() {
		super(ARENA_WIDTH, ARENA_HEIGHT);
		this.setImage("bossfights/cheesypoofs/background2.png");
	}

	@Override
	public void generate(Player player, Game game) {
		game.spawnEntity(this);
		spawnAirship(game, SPACESHIP_POSITION, ARENA_HEIGHT / 2);
		spawnAirship(game, ARENA_WIDTH - SPACESHIP_POSITION, ARENA_HEIGHT / 2);
		
		game.spawnEntity(new GameWall(ARENA_WIDTH / 2, -WALL_THICKNESS / 2, 
				ARENA_WIDTH, WALL_THICKNESS, "transparent.png"));
		game.spawnEntity(new GameWall(ARENA_WIDTH / 2, ARENA_HEIGHT + WALL_THICKNESS / 2, 
				ARENA_WIDTH, WALL_THICKNESS, "transparent.png"));
		game.spawnEntity(new GameWall(-WALL_THICKNESS / 2, ARENA_HEIGHT / 2, 
				WALL_THICKNESS, ARENA_HEIGHT + WALL_THICKNESS * 2, "transparent.png"));
		game.spawnEntity(new GameWall(ARENA_WIDTH + WALL_THICKNESS / 2, ARENA_HEIGHT / 2, 
				WALL_THICKNESS, ARENA_HEIGHT + WALL_THICKNESS * 2, "transparent.png"));
		
		player.moveTo(new Point2D.Double(3000, ARENA_HEIGHT / 2));
		game.spawnEntity(player);
		game.spawnEntity(new CheesyPoofs(250.0, ARENA_HEIGHT / 2, null));
		
		game.spawnEntity(new FuelSpawner(140 * ARENA_WIDTH / 500, 
				10 * ARENA_HEIGHT / 250, null));
		game.spawnEntity(new FuelSpawner(183 * ARENA_WIDTH / 500, 
				10 * ARENA_HEIGHT / 250, null));
		game.spawnEntity(new FuelSpawner(320 * ARENA_WIDTH / 500, 
				10 * ARENA_HEIGHT / 250, null));
		game.spawnEntity(new FuelSpawner(363 * ARENA_WIDTH / 500, 
				10 * ARENA_HEIGHT / 250, null));
		game.spawnEntity(new FuelDumper(490 * ARENA_WIDTH / 500, 
				10 * ARENA_HEIGHT / 250, null));
		game.spawnEntity(new FuelDumper(10 * ARENA_WIDTH / 500, 
				10 * ARENA_HEIGHT / 250, null));
	}
	
	private static final double HEXAGON_RATIO = Math.sqrt(3) / 2;
	private static final double HEXAGON_RADIUS = 175;
	private static final double HEXAGON_SIDE = HEXAGON_RADIUS / HEXAGON_RATIO;
	private static final double HEXAGON_SMALL_RECTANGLE = HEXAGON_RADIUS / 3.5 * 0.449;
	private static final double HEXAGON_RECTANGLE_HDIFF = HEXAGON_RADIUS / 3.5 * 0.389;
	
	/**
	 * Creates an airship.
	 * @param game : Game to spawn items into.
	 * @param image : 
	 * @param x
	 * @param y
	 */
	public void spawnAirship(Game game, double x, double y) {
		game.spawnEntity(new GameWall(x, y, HEXAGON_RADIUS * 2, HEXAGON_SIDE, 
				"transparent.png"));
		for (double width = HEXAGON_RADIUS * 2 - HEXAGON_RECTANGLE_HDIFF, 
				rectNum = 0.5; rectNum < 4; rectNum++) {
			game.spawnEntity(new GameWall(x, y - HEXAGON_SIDE / 2 - rectNum * HEXAGON_SMALL_RECTANGLE,
					width, HEXAGON_SMALL_RECTANGLE, "transparent.png"));
			game.spawnEntity(new GameWall(x, y + HEXAGON_SIDE / 2 + rectNum * HEXAGON_SMALL_RECTANGLE,
					width, HEXAGON_SMALL_RECTANGLE, "transparent.png"));
			width -= HEXAGON_RECTANGLE_HDIFF * 4;
		}
	}

	@Override
	public void cleanUp(Game game) {
		game.getPlayer().removeWeapon("Gear");
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(getImage(), (int) (-0.216 * getWidth()), (int) (-0.124 * getHeight()), 
				(int) (1.434 * getWidth()), (int) (1.232 * getHeight()), Game.INSTANCE);
	}
}
