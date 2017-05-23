package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import game.entity.Player;
import game.entity.Weapon;

/**
 * That thing on the right that shows your score and weapons.
 * @author jcpen
 *
 */
public class GuiSideBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1850395610493082958L;
	public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("0.0##");
	private WeaponPane weapons;
	
	public GuiSideBar(int x, int y, int w, int h) {
		this.setBackground(Color.WHITE);
		this.setBounds(x, y, w, h);
		this.setVisible(true);
		this.setLayout(null);
		weapons = new WeaponPane();
		JScrollPane inventory = new JScrollPane(weapons);
		inventory.setBounds(0, 300, w, h - 300);
		inventory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inventory.getVerticalScrollBar().setUnitIncrement(16);
		inventory.setVisible(true);
		this.add(inventory);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Player p = Game.INSTANCE.getPlayer();
		if (p == null) return;
		String score = "Score: " + NUMBER_FORMAT.format(Math.ceil(p.getScore()));
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		g.drawChars(score.toCharArray(), 0, score.length(), 20, 20);
		
		String health = "Health: " + NUMBER_FORMAT.format(p.getHealth()) + "/" + 
				NUMBER_FORMAT.format(p.getMaxHealth());
		g.drawChars(health.toCharArray(), 0, health.length(), 20, 60);
	}
	
	/**
	 * Display a new weapon.
	 * @param weapon : Weapon to display
	 */
	public void updateWeapon(Weapon weapon) {
		weapons.updateWeapon(weapon);
	}
	
	/**
	 * Removes a weapon from this gui.
	 * @param weapon : Weapon to remove
	 */
	public void removeWeapon(Weapon weapon) {
		weapons.removeWeapon(weapon);
	}
}
