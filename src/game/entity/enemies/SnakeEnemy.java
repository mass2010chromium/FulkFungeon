package game.entity.enemies;

import java.awt.Graphics;
import java.util.Random;

import game.Game;
import game.Items;
import game.entity.AnimatedEnemy;
import game.entity.CopyPowerUp;
import game.entity.Projectile;
import utils.MathUtils;

public class SnakeEnemy extends AnimatedEnemy{

	private int factor = 1;
	private int shotCooldown;
	
	public SnakeEnemy(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 40, 40, 3.5, 4.5, 50, "enemies/snake.png", 44, 44, 5, 8);
		shotCooldown = 0;
	}

	@Override
	protected void dropLoot() {
		if (Math.random() < 0.1) Game.INSTANCE.spawnEntity(new CopyPowerUp(
				getCenter().getX(), getCenter().getY(), Items.randomPowerUp()));
		player.addScore(10);
	}

	@Override
	public void tick() {
    	if (moveRandomly() || startRandomMovement(50)) {
    		
    	}
    	else {
    		this.moveDirect(MathUtils.angle(player.getCenter(), getCenter()), moveSpeed);
        	shotCooldown++;
        	if (shotCooldown >= 30) {
    	    	Projectile p = super.shootSpread(0.25, shootSpeed, 7, "enemies/snake_projectile.png");
    	    	p.setLife(70);
    	    	shotCooldown = 0;
        	}
    	}
	}
	
	@Override
	public double adjustHealth(double amount) {
		double tmp = super.adjustHealth(amount);
		if (amount < 0) {
			randMove = 50;
            moveEigths(Math.PI * 2 * Math.random(), moveSpeed);
		}
		return tmp;
	}

	@Override
    public void draw(Graphics g) {
    	g.translate((int) getCenter().getX(), (int) getCenter().getY());
    	int tmp = (int) Math.signum(vX);
    	if (tmp != 0) factor = tmp;
    	int halfW = (int) (getWidth() / 2);
    	int halfH = (int) (getHeight() / 2);
    	g.drawImage(getImage(), factor * halfW, -halfH, -factor * halfW, halfH, 
    			imageX * (curFrame / tickRate), 0, imageX * (curFrame / tickRate + 1), imageY, Game.INSTANCE);
    	g.translate(-(int) getCenter().getX(), -(int) getCenter().getY());
    	drawHealthBar(g);
    }
}
