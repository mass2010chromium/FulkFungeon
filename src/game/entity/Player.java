package game.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

import game.Game;
import game.GameInputListener;
import game.Items;
import utils.MathUtils;

/**
 * 
 *  Player Character
 *  --picks up Weapons/PowerUps
 *  --Fires Projectiles
 *  @author  smohidekar916
 *  @version Apr 26, 2017
 *  @author  Period: 2
 *  @author  Assignment: Dungeon
 */
public class Player extends GameLiving 
{
	private static final int SHOOT = 1;
	private static final int IDLE = 0;
	
	private static final int WALK_STATES = 80;
	
	private Point2D mousePt = new Point2D.Double();
	private int state;
	private int walkState;
	
	private Weapon currentWeapon;
	
	private double score;
	
	public static final Point2D HAND_POS = new Point2D.Double(12, 40); //TODO
	
	private Map<String, Weapon> weapons;
	
	/**
	 * Creates a player.
	 * @param xCenter : X position
	 * @param yCenter : Y position
	 * @param xSize : Width of hitbox
	 * @param ySize : Height of hitbox
	 * @param health : Starting max and current health of the player.
	 * @param imgPath : Where the player's image is located.
	 */
	public Player(double xCenter, double yCenter, double xSize, double ySize, double health, 
			String imgPath) {
		super(xCenter, yCenter, xSize, ySize, health, imgPath);
		state = IDLE;
		walkState = 0;

		weapons = new TreeMap<String, Weapon>();
		this.currentWeapon = Items.getWeapon("NULL");
		weapons.put(currentWeapon.getName(), currentWeapon);
		Game.INSTANCE.getSideBar().updateWeapon(currentWeapon);
	}
	
	/**
	 * Sets the weapon this player is using.
	 * @param wep : Weapon to use.
	 * @return The weapon this player was using.
	 */
	public Weapon setCurrentWeapon(String name) {
		Weapon tmp = this.currentWeapon;
		this.currentWeapon = weapons.get(name);
		this.currentWeapon.cooldown = 0;
		return tmp;
	}
	
	public Weapon removeWeapon(String name) {
		Weapon wep = weapons.remove(name);
		if (wep != null) Game.INSTANCE.getSideBar().removeWeapon(wep);
		return wep;
	}
	
	/**
	 * Called when the player should pick up a weapon.
	 * Either adds the weapon to the player's inventory or upgrades
	 * an existing weapon.
	 * @param wep : weapon to pick up.
	 */
	public void pickupWeapon(Weapon wep) {
		if (weapons.containsKey(wep.getName())) {
			weapons.get(wep.getName()).upgrade();
		}
		else {
			weapons.put(wep.getName(), wep);
		}
		if (currentWeapon.getName().equals("NULL")) {
			setCurrentWeapon(wep.getName());
			removeWeapon("NULL");
		}
		Game.INSTANCE.getSideBar().updateWeapon(weapons.get(wep.getName()));
	}
	
	public Map<String, Weapon> getWeapons() {
		return weapons;
	}
	
	/**
	 * Increases the player's max health.
	 * @param maxHP : Amount to increase by.
	 */
	public void addMaxHealth(int maxHP) {
		this.maxHealth += maxHP;
		this.adjustHealth(maxHP);
	}

	@Override
	public void tick() {
		GameInputListener l = Game.INSTANCE.getInput();
		mousePt = l.getMousePos();
		
		// Grid Control
		if (l.keyDown(KeyEvent.VK_A)) vX -= 0.4;
		if (l.keyDown(KeyEvent.VK_D)) vX += 0.4;
		if (l.keyDown(KeyEvent.VK_W)) vY -= 0.4;
		if (l.keyDown(KeyEvent.VK_S)) vY += 0.4;
		
		if (Math.abs(vY) > 0.1 || Math.abs(vX) > 0.1) {
			walkState = (walkState + 1) % WALK_STATES;
		}
		else {
			walkState = 0;
		}
//		if (l.keyDown(KeyEvent.VK_A)) vX = -10;
//		if (l.keyDown(KeyEvent.VK_D)) vX = 10;
//		if (l.keyDown(KeyEvent.VK_W)) vY = -10;
//		if (l.keyDown(KeyEvent.VK_S)) vY = 10;
		
		if (state == SHOOT) {
			vX *= 0.9;
			vY *= 0.9;
		}
		else {
			vX *= 0.93;
			vY *= 0.93;
		}
//		System.out.println(vX + ", " + vY);
//		vX = 0;
//		vY = 0;
		byte b = l.getMouseState();
		Point2D centerPt = MathUtils.add(getCenter(), HAND_POS);
		double angle = MathUtils.angle(mousePt, centerPt);
		if ((b & GameInputListener.MOUSE1_PRESSED) > 0) {
			state = SHOOT;
		}
		else {
			state = IDLE;
		}
		if ((b & GameInputListener.MOUSE2_CLICKED) > 0 && this.weapons.size() > 1) {
			System.out.println("Switch");
			Queue<Weapon> weaponsAlpha = new LinkedList<Weapon>(new PriorityQueue<Weapon>(weapons.values()));
			while (weaponsAlpha.peek() != currentWeapon) weaponsAlpha.add(weaponsAlpha.poll());
			weaponsAlpha.poll();
			this.currentWeapon = weaponsAlpha.peek();
		}
		this.currentWeapon.update(this, angle, centerPt, state == SHOOT);
	}
	
	/**
	 * Adds an amount to this player's score count. Adds more score for higher
	 * difficulty levels.
	 * @param d : Amount to add.
	 */
	public void addScore(double d) {
		this.score += d * Game.INSTANCE.getMap().getDifficulty();
	}
	
	public double getScore() {
		return score;
	}

	@Override
    public void draw(Graphics g) {
//    	DrawingUtils.drawRotatedImage(g, getImage(), getCenter().getX(), getCenter().getY(), 
//    			2, 0, state * 50, 50, (state + 1) * 50, 
//    			MathUtils.angle(mousePt, getCenter()) - Math.PI / 2, Game.INSTANCE);
//    	DrawingUtils.drawRotatedImage(g, getImage(), getCenter().getX(), getCenter().getY(), 
//    			2, 0, 0, 28, 54, 
//    			MathUtils.angle(mousePt, getCenter()) - Math.PI / 2, Game.INSTANCE);
    	g.translate((int) getCenter().getX(), (int) getCenter().getY());
    	double halfW = this.getWidth() / 2;
    	double halfH = this.getHeight() / 2;
    	g.drawImage(this.getImage(), (int) -halfW, (int) -halfH, 
    			(int) halfW, (int) halfH, 
    			(walkState / 20) * 28, 0, (walkState / 20 + 1) * 28, 54, Game.INSTANCE);
    	this.currentWeapon.drawWeapon((Graphics2D) g, MathUtils.angle(mousePt, 
    			MathUtils.add(getCenter(), HAND_POS)), HAND_POS, state == SHOOT);
    	g.translate(-(int) getCenter().getX(), -(int) getCenter().getY());
    	drawHealthBar(g);
//    	if (state == SHOOT) {
////    		DrawingUtils.drawLine(g, new Line2D.Double(this.getCenter(), mousePt));
//    	}
    }
}
