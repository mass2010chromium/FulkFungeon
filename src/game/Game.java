package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import game.entity.GameItem;
import game.entity.GameTickable;
import game.entity.Player;

public class Game extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1173481325727586167L;

	public static Game INSTANCE;
	
	private List<GameItem> items;
	private List<GameItem> addItems;
	private GameInputListener input;
	
	private JPanel content;
	private GuiSideBar sidebar;
	
	private Player player;
	
	private GameMap map;
	
	private Image floorTile;
	
	private Game() {
		
		items = new LinkedList<GameItem>();
		addItems = new LinkedList<GameItem>();
		
//        second s = new second();
//        f.add(s);
        inTick = true;
        Timer t = new Timer(10, this);
        t.start();
        JFrame f = new JFrame();
        
        content = new JPanel();
        
		input = new GameInputListener();
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
        
		content.setLayout(null);
		content.add(this);
		this.setBounds(0, 0, 1000, 1000);
		
		sidebar = new GuiSideBar(1000, 0, 400, 1000);
		content.add(sidebar);
		
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        content.setPreferredSize( new Dimension(1400, 1000) );
        this.setBackground(Color.GRAY);
        f.setContentPane(content);
        f.setLayout(null);
        f.pack();
        f.setResizable(false); //TODO fix
        f.setVisible( true );
	}
	
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Adds an entity to the game.
	 * @param item
	 */
	public void spawnEntity(GameItem item) {
		addItems.add(item);
	}
	
	/**
	 * Initialize game.
	 */
	private void initGame() {
		player = new Player(100, 100, 50, 100, 100, "mrFulk_walk.png");
		items.add(player);
		
		map = new GameMap(1000, 1000, player);
		map.generateMap(this);
		floorTile = getImage("floor_tile.png");
		inTick = false;
	}
	
    public static void main(String args[])
    {
    	INSTANCE = new Game();
    	INSTANCE.initGame();
    }
    
    public void resetMap() {
    	this.items.clear();
    }
    
    boolean repaint = true;
    boolean inTick = false;
    
    /**
     * Game loop. Every 5 milliseconds.
     */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (inTick) {
//			System.out.println("Skipping tick! " + arg0.getWhen());
			return;
		}
		inTick = true;
		Iterator<GameItem> iter = items.iterator();
		while (iter.hasNext()) {
			GameItem update = iter.next();
			if (update instanceof GameTickable) {
				((GameTickable) update).doTick();
				for (GameItem item : items) {
					if (update.checkCollision(item)) {
						update.collideWith(item);
					}
				}
			}
			if (update.isDead()) {
				iter.remove();
			}
		}
		for (GameItem item : items) {
			if (item instanceof GameTickable) ((GameTickable) item).postTick();
		}
		items.addAll(addItems);
		addItems.clear();
		repaint = !repaint;
		this.requestFocus();
		if (repaint) content.repaint();
		inTick = false;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (inTick) return;
		if (map != null) map.updatePosition(g);
		for (int r = 0; r < map.height() * 4; r++) {
			for (int c = 0; c < map.width() * 4; c++) {
				g.drawImage(floorTile, c * GameMap.ROOM_SIZE / 4, r * GameMap.ROOM_SIZE / 4, 
						GameMap.ROOM_SIZE / 4, GameMap.ROOM_SIZE / 4, this);
			}
		}
		Iterator<GameItem> iter = items.iterator();
		while (iter.hasNext()) {
			iter.next().draw(g);
		}
	}
	
	public GameInputListener getInput() {
		return this.input;
	}
	
	public GameMap getMap() {
		return map;
	}
	
	public List<GameItem> getEntities() {
		return this.items;
	}
    
	/**
	 * Gets an image from src/resources/path
	 * @param path : Path of the image.
	 * @return An image at the path or null if an ugly exception was thrown.
	 */
	public static Image getImage(String path) {
        path = "/" + path;
//        System.out.println(path);
        try {
	        return new ImageIcon(INSTANCE.getClass().getResource(
	                path)).getImage();
        } catch (Exception e) {
        	return null;
        }
	}
	
	public GuiSideBar getSideBar() {
		return sidebar;
	}
}
