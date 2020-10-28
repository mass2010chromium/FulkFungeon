package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The start menu.
 * @author jcpen
 *
 */
public class StartMenu extends JPanel
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = -5972172128436782740L;
	
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	
	private Image title;
	
	/**
	 * Creates a new start menu JPanel.
	 */
	public StartMenu()
    {
		StartMenu m = this;
        JButton start = new JButton("");
        JButton help = new JButton("");
        start.setBackground(Color.GRAY);
        help.setBackground(Color.GRAY);
        start.setBounds((WIDTH - 720) / 2, 400, 720, 100);
        help.setBounds((WIDTH - 720) / 2, 600, 720, 100);
        try {
	        start.setIcon(new ImageIcon(Game.getImage("start.png")));
	        help.setIcon(new ImageIcon(Game.getImage("help.png")));
	        title = Game.getImage("game_title.png");
        } catch (Exception e) {
        	System.err.println("Game not loaded");
        	e.printStackTrace();
        }
        start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println(" b");
				m.setVisible(false);
				Game.INSTANCE.initGame();
			}
        	
        });
        help.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println(" b");
				try {
					java.awt.Desktop.getDesktop().browse(new java.net.URI("https://docs.google.com/document/"
							+ "d/1Cy9XNYstVAzqGtDc132f1yoVjeDyk2IgESI7b-QQmTU/edit?usp=sharing"));
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	
        });
        this.setLayout(null);
        this.add(start);
        this.add(help);
//        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBounds(0, 0, WIDTH, HEIGHT);
        
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.requestFocus();
    }
	
//	public static void main(String[] args) {
//		JFrame f = new JFrame();
//		f.add(new StartMenu());
//		f.pack();
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setVisible(true);
//		f.repaint();
//	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(title, (WIDTH - 720) / 2, 100, 720, 140, this);
	}
}
