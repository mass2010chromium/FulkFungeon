package game.bosses;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import game.Game;
import game.entity.Player;
import game.entity.enemies.BasicEnemy;

/**
 * Testing purposes only.
 * @author jcpen
 *
 */
public class TMPBossFight extends BossFight{

	public TMPBossFight() {
		super(1, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate(Player player, Game game) {
		player.moveTo(0, 0);
		game.spawnEntity(player);
		
		game.spawnEntity(new BasicEnemy(300.0, 300.0, new Random()) {
			@Override
			public void draw(Graphics g) {
				super.draw(g);
				g.setColor(Color.RED);
				g.drawLine((int) getCenter().getX(), (int) getCenter().getY(), 
						(int) player.getCenter().getX(), (int) player.getCenter().getY());
			}
		});
	}

	@Override
	public void cleanUp(Game game) {
		
	}

	@Override
	public void tick() {
		
	}

}
