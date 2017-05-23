package test;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Line2D;

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
	
	@Before
	public void init() {
		testLine = new Line2D.Double(0, 0, 1, 0);
	}
	
	@Test
	public void testRotation() {
		testLine = MathUtils.rotate(testLine, Math.PI / 2);
		assertEquals(0.5, testLine.getX1(), 0.0001);
		assertEquals(-0.5, testLine.getY1(), 0.0001);
		assertEquals(0.5, testLine.getX2(), 0.0001);
		assertEquals(0.5, testLine.getY2(), 0.0001);
		testLine = MathUtils.scale(testLine, 2);
		assertEquals(0.5, testLine.getX1(), 0.0001);
		assertEquals(-1.0, testLine.getY1(), 0.0001);
		assertEquals(0.5, testLine.getX2(), 0.0001);
		assertEquals(1.0, testLine.getY2(), 0.0001);
	}
}
