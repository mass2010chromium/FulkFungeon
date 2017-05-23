package game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 *  Logs commands from the mouse and keyboard to move the Player
 *
 *  @author  smohidekar916
 *  @version Apr 26, 2017
 *  @author  Period: 2
 *  @author  Assignment: Dungeon
 *
 */

public class GameInputListener implements MouseListener, MouseMotionListener, KeyListener
{
	private Set<Integer> pressedKeys = new HashSet<Integer>();
	private boolean mouse1Pressed;
	private boolean mouse1ReleasedFlag;
	private boolean mouse2Pressed;
	private boolean mouse2ReleasedFlag;
	private Point2D mousePos = new Point2D.Double();
	
	public static final byte MOUSE1_PRESSED = 0b00000001;
	public static final byte MOUSE1_CLICKED = 0b00000010;
	public static final byte MOUSE2_PRESSED = 0b00000100;
	public static final byte MOUSE2_CLICKED = 0b00001000;
	
    @Override
    public void mouseDragged( MouseEvent e ) {
    	mousePos = e.getPoint();
    }

    @Override
    public void mouseMoved( MouseEvent e )
    {
    	mousePos = e.getPoint();
    }

    @Override
    public void mouseClicked( MouseEvent arg0 ) {}

    @Override
    public void mouseEntered( MouseEvent arg0 ) {}

    @Override
    public void mouseExited( MouseEvent arg0 ) {}

    @Override
    public void mousePressed( MouseEvent arg0 ) {
    	if (arg0.getButton() == MouseEvent.BUTTON1) {
    		mouse1Pressed = true;
    	}
    	if (arg0.getButton() == MouseEvent.BUTTON2) {
    		mouse2Pressed = true;
    	}
    }

    @Override
    public void mouseReleased( MouseEvent arg0 ) {
    	if (arg0.getButton() == MouseEvent.BUTTON1) {
        	mouse1Pressed = false;
        	mouse1ReleasedFlag = true;
    	}
    	if (arg0.getButton() == MouseEvent.BUTTON3) {
    		System.out.println("MOUSE 2");
        	mouse2Pressed = false;
        	mouse2ReleasedFlag = true;
    	}
    }
    
    /**
     * Gets the mouse state, pressed/not pressed and clicked/not clicked.
     * @return A combination of MOUSE_PRESSED and MOUSE_CLICKED
     */
    public byte getMouseState() {
    	byte b = 0;
    	if (mouse1Pressed) b |= MOUSE1_PRESSED;
    	if (mouse1ReleasedFlag) {
    		b |= MOUSE1_CLICKED;
        	mouse1ReleasedFlag = false;
    	}
    	if (mouse2Pressed) b |= MOUSE2_PRESSED;
    	if (mouse2ReleasedFlag) {
    		b |= MOUSE2_CLICKED;
        	mouse2ReleasedFlag = false;
    	}
    	return b;
    }
    
    /**
     * Gets the current mouse position.
     * @return A Point2D representing the mouse position.
     */
    public Point2D getMousePos() {
    	GameMap map = Game.INSTANCE.getMap();
    	if (map == null) return mousePos;
    	return map.applyOffset(mousePos);
    }

    @Override
    public void keyPressed( KeyEvent e )
    {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased( KeyEvent e )
    {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped( KeyEvent arg0 ) {}

    /**
     * Check if a certain key is down.
     * @param key : Key to check.
     * @return True if key is pressed, false otherwise.
     */
    public boolean keyDown(int key) {
    	return pressedKeys.contains(key);
    }
}
