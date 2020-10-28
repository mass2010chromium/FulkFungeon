package utils;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Cool math things.
 * @author jpeng988
 *
 */
public class MathUtils {
	
	/**
	 * Multiplies the length of this line by amnt, keeping its midpoint the same.
	 * @param line : Line to scale.
	 * @param amnt : Amount to scale by.
	 * @return A scaled line.
	 */
	public static Line2D scale(Line2D line, double amnt) { //TESTED
		Point2D p1 = line.getP1();
		Point2D mid = midpoint(line);
		double dx = p1.getX() - mid.getX();
		double dy = p1.getY() - mid.getY();
		double newX = dx * amnt;
		double newY = dy * amnt;
		return new Line2D.Double(new Point2D.Double(mid.getX() + newX, mid.getY() + newY), 
				new Point2D.Double(mid.getX() - newX, mid.getY() - newY));
	}
	
	/**
	 * Rotates a line about its midpoint by the specified number of radians.
	 * @param line : Line to rotate
	 * @param radians : Amount to rotate
	 * @return Rotated line
	 */
	public static Line2D rotate(Line2D line, double radians) { //TESTED
		Point2D p1 = line.getP1();
		Point2D mid = midpoint(line);
		Point2D p2 = rotate(p1, mid, radians);
		return new Line2D.Double(new Point2D.Double(p2.getX(), p2.getY()), 
				new Point2D.Double(2 * mid.getX() - p2.getX(), 2 * mid.getY() - p2.getY()));
	}
	
	/**
	 * Finds the length of a line.
	 * @param line : Line to find length of
	 * @return Length of the line.
	 */
	public static double length(Line2D line) { //TESTED
		return distance(line.getP1(), line.getP2());
	}
	
	/**
	 * Gets the midpoint of 2 points.
	 * @param p1 : First point
	 * @param p2 : Second point
	 * @return Midpoint
	 */
	public static Point2D midpoint(Point2D p1, Point2D p2) { //TESTED
		return new Point2D.Double((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
	}
	
	/**
	 * Gets the midpoint of a line.
	 * @param line : Line to find midpoint of.
	 * @return Midpoint
	 */
	public static Point2D midpoint(Line2D line) { //TESTED
		return midpoint(line.getP1(), line.getP2());
	}
	
	/**
	 * Rotates a point about another point.
	 * @param initial : Initial point
	 * @param pivot : Point to pivot about
	 * @param radians : Radians to rotate.
	 * @return A new point.
	 */
	public static Point2D rotate(Point2D initial, Point2D pivot, double radians) { //TESTED
		double dx = initial.getX() - pivot.getX();
		double dy = initial.getY() - pivot.getY();
		double r = Math.hypot(dx, dy);
		double curAngle = Math.atan2(dy, dx);
		double newAngle = curAngle + radians;
		double newX = r * Math.cos(newAngle);
		double newY = r * Math.sin(newAngle);
		return new Point2D.Double(pivot.getX() + newX, pivot.getY() + newY);
	}
	
	/**
	 * Scales the distance between two points, using one as the center.
	 * @param initial : Point to scale.
	 * @param center : Center point.
	 * @param scale : Amount to scale by.
	 * @return A new point.
	 */
	public static Point2D scale(Point2D initial, Point2D center, double scale) { //TESTED
		double dx = initial.getX() - center.getX();
		double dy = initial.getY() - center.getY();
		return new Point2D.Double(center.getX() + dx * scale, center.getY() + dy * scale);
	}
	
	/**
	 * Adds two points.
	 * @param p1
	 * @param p2
	 * @return A new point x1+x2, y1+y2
	 */
	public static Point2D add(Point2D p1, Point2D p2) { //TESTED
		return new Point2D.Double(p1.getX() + p2.getX(), p1.getY() + p2.getY());
	}
	
	/**
	 * Angle between target point and the horizontal.
	 * @param target : Target point
	 * @param center : Center for anglefinding
	 * @return Angle, radians, from 0 to 2pi.
	 */
	public static double angle(Point2D target, Point2D center) { //TESTED
		double dx = target.getX() - center.getX();
		double dy = target.getY() - center.getY();
		return (Math.atan2(dy, dx) + Math.PI * 2) % (Math.PI * 2);
	}
	
	/**
	 * Distance between 2 points. sqrt(x^2+y^2)
	 * @param p1
	 * @param p2
	 * @return Distance.
	 */
	public static double distance(Point2D p1, Point2D p2) { //TESTED
		return Math.hypot(p1.getX() - p2.getX(), p1.getY() - p2.getY());
	}
}
