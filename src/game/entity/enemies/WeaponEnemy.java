package game.entity.enemies;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import game.Factory;
import game.Game;
import game.Items;
import game.entity.CopyPowerUp;
import game.entity.GameEnemies;
import game.entity.Player;
import game.entity.Weapon;
import utils.MathUtils;

public class WeaponEnemy extends GameEnemies{

	private Weapon weapon;
	private double angle;
	private int shootCoolDown;
	
	public WeaponEnemy(Double xCenter, Double yCenter, Random rand) {
        super( xCenter, yCenter, 50, 100, 2, 0, 150, "enemies/anti_fulk.png" );
        weapon = Factory.createWeapon(0, 0, Items.randomWeapon());
//        weapon = Items.getWeapon("Ragnell");
        weapon.setDamage(weapon.getDamage() * Game.INSTANCE.getMap().getDifficulty() / 2);
        angle = 0;
        shootCoolDown = 0;
	}

	@Override
	public void tick() {
    	if (moveRandomly() || startRandomMovement(200)) {
    		
    	}
    	else {
    		angle = MathUtils.angle(player.getCenter(), getCenter());
			moveEigths(angle, moveSpeed);
    	}
		shootCoolDown++;
		if (shootCoolDown >= 4) {
			shootCoolDown = 0;
			weapon.update(this, MathUtils.angle(player.getCenter(), MathUtils.add(getCenter(), Player.HAND_POS)), 
					MathUtils.add(getCenter(), Player.HAND_POS), true);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
    	weapon.drawWeapon((Graphics2D) g, angle, MathUtils.add(getCenter(), Player.HAND_POS), true);
	}

	@Override
	protected void dropLoot() {
		double result = Math.random();
		if (result < 0.25) Game.INSTANCE.spawnEntity(new CopyPowerUp(
				getCenter().getX(), getCenter().getY(), Items.randomPowerUp()));
		else {
			Weapon wep = Items.getWeapon(weapon.getName());
			Game.INSTANCE.spawnEntity(
					Factory.createWeapon(getCenter().getX(), getCenter().getY(), Items.getWeapon(wep.getName())));
		}
		player.addScore(25);
	}
}
