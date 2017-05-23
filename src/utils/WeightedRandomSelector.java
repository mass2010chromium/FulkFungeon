package utils;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * A weighted random item selector that also has mapping capabilities.
 * @author jcpen
 *
 * @param <K> : Identifier for items in this set.
 * @param <V> : Item type of this set.
 */
public class WeightedRandomSelector<K, V> {
	
	private double weightTotal;
	
	private TreeMap<K, Item> items;
	
	/**
	 * Creates a new WeightedRandomSelector.
	 */
	public WeightedRandomSelector() {
		weightTotal = 0;
		items = new TreeMap<K, Item>();
	}
	
	/**
	 * Adds a new item to this selector's pool.
	 * @param weight : weight to associate with this item.
	 * @param name : Name of the item.
	 * @param item : The item to add.
	 */
	public void add(double weight, K name, V item) {
		items.put(name, new Item(weight, item));
		weightTotal += weight;
	}
	
	/**
	 * Gets a random item, respects weights.
	 * @return A random item
	 */
	public V randomItem() {
		double item = Math.random() * weightTotal;
		List<Item> tmp = new LinkedList<Item>(items.values());
		V ret = tmp.get(0).getItem();
		item -= tmp.remove(0).getWeight();
		while (item > 0) {
			ret = tmp.get(0).getItem();
			item -= tmp.remove(0).getWeight();
		}
		return ret;
	}
	
	/**
	 * Gets an item by its label.
	 * @param label
	 * @return The item
	 */
	public V get(K label) {
		return items.get(label).getItem();
	}
	
	private class Item implements Comparable<Item> {

		private double weight;
		private V item;
		
		public Item(double weight, V item) {
			this.weight = weight;
			this.item = item;
		}
		
		@Override
		public int compareTo(Item arg0) {
			return (int) Math.ceil(weight - arg0.weight);
		}
		
		public double getWeight() {
			return weight;
		}
		
		public V getItem() {
			return item;
		}
	}
}
