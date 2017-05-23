package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import game.entity.Weapon;

public class WeaponButton extends JButton implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4970070136828343019L;
	
	private Weapon weapon;
	private String info1, info2, info3;
	
	public WeaponButton(Weapon wep) {
		updateInfo(wep);
		this.setBackground(Color.DARK_GRAY);
		this.addActionListener(this);
	}
	
	public void updateInfo(Weapon wep) {
		weapon = Factory.createWeapon(WeaponPane.SQUARE_SIZE * 0.5, WeaponPane.SQUARE_SIZE * 0.3, 
				wep);
		info1 = "DPH: " + GuiSideBar.NUMBER_FORMAT.format(weapon.getDamage());
		info2 = "Attack Speed: " + GuiSideBar.NUMBER_FORMAT.format(
				weapon.getCooldown() / 100.0) + "s";
		info3 = "DPS: " + GuiSideBar.NUMBER_FORMAT.format(
				weapon.getDamage() / weapon.getCooldown() * 100);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		g.setColor(Color.WHITE);
		weapon.draw(g);
		g.translate(WeaponPane.SQUARE_SIZE / 5, (int) (WeaponPane.SQUARE_SIZE * 0.7));
		g.drawString(weapon.getName(), 0, 0);
		g.drawString(info1, 0, 20);
		g.drawString(info2, 0, 40);
		g.drawString(info3, 0, 60);
		g.translate(WeaponPane.SQUARE_SIZE / 5, -(int) (WeaponPane.SQUARE_SIZE * 0.7));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Game.INSTANCE.getPlayer().setCurrentWeapon(weapon.getName());
	}
}
