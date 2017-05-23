package game.entity;

/**
 * A tickable game item. Recieves updates every game tick.
 * @author jcpen
 *
 */
public abstract class GameTickable extends GameItem
{
	protected double lastX;
	protected double lastY;
	private double lastVX, lastVY;
	protected double tmpX;
	protected double tmpY;
	protected double vX, vY;
	protected double tmpVX, tmpVY;
	
	public GameTickable(double xCenter, double yCenter, double xSize, double ySize, String imgPath) {
		super(xCenter, yCenter, xSize, ySize, imgPath);
	}
	
	/**
	 * Increase this object's velocity.
	 * @param x : x velocity
	 * @param y : y velocity
	 */
	public void accelerate(double x, double y) {
		tmpVX += x;
		tmpVY += y;
		vX += x;
		vY += y;
	}
	
	public double getVX() {
		return vX;
	}
	
	public double getVY() {
		return vY;
	}
	
	public double getLastVX() {
		return lastVX;
	}
	
	public double getLastVY() {
		return lastVY;
	}

	/**
	 * Ticks this entity, moving it and calling tick().
	 */
	public void doTick() {
		lastX = getCenter().getX();
		lastY = getCenter().getY();
		lastVX = vX;
		lastVY = vY;
		this.moveBy(vX, vY);
		tmpX = getCenter().getX();
		tmpY = getCenter().getY();
		tick();
		tmpVX = vX;
		tmpVY = vY;
	}
	
	public abstract void tick();
    
	public void postTick() {
		moveTo(tmpX, tmpY);
		vX = tmpVX;
		vY = tmpVY;
	}
	
	/**
	 * Call this when this tickable item collides in a wall and wants the collision resolved.
	 * @param wall : Wall you ran into
	 */
    public void resolveWallCollision(GameItem wall) {
    	double collideX = lastX - getWidth() / 2, collideY = lastY - getHeight() / 2;
    	double wallX = wall.getCenter().getX() + wall.getWidth() / 2;
    	double wallY = wall.getCenter().getY() + wall.getHeight() / 2;
    	if (vX > 0) {
    		collideX += getWidth();
    		wallX -= wall.getWidth();
    	}
    	if (vY > 0) {
    		collideY += getHeight();
    		wallY -= wall.getHeight();
    	}
    	double xTime = (wallX - collideX) / vX;
    	double yTime = (wallY - collideY) / vY;
    	if (xTime < 0) xTime = Double.POSITIVE_INFINITY;
    	if (yTime < 0) yTime = Double.POSITIVE_INFINITY;
//    	System.out.println(xTime + ", " + yTime);
    	if ((xTime <= yTime || (vY > 0 ? (collideY + vY * xTime <= wallY) : (collideY + vY * xTime >= wallY)))
    			/*&& (vY > 0 ? (collideY + vY * xTime >= wallY) : (collideY + vY * xTime <= wallY))*/) {
    		tmpX = lastX;
    		tmpY = getCenter().getY() - vY;
    		tmpVX = 0;
    	}
    	else {
    		tmpX = getCenter().getX() - vX;
    		tmpY = lastY;
    		tmpVY = 0;
    	}
    }
}
