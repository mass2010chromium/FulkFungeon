package game.entity;

/**
 * A potion effect that is temporary.
 * @author jpeng988
 */
public abstract class GameEffects implements Comparable<GameEffects>{

	private int time;
	private GameLiving affected;
	
	public GameEffects(GameLiving target, int time) {
		this.time = time;
		affected = target;
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
	public abstract boolean updateInstance(GameEffects effect);
	
	public GameEffects copy(GameLiving target) {
		GameEffects t = this;
		return new GameEffects(target, time) {
			public boolean updateInstance(GameEffects effect) {
				return t.updateInstance(this);
			}
		};
	}
}
