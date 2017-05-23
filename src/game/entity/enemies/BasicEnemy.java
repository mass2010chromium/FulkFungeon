package game.entity.enemies;

import java.util.Random;

import game.Game;
import game.Items;
import game.entity.CopyPowerUp;
import game.entity.GameEnemies;
import utils.MathUtils;

/**
 * Basic Enemy class.
 * @author jcpen
 *
 */
public class BasicEnemy extends GameEnemies {

    private int shootTimer = 0;
    private int type;
    
	public BasicEnemy(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 75, 75, 3, 6, 100, null);
		type = rand.nextInt(3) + 1;
		setImage("enemies/Enemy-" + type + ".png");
	}

    @Override
    public void tick()
    {
    	walk();
    	if (MathUtils.distance(getCenter(), player.getCenter()) <= 1300) {
    		shootTimer++;
    	}
    	if (shootTimer >= 100) {
    		shootTimer = 0;
    		shootSpread(0.5, shootSpeed, 10, "enemies/enemy_proj" + type + ".png");
    	}
    }
    
    /**
     * Default walking algorithm.
     */
    public void walk()
    {
    	if (moveRandomly() || startRandomMovement(200)) {
    		
    	}
        else {
        	moveEigths(MathUtils.angle( player.getCenter(), getCenter()), moveSpeed);
        }
    }

	@Override
	protected void dropLoot() {
		if (Math.random() < 0.1) Game.INSTANCE.spawnEntity(new CopyPowerUp(
				getCenter().getX(), getCenter().getY(), Items.randomPowerUp()));
		player.addScore(10);
	}
}
