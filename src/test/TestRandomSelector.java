package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.Items;
import utils.WeightedRandomSelector;

/**
 * Testing WeightedRandomSelector with weapons.
 * @author jcpen
 *
 */
public class TestRandomSelector {

	WeightedRandomSelector<String, String> randSel;
	
	@Before
	public void init() {
		randSel = new WeightedRandomSelector<String, String>();
	}
	
	@Test
	public void testPutGet() {
		randSel.add(1, "a", "b");
		randSel.add(1, "c", "d");
		assertEquals("b", randSel.get("a"));
		assertEquals("d", randSel.get("c"));
	}
	
	@Test
	public void testRandomItems() {
		HashMap<String, Integer> hitCounts = new HashMap<String, Integer>();
		hitCounts.put("a", 0);
		hitCounts.put("b", 0);
		hitCounts.put("c", 0);
		hitCounts.put("d", 0);
		hitCounts.put("e", 0);
		hitCounts.put("f", 0);
		randSel.add(1, "a", "b");
		randSel.add(2, "c", "d");
		randSel.add(3, "e", "f");
		for (int i = 0; i < 60000; i++) {
			String hit = randSel.randomItem();
			hitCounts.put(hit, hitCounts.get(hit) + 1);
		}
		assertEquals(0, hitCounts.get("a").intValue());
		assertEquals(0, hitCounts.get("c").intValue());
		assertEquals(0, hitCounts.get("e").intValue());
		assertEquals(10000, hitCounts.get("b").intValue(), 1000);
		assertEquals(20000, hitCounts.get("d").intValue(), 2000);
		assertEquals(30000, hitCounts.get("f").intValue(), 3000);
	}
	
	/**
	 * Visually test random generation.
	 * @param args
	 */
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
