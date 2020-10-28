package game.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * 
 * Projectiles fired by Player and GameEnemies
 *
 *  @author  smohidekar916
 *  @version Apr 26, 2017
 *  @author  Period: 2
 *  @author  Assignment: Dungeon
 *
 */

public class Projectile extends GameTickable
{

	private double damage;
	protected double life;
	private GameItem shooter;
	protected String imgPath;
	
	protected double angle = 0;
	
	protected Projectile template;
	
	private int maxLife;
	
	/**
	 * Creates a new Projectile.
	 * @param shooter : Its shooter, null for templates.
	 * @param xCenter : X position, 0 for templates.
	 * @param yCenter : Y position, 0 for templates.
	 * @param xSize : Width.
	 * @param ySize : Height.
	 * @param vX : X velocity, 0 for templates.
	 * @param vY : Y velocity, 0 for templates.
	 * @param damage : Damage this projectile will do.
	 * @param imgPath : Image path for this projectile's image.
	 * @param life : Amount of ticks after which this projectile will expire.
	 */
	public Projectile(GameItem shooter, double xCenter, double yCenter, double xSize, double ySize, 
			double vX, double vY, double damage, String imgPath, int life) {
		super(xCenter, yCenter, xSize, ySize, imgPath);
		this.vX = vX;
		this.vY = vY;
		this.damage = damage;
		this.life = 0;
		this.shooter = shooter;
		this.imgPath = imgPath;
		
		if (vX != 0 && vY != 0) {
			angle = Math.atan2(vY, vX);
		}
		template = null;
		maxLife = life;
	}
	
	public Projectile(GameItem shooter, double xCenter, double yCenter, double xSize, double ySize, 
			double vX, double vY, double damage, String imgPath) {
		this(shooter, xCenter, yCenter, xSize, ySize, vX, vY, damage, imgPath, 200);
	}
	
	public Projectile(GameItem shooter, double xCenter, double yCenter, double vX, double vY, 
			Projectile template) {
		this(shooter, xCenter, yCenter, template.getWidth(), template.getHeight(), vX, vY, 
				template.getDamage(), template.imgPath);
		this.template = template;
	}
	
	public void setLife(int life) {
		this.life = life;
	}

	@Override
	public void tick() {
		if (template == null) {
			tick(this);
		}
		else {
			template.tick(this);
		}
	}
	
	/**
	 * Instance tick. For "static" projectile template objects.
	 * @param inst : Projectile instance to tick.
	 */
	public void tick(Projectile inst) {
		inst.life++;
		if (inst.life >= maxLife) {
			inst.setDead(true);
		}
	}
	
	public double getLife() {
		return this.life;
	}
	
	public int getMaxLife() {
		return this.maxLife;
	}
	
	@Override
	public void draw(Graphics g) {
		g.translate((int) getCenter().getX(), (int)getCenter().getY());
		((Graphics2D) g).rotate(angle);
		g.translate(-(int) getCenter().getX(), -(int)getCenter().getY());
		super.draw(g);
		g.translate((int) getCenter().getX(), (int)getCenter().getY());
		((Graphics2D) g).rotate(-angle);
		g.translate(-(int) getCenter().getX(), -(int)getCenter().getY());
	}
	
	/**
	 * Kills itself.
	 */
	@Override
	public void collideWith(GameItem other) {
		if (other == shooter) return;
		if (shooter instanceof GameEnemies && other instanceof GameEnemies) {
			return;
		}
		if (other instanceof GameLiving) {
			double damageDealt = -damage;
			((GameLiving) other).adjustHealth(damageDealt);
		}
		this.setDead(true);
		other.collideWith(this);
	}
	
	public GameItem getShooter() {
		return this.shooter;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
}
