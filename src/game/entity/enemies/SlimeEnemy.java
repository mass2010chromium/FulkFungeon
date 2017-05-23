package game.entity.enemies;

import java.util.Random;

import game.entity.AnimatedEnemy;
import utils.MathUtils;

public class SlimeEnemy extends AnimatedEnemy {

	private int shootTimer;
	
	private static final String[] TEXTURE_NAMES = new String[]{"slime_green.png", "slime_pink.png", "slime_blue.png"};
	private int type;
	
	public SlimeEnemy(Double xCenter, Double yCenter, Random rand) {
		super(xCenter, yCenter, 100, 100, 1, 4, 400, 
				null, 20, 20, 9, 8);
		type = rand.nextInt(TEXTURE_NAMES.length) + 1;
		this.setImage("enemies/" + TEXTURE_NAMES[type - 1]);
		shootTimer = 0;
	}

	@Override
	public void tick() {
    	if (moveRandomly() || startRandomMovement(200)) {
    		
    	}
    	else {
			double angle = MathUtils.angle(player.getCenter(), getCenter());
			this.moveDirect(angle, 2);
    	}
		if (MathUtils.distance(getCenter(), player.getCenter()) <= 900) shootTimer++;
		if (shootTimer >= 200) {
			shootTimer = 0;
			for (int i = 0; i < 8; i++) {
				shootSpread(Math.PI / 4, shootSpeed, 5, "enemies/slime_proj" + type + ".png");
			}
		}
	}

	@Override
	protected void dropLoot() {
		player.addScore(10);
	}
}
