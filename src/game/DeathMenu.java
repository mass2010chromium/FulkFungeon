package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DeathMenu extends JPanel{

	
	private static final int WIDTH = 1400;
	private static final int HEIGHT = 1000;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8319693687472612214L;
	
	public DeathMenu() {
		this.setOpaque(false);
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.setVisible(false);
        this.setLayout(null);
        JButton menu = new JButton("Menu");
        menu.setFont(new Font(Font.MONOSPACED, Font.BOLD, 100));
        menu.setForeground(Color.WHITE);
        menu.setBounds(100, 600, 400, 100);
        menu.setOpaque(false);
        menu.setBackground(Color.GRAY);
        menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.INSTANCE.startMenu();
			}
        	
        });
        this.add(menu);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 100));
		g.setColor(Color.WHITE);
		g.drawString("What the fulk!", 100, 200);
		g.drawString("You died.", 100, 400);
	}
}
