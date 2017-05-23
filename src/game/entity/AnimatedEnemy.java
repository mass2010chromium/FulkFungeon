package game.entity;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import game.Game;

public abstract class AnimatedEnemy extends GameEnemies{

	protected int imageX, imageY, frames, tickRate, curFrame;
	
	public AnimatedEnemy(double xCenter, double yCenter, double xSize, double ySize, 
			double moveSpeed, double shootSpeed, double health, String imgPath, 
			int imageX, int imageY, int frames, int tickRate) {
		super(xCenter, yCenter, xSize, ySize, moveSpeed, shootSpeed, health, imgPath);
		this.imageX = imageX;
		this.imageY = imageY;
		this.frames = frames;
		this.tickRate = tickRate;
		this.curFrame = 0;
	}
	
	@Override
	public void doTick() {
		super.doTick();
    	curFrame = (curFrame + 1) % (frames * tickRate);
	}

	@Override
    public void draw(Graphics g) {
    	Rectangle2D bBox = getBoundingBox();
    	g.drawImage(getImage(), (int) bBox.getX(), (int) bBox.getY(), 
    			(int) (bBox.getX() + bBox.getWidth()), (int) (bBox.getY() + bBox.getHeight()), 
    			imageX * (curFrame / tickRate), 0, imageX * (curFrame / tickRate + 1), imageY, Game.INSTANCE);
    	drawHealthBar(g);
    }
    
}
