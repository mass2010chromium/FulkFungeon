package game.entity;

import java.awt.event.KeyEvent;

import game.Game;

public class Portal extends GameTickable{

	double initialScore;
	public Portal(double xCenter, double yCenter, double xSize, double ySize, String imgPath) {
		super(xCenter, yCenter, xSize, ySize, imgPath);
		initialScore = Game.INSTANCE.getPlayer().getScore();
	}

	@Override
	public void tick() {}
	
	@Override
	public void doTick() {}

	@Override
	public void collideWith(GameItem other) {
		if (other instanceof Player && Game.INSTANCE.getInput().keyDown(KeyEvent.VK_SPACE)) {
			Player p = Game.INSTANCE.getPlayer();
			double deltaScore = p.getScore() - initialScore;
			p.adjustHealth(10 + deltaScore / 10);
			((Player) other).adjustHealth(20);
			p.addScore(20 + deltaScore / 10);
			Game.INSTANCE.getMap().generateMap(Game.INSTANCE);
		}
		
	}

}
