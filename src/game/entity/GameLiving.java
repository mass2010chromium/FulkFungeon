package game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Living entity, with health. Differentiates walls from players, etc.
 * @author jcpen
 *
 */
public abstract class GameLiving extends GameTickable implements ICollidable{

	private double health;
	protected double maxHealth;
	
	private Set<GameEffects> effects;
	
	public GameLiving(double xCenter, double yCenter, double xSize, double ySize, 
			double health, String imgPath) {
		super(xCenter, yCenter, xSize, ySize, imgPath);
		this.health = health;
		this.maxHealth = health;
		effects = new TreeSet<GameEffects>();
	}

	@Override
	public void doTick() {
		super.doTick();
		Iterator<GameEffects> iter = effects.iterator();
		while (iter.hasNext()) {
			if (iter.next().updateInstance(null)) {
				iter.remove();
			}
		}
	}
	
	/**
	 * Get the potion effects affecting this living entity.
	 * @return
	 */
	public Set<GameEffects> getActiveEffects() {
		return this.effects;
	}
	
	/**
	 * Add a new potion effect to this entity.
	 * @param effect
	 */
	public void addEffect(GameEffects effect) {
		this.effects.add(effect);
	}
	
	/**
	 * Adjusts this entity's health by the amount.
	 * @param amount : Amount to change health by. Negative to damage, positive to heal.
	 * @return Adjusted health.
	 */
	public double adjustHealth(double amount) {
		health += amount;
		if (health <= 0 && !isDead()) {
			setDead(true);
		}
		if (health > maxHealth) {
			health = maxHealth;
		}
		return health;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	@Override
	public void collideWith(GameItem other) {
		if (other instanceof ICollidable) this.resolveWallCollision(other);
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		drawHealthBar(g);
	}
	
	/**
	 * Draws the hp bar for this living entity.
	 * @param g
	 */
	protected void drawHealthBar(Graphics g) {
		Rectangle2D bBox = this.getBoundingBox();
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.fillRect((int) bBox.getMinX(), (int) bBox.getMinY() - 10, 
				(int) (getWidth() * (health / maxHealth)), 10);
		g.setColor(c);
	}
}
