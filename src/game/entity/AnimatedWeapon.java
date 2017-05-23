package game.entity;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import game.Game;
import utils.DrawingUtils;

public class AnimatedWeapon extends Weapon{

	protected int frame;
	private int animationFrames;
	protected int animationTiming;
	protected boolean lastShooting;
	
	public AnimatedWeapon(double xCenter, double yCenter, double damage, double cooldown, double shotPower, 
			String imgPath, Projectile proj, String name, int animationFrames, int animationTiming) {
		super(xCenter, yCenter, damage, cooldown, shotPower, imgPath, proj, name);
		// TODO Auto-generated constructor stub
		frame = 0;
		this.setAnimationFrames(animationFrames);
		this.animationTiming = animationTiming;
	}
	public AnimatedWeapon(double damage, int cooldown, double shotPower, 
			String imgPath, Projectile proj, String name, int animationFrames, int animationTiming) {
		this(0, 0, damage, cooldown, shotPower, imgPath, proj, name, animationFrames, animationTiming);
	}

	@Override
    public void drawWeapon(Graphics2D g, double angle, Point2D pos, boolean shooting) {
		int xPos = 0;
		if (shooting) xPos = 200;
		int frameToDraw = frame / animationTiming;
    	DrawingUtils.drawRotatedImage(g, getImage(), pos.getX(), pos.getY(), 1, 
    			xPos, 200 * frameToDraw, xPos + 200, 200 * (frameToDraw + 1), 
    			angle, Game.INSTANCE);
    }
    
	/**
	 * Copies an animatedWeapon.
	 * @param x : x position
	 * @param y : y position
	 * @param copy : Weapon to copy properties from
	 */
    public AnimatedWeapon(double x, double y, AnimatedWeapon copy) {
    	this(x, y, 0, copy.maxCooldown, copy.shotPower, copy.imgPath,
    			copy.templateProjectile, copy.getName(), 
    			copy.getAnimationFrames(), copy.animationTiming);
    }

	@Override
    public void update( GameLiving shooter, double angle, Point2D pos, boolean shooting, 
    		Weapon inst )
    {
		super.update(shooter, angle, pos, shooting, inst);
		AnimatedWeapon wep = (AnimatedWeapon) inst;
		wep.incrementAnimationFrame(shooting);
    }
	
	public int getAnimationFrames() {
		return animationFrames;
	}
	public void setAnimationFrames(int animationFrames) {
		this.animationFrames = animationFrames;
	}

	public boolean isLastShooting() {
		return lastShooting;
	}
	
	public void setLastShooting(boolean b) {
		lastShooting = b;
	}
	
	public void incrementAnimationFrame(boolean shooting) {
		if (shooting && !lastShooting) frame = 0;
		lastShooting = shooting;
		if (cooldown == 1) frame = 0;
		frame = (frame + 1) % (getAnimationFrames() * animationTiming);
	}
}
