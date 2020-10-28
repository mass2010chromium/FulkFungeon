package test;

import static org.junit.Assert.*;

import java.awt.BasicStroke;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import game.MazeGenerator;

/**
 * Testing maze generation.
 * @author jpeng988
 *
 */
public class TestMazeGenerator {
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void testMazeGenerator() {
		MazeGenerator gen = new MazeGenerator();
		assertNotNull(gen);
		gen = new MazeGenerator(new Random(1));
		assertNotNull(gen);
		List<Line2D> lst = gen.generateMaze(4, 4);
		assertNotNull(lst);
	}
	
	/**
	 * Visually test maze gen.
	 * @param args : Arguments
	 */
	public static void main(String[] args) {
		MazeGenerator gen = new MazeGenerator(new Random(1));
		List<Line2D> lst = gen.generateMaze(4, 4);
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
					g.drawLine((int) (line.getX1() * 200) + 150, (int) (line.getY1() * 200) + 150, 
							(int) (line.getX2() * 200) + 150, (int) (line.getY2() * 200) + 150);
				}
				g.drawLine(50, 50, 50, 50+200*4);
				g.drawLine(50, 50, 50+200*4, 50);
				g.drawLine(50+200*4, 50, 50+200*4, 50+200*4);
				g.drawLine(50, 50+200*4, 50+200*4, 50+200*4);
			}
		});
		
		frame.repaint();
	}
}
