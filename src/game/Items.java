package game;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.bosses.BossFight;
import game.bosses.CheesyPoofsFight;
import game.entity.AnimatedWeapon;
import game.entity.GameEnemies;
import game.entity.GameLiving;
import game.entity.GamePowerUps;
import game.entity.Player;
import game.entity.Projectile;
import game.entity.Weapon;
import game.entity.enemies.BasicEnemy;
import game.entity.enemies.SlimeEnemy;
import game.entity.enemies.SnakeEnemy;
import utils.WeightedRandomSelector;

/**
 * Game items stored here.
 * @author jcpen
 *
 */
public class Items {
	
	private static final Items INSTANCE = new Items();
	
	private static final Random RAND = new Random();
	
	private WeightedRandomSelector<String, Weapon> weapons;
	private WeightedRandomSelector<String, Class<? extends GameEnemies>> enemies;
	private List<GamePowerUps> powerups;
	private List<BossFight> bosses;
	
	private Items() {
		
		weapons = new WeightedRandomSelector<String, Weapon>();
		enemies = new WeightedRandomSelector<String, Class<? extends GameEnemies>>();
		powerups = new ArrayList<GamePowerUps>();
		bosses = new ArrayList<BossFight>();
		
		//Adding weapons
		addWeapon(0, new Weapon(0, 30, 6, "weapons/starter.png", 
				new Projectile(null, 0, 0, 20, 20, 0, 0, 6, null), "NULL"));
		
		addWeapon(0, new Weapon(0, 250, 6, "weapons/sulfuras_anim.png", 
				new Projectile(null, 0, 0, 100, 100, 0, 0, 100, "weapons/sulfuras_projectile.png"), "Sulfuras"));
		
		addWeapon(0, new Weapon(0, 2, 6, "weapons/unicorn_small.png", 
				new Projectile(null, 0, 0, 20, 20, 0, 0, 0.5, "weapons/unicorn_projectile.png"), "Unihorn"));
		
		addWeapon(1, new Weapon(0, 30, 16, "weapons/mouse.png", 
				new Projectile(null, 0, 0, 20, 20, 0, 0, 10, "weapons/mouse_projectile.png"), "Mouse"));
		
		addWeapon(0.75, new AnimatedWeapon(0, 32, 12, "weapons/Hilt.png", 
				new Projectile(null, 0, 0, 20, 20, 0, 0, 15, "weapons/lightBlueBall.png"), "Hilt", 4, 8));
		
		addWeapon(1, new AnimatedWeapon(0, 50, 16, "weapons/lightpole.png", 
				new Projectile(null, 0, 0, 20, 20, 0, 0, 20, "weapons/yellowBall.png"), "Light Pole", 1, 1));
		
		addWeapon(1, new Weapon(0, 25, 8, "weapons/facebook.png", 
				new Projectile(null, 0, 0, 40, 40, 0, 0, 10, "weapons/facebook_projectile.png"), "Facebook"));
		
		addWeapon(0, new Weapon(0, 25, 12, "weapons/robot.png", 
				new Projectile(null, 0, 0, 20, 20, 0, 0, 13, "weapons/robot_projectile.png"), "Steamfunk") {
			
			@Override
		    public void update( GameLiving shooter, double angle, Point2D pos, boolean shooting, Weapon inst )
		    {
		    	if (shooting) {
		    		inst.setCurrentCooldown(inst.getCurrentCooldown() + 1);
		    		if (inst.getCurrentCooldown() >= inst.getCooldown()) {
		    			angle += Math.random() / 2;
		    			angle -= 0.25;
		    			shoot(shooter, angle, pos, templateProjectile, inst.getShotSpeed());
		    			inst.setCurrentCooldown(0);;
		    		}
		    	}
		    	else {
		    		inst.setCurrentCooldown(0);
		    	}
		    }
		});
		
		addWeapon(0.75, new AnimatedWeapon(0, 25, 12, "weapons/crossBow2.png", 
				new Projectile(null, 0, 0, 20, 20, 0, 0, 10, "weapons/yellowBall.png"), "Cross Bow", 1, 1) {
			private Projectile t2 = 
					new Projectile(null, 0, 0, 20, 20, 0, 0, 10, "weapons/lightBlueBall.png");
			@Override
			public void upgrade() {
				super.upgrade();
		    	t2.setDamage(t2.getDamage() + getBaseDamage() * WEAPON_BUFF_AMOUNT);
			}
			@Override
		    public void update( GameLiving shooter, double angle, Point2D pos, boolean shooting, Weapon inst )
		    {
		    	if (shooting) {
		    		inst.setCurrentCooldown(inst.getCurrentCooldown() + 1);;
		    		if (inst.getCurrentCooldown() >= inst.getCooldown()) {
		    			shoot(shooter, angle + Math.PI * 0.25, pos, inst.getProjectileTemplate(), shotPower);
		    			shoot(shooter, angle + Math.PI * 0.75, pos, t2, shotPower);
		    			shoot(shooter, angle + Math.PI * 1.25, pos, inst.getProjectileTemplate(), shotPower);
		    			shoot(shooter, angle + Math.PI * 1.75, pos, t2, shotPower);
		    			inst.setCurrentCooldown(0);
		    		}
		    	}
		    	else {
		    		inst.setCurrentCooldown(0);
		    	}
		    	AnimatedWeapon wep = (AnimatedWeapon) inst;
		    	wep.incrementAnimationFrame(shooting);
		    }
		});
		
		addWeapon(0.75, new AnimatedWeapon(15, 100, 5, "weapons/catherinegun.png", null, "Catherine", 4, 25) {
			@Override
		    public void update( GameLiving shooter, double angle, Point2D pos, boolean shooting, Weapon inst )
		    {
		    	if (shooting) {
		    		inst.setCurrentCooldown(inst.getCurrentCooldown() + 1);
		    		if (inst.getCurrentCooldown() >= inst.getCooldown()) {
		    			Game.INSTANCE.spawnEntity(new Projectile(
		    					shooter, pos.getX() + 50 * Math.cos(angle),	pos.getY() + 50 * Math.sin(angle), 
		    					 20, 20, inst.getShotSpeed() * Math.cos(angle), inst.getShotSpeed() * Math.sin(angle), 
		    					 getDamage(), "weapons/catherinegun_projectile.png") {
		    				double angleCount = 0;
		    				@Override
		    				public void tick() {
		    					super.tick();
		    					double shotAngle = angle - Math.PI / 2;
		    					if (angleCount >= Math.PI) shotAngle -= Math.PI;
				    			if (life % 2 == 0) Game.INSTANCE.spawnEntity(
				    					new Projectile(this.getShooter(), 
				    					getCenter().getX() + 10 * Math.cos(shotAngle), getCenter().getY() + 10 * Math.sin(shotAngle), 
				    					10, 10, 8 * Math.cos(shotAngle), 8 * Math.sin(shotAngle), getDamage() / 6, 
				    					"weapons/yellowBall.png"));
				    			angleCount += Math.PI / 64;
		    					angleCount %= Math.PI * 2;
		    					angle += Math.PI / 64;
		    				}
		    			});
			    		inst.setCurrentCooldown(0);
		    		}
		    	}
		    	else {
		    		inst.setCurrentCooldown(0);
		    	}
				AnimatedWeapon wep = (AnimatedWeapon) inst;
				wep.incrementAnimationFrame(shooting);
		    }
		});
		
		addWeapon(0.75, new AnimatedWeapon(0, 120, 30, "weapons/ragnell_anim.png", 
				new Projectile(null, 0, 0, 30, 30, 0, 0, 50, "weapons/ragnell_projectile.png"), "Ragnell", 10, 12){
			@Override
			public void upgrade(Weapon inst) {
				AnimatedWeapon wep = (AnimatedWeapon) inst;
				double currentDPT = inst.getDamage() / inst.getCooldown();
				currentDPT = currentDPT + (inst.getBaseDamage() / 120) * WEAPON_BUFF_AMOUNT;
				inst.setCooldown(getDamage() / currentDPT);
				this.animationTiming = (int) Math.max(1, getCooldown() / wep.getAnimationFrames());
			}
		});
		
		addWeapon(0, new Weapon(0, 3, 0.1, "weapons/saltshaker.png", 
				new Projectile(null, 0, 0, 10, 10, 0, 0, 3, "weapons/whiteBall.png") {
		}, "Salty") {
			@Override
		    public void update( GameLiving shooter, double angle, Point2D pos, 
		    		boolean shooting, Weapon inst)
		    {
		    	if (shooting) {
		    		inst.setCurrentCooldown(inst.getCurrentCooldown() + 1);
		    		if (inst.getCurrentCooldown() >= inst.getCooldown()) {
		    			for (int i = 0; i < 2; i++) {
		    				inst.shoot(shooter, angle + Math.random() - 0.5, pos, 
		    						inst.getProjectileTemplate(), inst.getShotSpeed());
				    		inst.setCurrentCooldown(0);
		    			}
		    		}
		    	}
		    	else {
		    		inst.setCurrentCooldown(0);
		    	}
		    }
		});
		
		addWeapon(0, new AnimatedWeapon(0, 300, 4, "weapons/alkatraz.png", 
				new Projectile(null, 0, 0, 100, 100, 0, 0, 80, "weapons/lightGreenBall.png") {
			@Override
			public void setDead(boolean dead) {
				if (dead && !isDead()) {
					for (int i = 0; i < 80; i++) {
						Projectile p = new Projectile(getShooter(), 
								getCenter().getX(), getCenter().getY(), 10, 10, 
								getDamage() / 20 * Math.cos(angle * i), 
								getDamage() / 20 * Math.sin(angle * i), 
								getDamage() / 80, "weapons/lightGreenBall.png");
						p.setLife(175);
						Game.INSTANCE.spawnEntity(p);
					}
				}
				super.setDead(dead);
			}
		}, "Alkatraz", 4, 75){
		});
		
		//Adding Enemies
		enemies.add(7, "Basic", BasicEnemy.class);
		enemies.add(1, "Slime", SlimeEnemy.class);
		enemies.add(2, "Snake", SnakeEnemy.class);
		
		//Adding powerups
		powerups.add(new GamePowerUps("powerups/heart.png") {

			@Override
			public void applyPowerUp(Player p) {
				p.addMaxHealth(5);
			}
			
		});
		powerups.add(new GamePowerUps("powerups/healing.png") {

			@Override
			public void applyPowerUp(Player p) {
				p.adjustHealth(50);
			}
			
		});
		
		//Adding bosses
		bosses.add(new CheesyPoofsFight());
	}
	private void addWeapon(double weight, Weapon weapon) {
		weapons.add(weight, weapon.getName(), weapon);;
	}
	
	/**
	 * Gets a random weapon from the weapon list.
	 * @return A random weapon.
	 */
	public static Weapon randomWeapon() {
		return INSTANCE.weapons.randomItem();
	}
	
	public static Weapon getWeapon(String name) {
		return INSTANCE.weapons.get(name);
	}
	
	public static Class<? extends GameEnemies> randomEnemy() {
		return INSTANCE.enemies.randomItem();
	}
	
	public static GamePowerUps randomPowerUp() {
		return INSTANCE.powerups.get(RAND.nextInt(INSTANCE.powerups.size()));
	}
	
	public static BossFight randomBossFight() {
		return INSTANCE.bosses.get(RAND.nextInt(INSTANCE.bosses.size()));
	}
}
