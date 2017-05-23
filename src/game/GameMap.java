package game;

import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

import game.bosses.BossFight;
import game.entity.GameFancyWall;
import game.entity.Player;
import game.entity.Portal;
import game.entity.enemies.WeaponEnemy;
import utils.MathUtils;

/**
 * Does things like draw the player correctly and generate new levels.
 * @author jpeng988
 *
 */
public class GameMap {
	
	public static final int ROOM_SIZE = 600;
	private static final int WALL_WIDTH = 60;
	private static final double DIFFICULTY_INCREASE = 1.05;
	
	private Player player;
	private Random rand;
	private MazeGenerator gen;
	private int xSize, ySize;
	private int floorWidth, floorHeight;
	private double difficulty;
	
	private int bossFightProb;
	private BossFight lastBossFight;
	private static final double BOSSFIGHT_CHANCE = 10;
	
	//TODO add boss portals
	public GameMap(int xSize, int ySize, Player player) {
		this.player = player;
		rand = new Random();
		this.xSize = xSize;
		this.ySize = ySize;
		gen = new MazeGenerator(rand);
		difficulty = 0;
		bossFightProb = -3;
		lastBossFight = null;
	}
	
	/**
	 * Offsets the graphics object to draw the player in the middle.
	 * @param g : Graphics object to offset
	 */
	public void updatePosition(Graphics g) {
		Point2D pt = applyOffset(new Point2D.Double());
		g.translate((int) -pt.getX(), (int) -pt.getY());
	}
	
	/**
	 * Offsets a given point from "graphics coordinates" to "game coordinates".
	 * @param point : Point to offset
	 * @return The offsetted value
	 */
	public Point2D applyOffset(Point2D point) {
		return new Point2D.Double(player.getCenter().getX() + point.getX() - xSize / 2, 
				player.getCenter().getY() + point.getY() - ySize / 2);
	}
	
	/**
	 * Generate a new level.
	 * @param game : Game object to generate for.
	 */
	public void generateMap(Game game) {
		if (difficulty == 0) difficulty = 1;
		else difficulty *= DIFFICULTY_INCREASE;
		game.resetMap();
		if (rand.nextDouble() < bossFightProb / BOSSFIGHT_CHANCE) {
			lastBossFight = Items.randomBossFight();
			lastBossFight.generate(player, game);
			bossFightProb = -3;
		}
		else {
			if (lastBossFight != null) {
				lastBossFight.cleanUp(game);
				lastBossFight = null;
			}
			generateNormalMap(game);
//			bossFightProb++;
		}
	}
	
	private void generateNormalMap(Game game) {
		int width = 4 + rand.nextInt(4);
		int height = 4 + rand.nextInt(4);
		List<Line2D> walls = gen.generateMaze(height, width);
		for (int i = 0; i < (width * height) / (4 + rand.nextInt(4)); i++) {
			walls.remove(rand.nextInt(walls.size()));
		}
		for (Line2D line : walls) {
			Point2D center = MathUtils.midpoint(line);
			game.spawnEntity(new GameFancyWall(
					center.getX() * ROOM_SIZE + ROOM_SIZE / 2, center.getY() * ROOM_SIZE + ROOM_SIZE / 2, 
					Math.abs(line.getX1() - line.getX2()) * ROOM_SIZE + WALL_WIDTH, 
					Math.abs(line.getY1() - line.getY2()) * ROOM_SIZE + WALL_WIDTH, "wall"));
		}
		double roomWidth = width * ROOM_SIZE;
		double roomHeight = height * ROOM_SIZE;
		game.spawnEntity(new GameFancyWall(roomWidth / 2, 0, roomWidth, WALL_WIDTH, "endwall"));
		game.spawnEntity(new GameFancyWall(roomWidth / 2, roomHeight, roomWidth, WALL_WIDTH, "endwall"));
		game.spawnEntity(new GameFancyWall(0, roomHeight / 2, WALL_WIDTH, roomHeight, "endwall"));
		game.spawnEntity(new GameFancyWall(roomWidth, roomHeight / 2, WALL_WIDTH, roomHeight, "endwall"));
		
		
		int area = width * height;
		
		// One enemy per 5 squares
		int[][] squares = new int[height][width];
		
		squares[height / 2][width / 2] = 999; //Player pos
		
		for (int i = 0; i < area / 4;) {
			int row = rand.nextInt(height);
			int col = rand.nextInt(width);
			if (squares[row][col] != 999) {
//				game.spawnEntity(new BasicEnemy((col + 0.5) * ROOM_SIZE, (row + 0.5) * ROOM_SIZE, 
//						"Enemy-" + (rand.nextInt(3) + 1) + ".png"));
				game.spawnEntity(Factory.instantiate(Items.randomEnemy(), 
						(col + 0.5) * ROOM_SIZE, (row + 0.5) * ROOM_SIZE, rand));
				
				squares[row][col] = 999;
				System.out.println("Spawned " + i + "th enemy at " + row + ", " + col);
				i++;
			}
		}
		for (int i = 0; i < area / 15;) {
			int row = rand.nextInt(height);
			int col = rand.nextInt(width);
			if (squares[row][col] != 999) {
				game.spawnEntity(Factory.createWeapon((col + 0.5) * ROOM_SIZE, 
						(row + 0.5) * ROOM_SIZE, Items.randomWeapon())); 
				
				squares[row][col] = 999;
				System.out.println("Spawned " + i + "th weapon at " + row + ", " + col);
				i++;
			}
		}
		
		while (true) {
			int row = rand.nextInt(height);
			int col = rand.nextInt(width);
			if (squares[row][col] != 999) {
				System.out.println(squares[row][col]);
				game.spawnEntity(new Portal(
						(col + 0.5) * ROOM_SIZE, (row + 0.5) * ROOM_SIZE, 100, 100, "portal.png"));
				System.out.println("Spawned portal at " + row + ", " + col);
				break;
			}
		}
		
		while (true) {
			int row = rand.nextInt(height);
			int col = rand.nextInt(width);
			if (squares[row][col] != 999) {
				System.out.println(squares[row][col]);
				game.spawnEntity(Factory.instantiate(WeaponEnemy.class, 
						(col + 0.5) * ROOM_SIZE, (row + 0.5) * ROOM_SIZE, rand));
				System.out.println("Spawned mimniboss at " + row + ", " + col);
				break;
			}
		}
		
		floorWidth = width;
		floorHeight = height;
		
		player.moveTo(new Point2D.Double((width / 2) * ROOM_SIZE + ROOM_SIZE / 2, (height / 2) * ROOM_SIZE + ROOM_SIZE / 2));
		game.spawnEntity(player);
	}
	
	/**
	 * Gets the game's difficulty, affecting enemies' damage and hp.
	 * @return Difficulty starting from 1 and going up by 1% every round.
	 */
	public double getDifficulty() {
		return this.difficulty;
	}
	
	/**
	 * Get how many rooms wide this map is.
	 * @return
	 */
	public int width() {
		return floorWidth;
	}
	
	/**
	 * Get how many rooms "tall" this map is.
	 * @return
	 */
	public int height() {
		return floorHeight;
	}
}
