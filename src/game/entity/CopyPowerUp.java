package game.entity;

/**
 * Class for powerups actually spawned into the game.
 * @author jcpen
 *
 */
public class CopyPowerUp extends GamePowerUps{

	private GamePowerUps effect;
	
	/**
	 * Copies a GamePowerUp's abilities.
	 * @param xCenter : y position
	 * @param yCenter : x position
	 * @param pwrUp : powerup to copy
	 */
	public CopyPowerUp(double xCenter, double yCenter, GamePowerUps pwrUp) {
		super(xCenter, yCenter, 100, 100, pwrUp.imgPath);
		effect = pwrUp;
	}

	@Override
	public void applyPowerUp(Player p) {
		effect.applyPowerUp(p);
	}

}
