package game.bosses;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

import game.Game;
import game.entity.GameBosses;
import game.entity.GameItem;
import game.entity.Player;
import utils.MathUtils;

public class CheesyPoofs extends GameBosses{

	private int factor = 1;
	private int ammo;
	private int shotCooldown;
	private boolean shootingSpree;
	private int moveCooldown;
	public CheesyPoofs(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 110 * 1.5, 78 * 1.5, 4.5, 2000, 12, "bossfights/cheesypoofs/254.png");
		ammo = 0;
		shotCooldown = 0;
		shootingSpree = false;
		moveCooldown = 0;
	}

	@Override
	protected void dropLoot() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collideWith(GameItem other) {
		super.collideWith(other);
		if (other instanceof FuelEnemy) {
			other.setDead(true);
			if (ammo < 254) ammo++;
		}
//		if (other instanceof Player) {
//			this.tmpVX = 0;
//			this.tmpVY = 0;
//			this.moveBy(-vX, -vY);
//		}
	}

	@Override
	public void tick() {
    	if (moveRandomly() || startRandomMovement(50)) {
			if (MathUtils.distance(getCenter(), player.getCenter()) < 200 && ammo > 0) {
	    		shotCooldown++;
				
				if (shotCooldown >= 3) {
					this.shootSpread(0.5, this.shootSpeed, 1.25, "weapons/robot_projectile.png");
					ammo--;
					shotCooldown = 0;
				}
			}
    	}
		if (shootingSpree) {
    		this.moveDirect(MathUtils.angle(player.getCenter(), getCenter()), moveSpeed);
			if (MathUtils.distance(getCenter(), player.getCenter()) < 600 && ammo > 0) {
	    		shotCooldown++;
				
				if (shotCooldown >= 3) {
					this.shootSpread(0.5, this.shootSpeed, 1.25, "weapons/robot_projectile.png");
					ammo--;
					shotCooldown = 0;
				}
				if (ammo == 0) {
					shootingSpree = false;
				}
			}
		}
		else if (randMove == 0){
			shotCooldown = 0;
//			moveCooldown++;
//			if (moveCooldown >= 10) {
				moveToFuel();
//				moveCooldown = 0;
//			}
			if (ammo > 100) {
				shootingSpree = true;
			}
		}
	}
	
	private int curX = 0;
	private int curY = 0;
	private double curDist = Double.POSITIVE_INFINITY;
	
	private void moveToFuel() {
		int[][] fuelCells = new int[34][18];
		for (int i = 0; i < fuelCells.length; i++) {
			for (int j = 0; j < fuelCells[i].length; j++) {
				fuelCells[i][j] = 0;
			}
		}
		List<GameItem> items = Game.INSTANCE.getEntities();
		for (GameItem e : items) {
			if (!(e instanceof FuelEnemy)) continue;
			try {
				fuelCells[(int) Math.round(e.getCenter().getX() / 100)]
						[(int) Math.round(e.getCenter().getY() / 100)]++;
			} catch (ArrayIndexOutOfBoundsException exc) {
				System.out.println("WTF " + exc);
			}
		}
		double consideredDist = curDist * 0.125;
		for (int i = 0; i < fuelCells.length; i++) {
			for (int j = 0; j < fuelCells[i].length; j++) {
				double dist = MathUtils.distance(getCenter(), 
						new Point2D.Double((curX + 0.5) * 100, (curY + 0.5) * 100));
				if (fuelCells[curX][curY] / consideredDist < fuelCells[i][j] / dist) {
					curX = i;
					curY = j;
					curDist = dist;
					consideredDist = curDist * 0.125;
				}
			}
		}
		double angle = MathUtils.angle(
				new Point2D.Double((curX + 0.5) * 100, (curY + 0.5) * 100), 
				getCenter());
		vX = moveSpeed * Math.cos(angle);
		vY = moveSpeed * Math.sin(angle);
	}

	@Override
	public double adjustHealth(double amount) {
		return super.adjustHealth(amount);
//		return 1;
	}
	
	@Override
	public void postTick() {
		super.postTick();
//		if (Math.abs(lastX - getCenter().getX()) < 0.1 && 
//				Math.abs(lastY - getCenter().getY()) < 0.1) {
//            moveEigths(Math.PI * 2 * Math.random(), moveSpeed);
//		}
	}
	
	@Override
	public void draw(Graphics g) {
    	g.translate((int) getCenter().getX(), (int) getCenter().getY());
    	int tmp = (int) Math.signum(vX);
    	if (tmp != 0) factor = tmp;
    	int halfW = (int) (getWidth() / 2);
    	int halfH = (int) (getHeight() / 2);
    	g.drawImage(getImage(), factor * halfW, -halfH, -factor * halfW, halfH, 
    			0, 0, 110, 78, Game.INSTANCE);
    	g.translate(-(int) getCenter().getX(), -(int) getCenter().getY());
    	drawHealthBar(g);
	}
    
	@Override
    protected boolean startRandomMovement(int delayTime) {
        if ( Math.abs( vX ) <= 0.5 && Math.abs( vY ) <= 0.5 )
        {
            randMove = delayTime;
            moveEigths(Math.PI * 2 * Math.random(), moveSpeed);
            moveRandomly();
            return true;
        }
        return false;
    }

	@Override
    protected boolean moveRandomly() {
        if ( Math.abs( vX ) <= 0.5 && Math.abs( vY ) <= 0.5 )
        {
        	randMove = 0;
        	return false;
        }
        if ( randMove > 0 )
        {
            randMove--;
            return true;
        }
        return false;
    }
}
