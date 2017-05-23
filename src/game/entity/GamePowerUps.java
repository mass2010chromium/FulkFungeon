package game.entity;
/**
 * 
 *  PowerUps to be picked up by the Player
 *
 *  @author  smohidekar916
 *  @version Apr 26, 2017
 *  @author  Period: 2
 *  @author  Assignment: Dungeon
 *
 */
public abstract class GamePowerUps extends GameTickable
{
	protected String imgPath;
    public GamePowerUps(double xCenter, double yCenter, double xSize, double ySize, String imgPath) {
		super(xCenter, yCenter, xSize, ySize, imgPath);
		this.imgPath = imgPath;
	}
    
    /**
     * Create a template powerup.
     * @param imgPath : Sprite img path
     */
    public GamePowerUps(String imgPath) {
    	super(0, 0, 0, 0, imgPath);
    	this.imgPath = imgPath;
    }

	@Override
	public void collideWith(GameItem other) {
		if (other instanceof Player) {
			applyPowerUp((Player) other);
			this.setDead(true);
		}
	}
	
	@Override
	public void tick() {}
	
	@Override
	public void doTick() {}
	
	/**
	 * Apply a powerup to the player (+hp, +dmg, etc).
	 * @param p
	 */
	public abstract void applyPowerUp(Player p);
}
