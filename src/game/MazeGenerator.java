package game;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import utils.MathUtils;

/**
 * Generates a maze.
 * @author jpeng988
 *
 */
public class MazeGenerator {
	
	// Bitshift testing
//	public static void main(String[] args) {
//		System.out.println((byte)(0b11000000 << 1));
//		System.out.println((byte)(0b11000000 << 2) | (byte)(0b11000000 >> 6));
//		System.out.println((byte)(0b11000000 << 2));
//		System.out.println((byte)(0b00000011));
//	}
	
	private Random random;
	
	/**
	 * For Testing.
	 * @param rand
	 */
	public MazeGenerator(Random rand) {
		random = rand;
	}
	
	public MazeGenerator() {
		this(new Random());
	}
	
	public List<Line2D> generateMaze(int xSize, int ySize) {
		List<Line2D> walls = new ArrayList<Line2D>();
		
		Stack<Point> movement = new Stack<Point>();
		Point cur = new Point(random.nextInt(ySize), random.nextInt(xSize));
		
		byte[][] allWalls = new byte[ySize][xSize];
		for (int i = 0; i < allWalls.length; i++) {
			for (int j = 0; j < allWalls[i].length; j++) {
				allWalls[i][j] = 0b01010101; // 0N0E0S0W
			}
		}
		
		int visitCount = xSize * ySize;
		
		Set<Point> visited = new HashSet<Point>();
		visited.add(cur);
		
//		System.out.println(visitCount);
		do {
//			System.out.println("Current Point: " + cur);
			List<Point> neighbors = cur.neighbors();
			Iterator<Point> iter = neighbors.iterator();
			while (iter.hasNext()) {
				Point p = iter.next();
//				if (visited.contains(p)) {
//					System.out.println("Already Visited");
//				}
				if (visited.contains(p) || p.getRow() < 0 || p.getCol() < 0 || 
						p.getRow() >= ySize || p.getCol() >= xSize) {
					iter.remove();
				}
			}
			if (neighbors.isEmpty()) {
//				System.out.println("Backtrack");
				if (movement.isEmpty()) {
//					System.out.println("Done");
					break;
				}
				cur = movement.pop();
				continue;
			}
			Point p = neighbors.get(random.nextInt(neighbors.size()));
			
			allWalls[cur.getRow()][cur.getCol()] ^= cur.direction(p);
			allWalls[p.getRow()][p.getCol()] ^= rotate(cur.direction(p), 4);
			
//			System.out.println("remove wall between " + cur + ", " + p);
//			System.out.println(Integer.toBinaryString(allWalls[cur.getRow()][cur.getCol()]));
//			System.out.println(Integer.toBinaryString(allWalls[p.getRow()][p.getCol()]));
			
			movement.push(cur);
			visited.add(p);
			cur = p;
			visitCount--;
		} while (visitCount > 0);
		
//		System.out.println(visited.size());
		
//		for (Point p : visited) {
//			System.out.println(p);
//		}
		
		for (int i = 0; i < allWalls.length; i++) {
			for (int j = 0; j < allWalls[i].length; j++) {
				byte connections = allWalls[i][j];
				for (byte direction : DIRECTIONS) {
					if ((connections & direction) == direction) {
						allWalls[i][j] ^= direction;
						Point p = new Point(i, j).offset(direction);
						if (p.getRow() < 0 || p.getCol() < 0 || 
								p.getRow() >= ySize || p.getCol() >= xSize) {
							continue;
						}
						allWalls[p.getRow()][p.getCol()] ^= rotate(direction, 4);
//						System.out.println("adding wall between " + new Point(i, j) + ", " + p);
						walls.add(MathUtils.rotate(new Line2D.Double(new Point2D.Double(i, j), 
								new Point2D.Double(p.getRow(), p.getCol())), Math.PI / 2));
					}
				}
			}
		}
		
		return walls;
	}
	
	private byte rotate(byte num, int leftShift) {
		return (byte) ((byte)(num << leftShift) | (byte)(num >> 8 - leftShift));
	}
	
	public static final byte NORTH = 0b01000000;
	public static final byte EAST  = 0b00010000;
	public static final byte SOUTH = 0b00000100;
	public static final byte WEST  = 0b00000001;
	
	public static final byte[] DIRECTIONS = new byte[]{
			NORTH, EAST, SOUTH, WEST
	};
	
	/**
	 * A Point with integer coords.
	 * @author jpeng988
	 *
	 */
	private class Point {
		
		private int row, col;
		
		public Point(int r, int c) {
			row = r;
			col = c;
		}
		
		public int getRow() {
			return row;
		}
		
		public int getCol() {
			return col;
		}
		
		public Point north() {
			return new Point(row - 1, col);
		}
		
		public Point south() {
			return new Point(row + 1, col);
		}
		
		public Point east() {
			return new Point(row, col + 1);
		}
		
		public Point west() {
			return new Point(row, col - 1);
		}
		
		public List<Point> neighbors() {
			List<Point> neighbors = new LinkedList<Point>();
			neighbors.add(north());
			neighbors.add(east());
			neighbors.add(south());
			neighbors.add(west());
			return neighbors;
		}
		
		/**
		 * Offsets this point in the given direction, and returns the result.
		 * @param direction : Direction to move in
		 * @return
		 */
		public Point offset(byte direction) {
			Point ret = new Point(row, col);
			if ((direction & NORTH) == NORTH) ret = ret.north();
			if ((direction & EAST ) == EAST ) ret = ret.east();
			if ((direction & SOUTH) == SOUTH) ret = ret.south();
			if ((direction & WEST ) == WEST ) ret = ret.west();
			return ret;
		}
		
		/**
		 * Direction to other point. 
		 * 0N0E0S0W
		 * @param other : Other point
		 * @return 0N0E0S0W
		 */
		public byte direction(Point other) {
			byte ret = 0b00000000;
			if (other.row > row) {
				ret |= SOUTH;
			}
			else if (other.row < row) {
				ret |= NORTH;
			}
			if (other.col > col) {
				ret |= EAST;
			}
			else if (other.col < col) {
				ret |= WEST;
			}
			return ret;
		}
		
		@Override
		public String toString() {
			return "X: " + col + ", Y: " + row;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof Point) {
				Point p = (Point) o;
				return col == p.getCol() && row == p.getRow();
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return row + col;
		}
	}
}
