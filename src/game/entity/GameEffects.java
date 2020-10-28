package game.entity;

/**
 * A potion effect that is temporary.
 * TODO: Make this
 * @author jpeng988
 */
public abstract class GameEffects implements Comparable<GameEffects>{

	private int time;
	private GameLiving affected;
	private GameEffects copy;
	
	/**
	 * Creates a new GameEffect.
	 * @param target : Target entity
	 * @param time : How long this effect lasts
	 * @param copy : template effect, or null
	 */
	public GameEffects(GameLiving target, int time, GameEffects copy) {
		this.time = time;
		affected = target;
		this.copy = copy;
	}
	
	public int getTimeRemaining() {
		return this.time;
	}
	
	public GameLiving getTarget() {
		return affected;
	}
	
	@Override
	public int compareTo(GameEffects o) {
		return getTimeRemaining() - o.getTimeRemaining();
	}
	
	/**
	 * Update this game effect. Return true if it is expired.
	 * @return True or false
	 */
	public boolean updateInstance(GameEffects effect) {
		if (copy != null) {
			return copy.updateInstance(this);
		}
		return true;
	}
}
