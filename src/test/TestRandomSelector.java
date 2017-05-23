package test;

import java.util.HashMap;
import java.util.Map.Entry;

import game.Game;
import game.Items;

/**
 * Testing WeightedRandomSelector with weapons.
 * @author jcpen
 *
 */
public class TestRandomSelector {

	public static void main(String[] args) {
		
		Game.main(null);
		
		HashMap<String, Integer> hitCounts = new HashMap<String, Integer>();
		hitCounts.put("Ragnell", 0);
		hitCounts.put("Hilt", 0);
		hitCounts.put("Light Pole", 0);
		hitCounts.put("Facebook", 0);
		hitCounts.put("Catherine", 0);
		hitCounts.put("Cross Bow", 0);
		hitCounts.put("Mouse", 0);
		
		for (int i = 0; i < 10000; i++) {
			String weapon = Items.randomWeapon().getName();
			hitCounts.put(weapon, hitCounts.get(weapon) + 1);
		}
		
		for (Entry<String, Integer> e : hitCounts.entrySet()) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
	}
	
}
