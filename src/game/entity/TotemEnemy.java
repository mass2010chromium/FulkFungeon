package game.entity;

public abstract class TotemEnemy extends GameEnemies{

	protected int shotCooldown;
	protected int maxCooldown;
	private double spread;
	private double shotDamage;
	private String projectileImage;
	
	public TotemEnemy(double xCenter, double yCenter, double xSize, double ySize, 
			double shootSpeed, double health, String imgPath, int maxCooldown, double spread,
			double shotDamage, String projectileImage) {
		super(xCenter, yCenter, xSize, ySize, 0, shootSpeed, health, imgPath);
		shotCooldown = 0;
		this.maxCooldown = maxCooldown;
		this.spread = spread;
		this.shotDamage = shotDamage;
		this.projectileImage = projectileImage;
	}

	@Override
	public void tick() {
		this.shotCooldown++;
		if (shotCooldown >= maxCooldown) {
			shotCooldown = 0;
			this.shootSpread(spread, shootSpeed, shotDamage, projectileImage);
		}
	}

}
