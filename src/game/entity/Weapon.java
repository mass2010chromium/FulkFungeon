package game.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import game.Game;
import game.Items;
import utils.DrawingUtils;


/**
 * 
 * Weapons to be picked up by the Player --enhance/augment projectiles fired by
 * Player
 *
 * @author smohidekar916
 * @version Apr 26, 2017
 * @author Period: 2
 * @author Assignment: Dungeon
 *
 */

public class Weapon extends GameTickable implements Comparable<Weapon>
{

    protected double maxCooldown;
    protected int cooldown;
    protected double shotPower;
    protected Projectile templateProjectile;
    protected String imgPath;

    private String name;
    private double baseDamage;
    
    /**
     * Amount to buff a weapon's dps by when it is upgraded.
     */
    protected static final double WEAPON_BUFF_AMOUNT = 0.1;
    
    /**
     * 
     * @param xCenter : x position
     * @param yCenter : y position
     * @param damage : Damage this weapon will do. Overriden if projectile is specified.
     * @param cooldown : How many ticks between shots.
     * @param shotPower : How fast the shot projectiles go.
     * @param imgPath : Image for this weapon.
     * @param proj : A projectile this weapon will fire.
     */
    public Weapon( double xCenter, double yCenter, double damage, double cooldown, double shotPower, 
    		String imgPath, Projectile proj, String name )
    {
        super( xCenter, yCenter, 200, 200, imgPath );
        this.imgPath = imgPath;
        this.maxCooldown = cooldown;
        this.cooldown = 0;
        if (proj == null) {
        	this.templateProjectile = new Projectile(null, Double.NaN, Double.NaN, 
        			20, 20, Double.NaN, Double.NaN, damage, "nullTexture.png");
        	this.setBaseDamage(damage);
        }
        else {
        	this.templateProjectile = new Projectile(null, 0, 0, 0, 0, proj) {
        		@Override
        		public void tick(Projectile inst) {
        			this.template.tick(inst);
        		}
        	};
        	this.setBaseDamage(templateProjectile.getDamage());
        }
        this.shotPower = shotPower;
        this.name = name;
    }
    
    public Weapon(double damage, double cooldown, double shotPower, String imgPath, Projectile proj, 
    		String name) {
    	this(0, 0, damage, cooldown, shotPower, imgPath, proj, name);
    }
    
    public Weapon(double x, double y, Weapon copy) {
    	this(x, y, 0, copy.getCooldown(), copy.getShotSpeed(), copy.imgPath, 
    			copy.getProjectileTemplate(), copy.getName());
    }
    
    /**
     * Upgrades this weapon once. Should get a 5% x base dps buff.
     * @param inst : Instance to act on
     */
    public void upgrade(Weapon inst) {
    	inst.templateProjectile.setDamage(inst.templateProjectile.getDamage() + 
    			inst.getBaseDamage() * WEAPON_BUFF_AMOUNT);
    }
    
    /**
     * @see Weapon#upgrade(Weapon)
     */
    public void upgrade() {
    	Items.getWeapon(getName()).upgrade(this);
    }
    
    /**
     * Gets the time between shots, should be constant.
     * @return Max Cooldown
     */
    public double getCooldown()
    {
        return maxCooldown;
    }
    
    public void setCooldown(double amount) {
    	maxCooldown = amount;
    }
    
    /**
     * Gets the time remaining between shots.
     * @return cooldown
     */
    public int getCurrentCooldown() {
    	return cooldown;
    }
    
    public void setCurrentCooldown(int amount) {
    	this.cooldown = amount;
    }
    
    public Projectile getProjectileTemplate() {
    	return this.templateProjectile;
    }
    
    /**
     * Get the projectile speed of this weapon.
     * @return
     */
    public double getShotSpeed() {
    	return this.shotPower;
    }
    
    public String getName() {
    	return name;
    }
    
    /**
     * Gets the damage this weapon does, per hit.
     * @return
     */
    public double getDamage() {
    	return templateProjectile.getDamage();
    }
    
    public void setDamage(double damage) {
    	templateProjectile.setDamage(damage);
    }

    @Override
    public void collideWith( GameItem other )
    {
        if ( other instanceof Player && Game.INSTANCE.getInput().keyDown(KeyEvent.VK_SPACE))
        {
        	((Player) other).pickupWeapon(this);
//        	w.moveTo(getCenter());
//        	w.setWidth(200);
//        	w.setHeight(200);
//        	w.setDead(false);
//        	Game.INSTANCE.spawnEntity(w);
        	this.setDead(true);
        }
    }

    /**
     * Draws this weapon, centered at a point.
     * @param g : Graphics object to draw with.
     * @param angle : Angle to draw at.
     * @param pos : Center point.
     * @param shooting : Whether the player is shooting or not.
     */
    public void drawWeapon(Graphics2D g, double angle, Point2D pos, boolean shooting) {
    	DrawingUtils.drawRotatedImage(g, getImage(), pos.getX(), pos.getY(), 1, 0, 0, 200, 200, angle, Game.INSTANCE);
    }

    /**
     * Updates a weapon, shooting and such.
     * @param shooter : Shooter of this weapon
     * @param angle : Angle aiming at
     * @param pos : Position of this weapon.
     * @param shooting : Whether to shoot or not.
     * @param inst : Weapon instance to update.
     */
    public void update( GameLiving shooter, double angle, Point2D pos, boolean shooting, Weapon inst )
    {
    	if (shooting) {
    		inst.cooldown++;
    		if (inst.cooldown >= inst.maxCooldown) {
    			shoot(shooter, angle, pos, inst.templateProjectile, inst.shotPower);
    			inst.cooldown = 0;
    		}
    	}
    	else {
    		inst.cooldown = 0;
    	}
    }
    
    /**
     * @see Weapon#update(GameLiving, double, Point2D, boolean, Weapon)
     * @param shooter
     * @param angle
     * @param pos
     * @param shooting
     */
    public void update( GameLiving shooter, double angle, Point2D pos, boolean shooting ) {
    	Items.getWeapon(getName()).update(shooter, angle, pos, shooting, this);
    }
    
	public void shoot(GameLiving shooter, double angle, Point2D pos, Projectile p, 
			double power) {
		Game.INSTANCE.spawnEntity(new Projectile(shooter, 
				pos.getX() + 50 * Math.cos(angle), pos.getY() + 50 * Math.sin(angle), 
				power * Math.cos(angle), power * Math.sin(angle), p));
	}
    
    @Override
    public void draw(Graphics g) {
    	drawWeapon((Graphics2D) g, -Math.PI / 4, this.getCenter(), false);
    }

	@Override
	public void tick() {}
	
	@Override
	public void doTick() {}

	@Override
	public int compareTo(Weapon arg0) {
		return name.compareToIgnoreCase(arg0.name);
	}

	public double getBaseDamage() {
		return baseDamage;
	}

	public void setBaseDamage(double baseDamage) {
		this.baseDamage = baseDamage;
	}
}
