package game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import game.Game;

public abstract class GameBosses extends GameEnemies
{

	public GameBosses(double xCenter, double yCenter, double xSize, double ySize, double moveSpeed,
			double health, double shootSpeed, String imgPath) {
		super(xCenter, yCenter, xSize, ySize, moveSpeed, shootSpeed, health, imgPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawHealthBar(Graphics g) {
		Point2D offset = Game.INSTANCE.getMap().applyOffset(new Point2D.Double());
		g.translate((int) offset.getX(), (int) offset.getY());
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(0, 0, (int) (1000 * (getHealth() / maxHealth)), 
				20);
		g.setColor(c);
		g.translate(-(int) offset.getX(), -(int) offset.getY());
	}
}
