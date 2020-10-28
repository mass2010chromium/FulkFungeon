package game;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	private static StartMenu menu;
	private static DeathMenu death;
	
	private List<GameItem> items;
	private List<GameItem> addItems;
	private GameInputListener input;
	
	private static JPanel content;
	private static JFrame frame;
	private GuiSideBar sidebar;
	
	private Player player;
	
	private GameMap map;
	
	private Image floorTile;
	
	private Timer timer;
	
	private Game() {
		
		items = new LinkedList<GameItem>();
		addItems = new LinkedList<GameItem>();
		
//        second s = new second();
//        f.add(s);
        inTick = true;
        timer = new Timer(10, this);
        
		input = new GameInputListener();
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
        
		this.setBounds(0, 0, 1000, 1000);
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
	
	private int skipGameTicks;
	
	public void setSkipTicks(int ticks) {
		skipGameTicks = ticks;
	}
	
	/**
	 * Initialize game.
	 */
	public void initGame() {
		
		content.setComponentZOrder(this, 0);
        content.setPreferredSize( new Dimension(1400, 1000) );
        frame.pack();
        
		player = new Player(100, 100, 50, 100, 100, "mrFulk_walk.png");
		items.add(player);
		
		map = new GameMap(1000, 1000, player);
		map.generateMap(this);
		inTick = false;
		skipGameTicks = 0;
		timer.start();
	}
	
    public static void main(String args[])
    {
    	
        frame = new JFrame();
        
        content = new JPanel();
		content.setLayout(null);
        
    	INSTANCE = new Game();
    	menu = new StartMenu();
    	death = new DeathMenu();

		INSTANCE.sidebar = new GuiSideBar(1000, 0, 400, 1000);
		content.add(INSTANCE.sidebar);
		INSTANCE.floorTile = getImage("grass_tile.png");
		
		content.add(INSTANCE);
    	content.add(menu);
    	content.add(death);
    	
    	content.repaint();
    	
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane(content);
        frame.setLayout(null);
        frame.setResizable(false); //TODO fix
        frame.setVisible( true );
        
        INSTANCE.startMenu();
        
//    	INSTANCE.initGame();
    }
    
    public void deathMenu() {
//    	timer.stop();
    	death.setVisible(true);
    	content.setComponentZOrder(death, 0);
    }
    
    public void startMenu() {
		timer.stop();
		menu.setVisible(true);
    	content.setComponentZOrder(menu, 0);
        content.setPreferredSize( new Dimension(1000, 1000) );
        frame.pack();
    }
    
    public void resetMap() {
    	this.items.clear();
    }
    
    byte repaint = 0;
    boolean inTick = false;
    
    private double lastTick = System.nanoTime();
    
    /**
     * Game loop. Every 10 milliseconds.
     */
	@Override
	public void actionPerformed(ActionEvent arg0) {
//		System.out.println(System.nanoTime() - lastTick);
//		lastTick = System.nanoTime();
		if (inTick) {
//			System.out.println("Skipping tick! " + arg0.getWhen());
			return;
		}
		if (skipGameTicks > 0) {
			skipGameTicks--;
			return;
		}
		inTick = true;
		Iterator<GameItem> iter = items.iterator();
		this.requestFocus();
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
		repaint++;
		if (repaint >= 2) {
			repaint = 0;
			content.repaint();
		}
		inTick = false;
	}
	
	@Override
	public void paint(Graphics g) {
//		long time = System.nanoTime();
		if (inTick) return;
//		System.out.println(".");
		super.paint(g);
		if (map != null) {
			map.updatePosition(g);
			((Graphics2D) g).scale(map.getScaleFactor(), map.getScaleFactor());
		}
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
//		System.out.println((System.nanoTime() - time) / 1000000000.0);
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
        path = "/resources/" + path;
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
