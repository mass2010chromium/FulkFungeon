package game.entity;

/**
 * An animated projectile.
 * @author jcpen
 *
 */
//TODO
public class AnimatedProjectile extends Projectile{

	
	public AnimatedProjectile(GameItem shooter, double xCenter, double yCenter, 
			double xSize, double ySize, double vX, double vY, double damage, 
			String imgPath, int imageX, int imageY, int frames, int frameTiming) {
		super(shooter, xCenter, yCenter, xSize, ySize, vX, vY, damage, imgPath);
		// TODO Auto-generated constructor stub
	}

}
