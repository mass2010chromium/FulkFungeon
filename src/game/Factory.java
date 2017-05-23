package game;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import game.entity.AnimatedWeapon;
import game.entity.Weapon;

/**
 * For sketchily creating instances of enemies from template classes. Maybe transfer this method to weapon
 * or projectile creation.
 * @author jcpen
 *
 */
public class Factory {
	
	/**
	 * Creates a new instance of game enemy.
	 * @param <T>
	 * @param clazz : Class to instantiate
	 * @param args : Constructor arguments
	 * @return A new enemy! Hopefully. If nothing goes wrong.
	 */
	public static <T> T instantiate(Class<? extends T> clazz, Object... args) {
		Class<?>[] params = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) params[i] = args[i].getClass();
		try {
			return clazz.getConstructor(params).newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("Failed to instantiate " + clazz.getName() + ": args " + 
				Arrays.toString(args));
			e.printStackTrace();
			return null;
		}
	}
	
	public static Weapon createWeapon(double x, double y, Weapon base) {
		if (base instanceof AnimatedWeapon) {
			return new AnimatedWeapon(x, y, (AnimatedWeapon) base);
		}
		else {
			return new Weapon(x, y, base);
		}
	}
}
