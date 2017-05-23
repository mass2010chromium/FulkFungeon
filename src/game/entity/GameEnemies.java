package game.entity;

import game.Game;
import utils.MathUtils;


/**
 * Base class for enemies.
 * 
 * ALL IMPLEMENTING CLASSES:
 * Constructors must take EXACTLY 3 parameters: Double xcenter, Double ycenter, and a Random object.
 * 
 * @author jcpen
 *
 */
public abstract class GameEnemies extends GameLiving implements ICollidable
{
    protected Player player;

    protected int randMove = 0;
    
    protected double moveSpeed;
    protected double shootSpeed;

    public GameEnemies( double xCenter, double yCenter, double xSize, double ySize, 
    		double moveSpeed, double shootSpeed, double health, String imgPath )
    {
        super( xCenter, yCenter, xSize, ySize, health * Game.INSTANCE.getMap().getDifficulty(), imgPath );
        player = Game.INSTANCE.getPlayer();
        this.moveSpeed = moveSpeed;
        this.shootSpeed = shootSpeed;
    }


    // TMP for testing purpoises
    @Override
    public void setDead( boolean dead )
    {
        if ( dead && !isDead() )
        {
        	this.dropLoot();
        }
        super.setDead( dead );
    }
    
    protected abstract void dropLoot();
    
    protected void moveEigths(double angle, double speed) {
        int eigth = (int) ((angle + Math.PI / 8) / (Math.PI / 4));
        angle = eigth * Math.PI / 4;
        vX = speed * Math.cos(angle);
        vY = speed * Math.sin(angle);
    }
    
    protected void moveDirect(double angle, double speed) {
        vX = speed * Math.cos(angle);
        vY = speed * Math.sin(angle);
    }

    /**
     * Shoot a default bullet with some spread.
     * @param spread: spread, in radians.
     */
    protected Projectile shootSpread(double spread, double speed, double damage, String imageName)
    {

        double angle = MathUtils.angle( player.getCenter(), getCenter() );
        angle -= spread / 2;
        angle += Math.random() * spread;
        Projectile p = new Projectile( this,
                getCenter().getX(),
                getCenter().getY(),
                20.0,
                20.0,
                Math.cos( angle ) * speed,
                Math.sin( angle ) * speed,
                damage * Game.INSTANCE.getMap().getDifficulty(),
                imageName );
        Game.INSTANCE.spawnEntity( p );
        return p;
    }
    
    protected boolean startRandomMovement(int delayTime) {
        if ( Math.abs( vX ) <= 0.01 && Math.abs( vY ) <= 0.01 )
        {
            randMove = delayTime;
            moveEigths(Math.PI * 2 * Math.random(), moveSpeed);
            moveRandomly();
            return true;
        }
        return false;
    }

    protected boolean moveRandomly() {
        if ( Math.abs( vX ) <= 0.01 && Math.abs( vY ) <= 0.01 )
        {
        	randMove = 0;
        	return false;
        }
        if ( randMove > 0 )
        {
            randMove--;
            return true;
        }
        return false;
    }
}
