package test;

import java.awt.BasicStroke;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import game.MazeGenerator;

/**
 * Testing maze generation.
 * @author jpeng988
 *
 */
public class TestMazeGenerator {
	public static void main(String[] args) {
		MazeGenerator gen = new MazeGenerator(new Random(1));
		List<Line2D> lst = gen.generateMaze(36, 36);
		JFrame frame = new JFrame();
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setContentPane(new Container() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8341440877168166298L;
			
			@Override
			public void paint(Graphics g) {
				((Graphics2D) g).setStroke(new BasicStroke(5));
				for (Line2D line : lst) {
//					if (line.getX1() < 0 || line.getX2() < 0) System.out.println(":");
					g.drawLine((int) (line.getX1() * 25) + 35, (int) (line.getY1() * 25) + 35, 
							(int) (line.getX2() * 25) + 35, (int) (line.getY2() * 25) + 35);
				}
			}
		});
		
		frame.repaint();
	}
}
