package game.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import game.Game;

/**
 * 
 *  Anything inside the game 
 *  -- implements collision detection
 *  @author  smohidekar916
 *  @version Apr 26, 2017
 *  @author  Period: 2
 *  @author  Assignment: Dungeon
 */

public abstract class GameItem
{
	private double xCenter, yCenter, xSize, ySize;
	private Image image;
	private boolean isDead;
	
	/**
	 * Creates a new GameItem.
	 * @param xCenter : x position of this GameItem's center.
	 * @param yCenter : y position
	 * @param xSize : x size, or width
	 * @param ySize : y size, or height
	 * @param img : image to use for this GameItem. Null will use the null texture.
	 */
	public GameItem(double xCenter, double yCenter, double xSize, double ySize, String imgPath) {
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.xSize = xSize;
		this.ySize = ySize;
		if (imgPath == null || imgPath.isEmpty()) {
			imgPath = "nullTexture.png";
		}
		this.image = Game.getImage(imgPath);
		this.isDead = false;
	}
	
	/**
	 * Sets the image of this game item.
	 * @param imgPath : The path to this image
	 */
	public void setImage(String imgPath) {
		if (imgPath == null || imgPath.isEmpty()) {
			imgPath = "nullTexture.png";
		}
		this.image = Game.getImage(imgPath);
	}
	
	/**
	 * Check if this item is dead.
	 * @return true if this is dead, false otherwise.
	 */
	public boolean isDead() {
		return isDead;
	}
	
	/**
	 * Set whether this item is dead or not. Dead items are removed in the next tick.
	 * @param dead : Whether this item is dead or not.
	 */
	public void setDead(boolean dead) {
		isDead = dead;
	}
	
	public double getWidth() {
		return xSize;
	}
	
	public double getHeight() {
		return ySize;
	}
	
	public void setWidth(double w) {
		xSize = w;
	}
	
	public void setHeight(double h) {
		ySize = h;
	}
	
	/**
	 * Called when this item collides with another item.
	 * @param other
	 */
	public abstract void collideWith(GameItem other);
	
	/**
	 * Checks if this GameItem collides with another one.
	 * @param other : Other gameItem
	 * @return True if their bounding boxes intersect, false otherwise.
	 */
    public boolean checkCollision(GameItem other) {
    	return other != this && other instanceof ICollidable && this.getBoundingBox().intersects(other.getBoundingBox());
    }
    
    /**
     * Gets this GameItem's bounding box.
     * @return A Rectangle2D representing this GameItem's bounding box.
     */
    public Rectangle2D getBoundingBox() {
    	return new Rectangle2D.Double(xCenter - xSize / 2, yCenter - ySize / 2, xSize, ySize);
    }
    
    /**
     * Gets the center of this GameItem.
     * @return A Point2D representing this GameItem's center
     */
    public Point2D getCenter() {
    	return new Point2D.Double(xCenter, yCenter);
    }
    
    /**
     * Moves the center of this GameItem to the specified location.
     * @param newPos : The new center of this GameItem.
     */
    public void moveTo(Point2D newPos) {
    	xCenter = newPos.getX();
    	yCenter = newPos.getY();
    }
    
    /**
     * Move the center of this GameItem to the specified location.
     * @param x : X pos
     * @param y : Y pos
     */
    public void moveTo(double x, double y) {
    	xCenter = x;
    	yCenter = y;
    }
    
    /**
     * Offsets the center of this GameItem by the specified amount.
     * @param x : X amount to move
     * @param y : Y amount to move
     */
    public void moveBy(double x, double y) {
    	xCenter += x;
    	yCenter += y;
    }
    
    /**
     * Draws this GameItem on the screen.
     * @param g : Graphics object from Game.gameFrame
     */
    public void draw(Graphics g) {
    	Rectangle2D bBox = getBoundingBox();
    	g.drawImage(image, (int) bBox.getX(), (int) bBox.getY(), 
    			(int) bBox.getWidth(), (int) bBox.getHeight(), Game.INSTANCE);
    }
    
    public Image getImage() {
    	return image;
    }
}
