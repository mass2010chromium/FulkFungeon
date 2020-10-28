package test;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import utils.MathUtils;

/**
 * Test the math things.
 * @author jpeng988
 *
 */
public class TestMathUtils {
	
	Line2D testLine;
	Point2D testPt;
	
	@Before
	public void init() {
		testLine = new Line2D.Double(0, 0, 1, 0);
		testPt = new Point2D.Double(1, 0);
	}
	
	@Test
	public void testRotation() {
		testLine = MathUtils.rotate(testLine, Math.PI / 2);
		assertEquals(0.5, testLine.getX1(), 0.0001);
		assertEquals(-0.5, testLine.getY1(), 0.0001);
		assertEquals(0.5, testLine.getX2(), 0.0001);
		assertEquals(0.5, testLine.getY2(), 0.0001);
		assertEquals(1.0, MathUtils.length(testLine), 0.0001);
		testLine = MathUtils.scale(testLine, 2);
		assertEquals(0.5, testLine.getX1(), 0.0001);
		assertEquals(-1.0, testLine.getY1(), 0.0001);
		assertEquals(0.5, testLine.getX2(), 0.0001);
		assertEquals(1.0, testLine.getY2(), 0.0001);
		assertEquals(2.0, MathUtils.length(testLine), 0.0001);
		Point2D midPt2 = MathUtils.midpoint(testLine.getP1(), testLine.getP2());
		assertEquals(0.5, midPt2.getX(), 0.0001);
		assertEquals(0, midPt2.getY(), 0.0001);
	}
	
	@Test
	public void testRest() {
		Point2D newPt = MathUtils.scale(testPt, new Point2D.Double(), 3);
		assertEquals(3, newPt.getX(), 0.0001);
		assertEquals(0, newPt.getY(), 0.0001);
		newPt = MathUtils.add(testPt, new Point2D.Double(0, 1));
		assertEquals(1, newPt.getX(), 0.0001);
		assertEquals(1, newPt.getY(), 0.0001);
		assertEquals(Math.PI / 4, MathUtils.angle(newPt, new Point2D.Double()), 0.0001);
	}
}
