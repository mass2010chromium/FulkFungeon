package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JPanel;

import game.entity.Weapon;

public class WeaponPane extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3452134821999308416L;
	
	public static final int SQUARE_SIZE = 300;
	
	private Map<String, WeaponButton> weapons;
	
	public WeaponPane() {
		this.setBackground(Color.GRAY);
		this.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
		weapons = new TreeMap<String, WeaponButton>();
	}
	
	public void paint(Graphics g) {
		this.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE * weapons.size()));
		this.revalidate();
		int i = 0;
		for (Entry<String, WeaponButton> entry : weapons.entrySet()) {
			entry.getValue().setBounds(10, i * SQUARE_SIZE + 10, SQUARE_SIZE - 20, SQUARE_SIZE - 20);
			i++;
		}
		super.paint(g);
	}
	
	public void updateWeapon(Weapon wep) {
		if (weapons.containsKey(wep.getName())) {
			weapons.get(wep.getName()).updateInfo(wep);
		}
		else {
			WeaponButton weapon = new WeaponButton(wep);
			weapons.put(wep.getName(), weapon);
			this.add(weapon);
		}
	}
	
	public void removeWeapon(Weapon wep) {
		if (weapons.containsKey(wep.getName())) {
			this.remove(weapons.get(wep.getName()));
			weapons.remove(wep.getName());
		}
	}
}
