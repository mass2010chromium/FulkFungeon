package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.junit.Before;
import org.junit.Test;

import game.entity.GameItem;
import game.entity.ICollidable;

/**
 * Test GameItems.
 * TBH THIS IS COMMON SENSE MAN
 * @author jcpen
 */
public class TestGameItem {
	
	private TestItem item1;
	
	@Before
	public void init() {
		item1 = new TestItem(0, 0, 10, 5);
	}
	
	@Test
	public void testCollided() {
		TestItem item2 = new TestCollidableItem(1, 1, 10, 10);
		TestItem item3 = new TestCollidableItem(15, 15, 10, 10);
		assertTrue(item1.checkCollision(item2));
		assertFalse(item2.checkCollision(item1));
		assertFalse(item1.checkCollision(item3));
		assertFalse(item3.checkCollision(item1));
		assertFalse(item1.checkCollision(item1));
		assertFalse(item2.checkCollision(item2));
	}
	
	@Test
	public void testDimensions() {
		assertEquals(10, item1.getWidth(), 0.0001);
		assertEquals(5, item1.getHeight(), 0.0001);
		Rectangle2D bBox = item1.getBoundingBox();
		assertEquals(bBox.getWidth(), item1.getWidth(), 0.0001);
		assertEquals(bBox.getHeight(), item1.getHeight(), 0.0001);
		assertEquals(bBox.getCenterX(), item1.getCenter().getX(), 0.0001);
		assertEquals(bBox.getCenterY(), item1.getCenter().getY(), 0.0001);
		
		item1.moveTo(new Point2D.Double(10, 0));
		assertEquals(10, item1.getCenter().getX(), 0.0001);
		assertEquals(0, item1.getCenter().getY(), 0.0001);
		
		assertEquals(10, item1.getWidth(), 0.0001);
		assertEquals(5, item1.getHeight(), 0.0001);
		bBox = item1.getBoundingBox();
		assertEquals(bBox.getWidth(), item1.getWidth(), 0.0001);
		assertEquals(bBox.getHeight(), item1.getHeight(), 0.0001);
		assertEquals(bBox.getCenterX(), item1.getCenter().getX(), 0.0001);
		assertEquals(bBox.getCenterY(), item1.getCenter().getY(), 0.0001);
		
		item1.moveTo(0, 10);
		assertEquals(0, item1.getCenter().getX(), 0.0001);
		assertEquals(10, item1.getCenter().getY(), 0.0001);
		bBox = item1.getBoundingBox();
		assertEquals(bBox.getCenterX(), item1.getCenter().getX(), 0.0001);
		assertEquals(bBox.getCenterY(), item1.getCenter().getY(), 0.0001);
		
		item1.moveBy(10, 0);
		assertEquals(10, item1.getCenter().getX(), 0.0001);
		assertEquals(10, item1.getCenter().getY(), 0.0001);
		bBox = item1.getBoundingBox();
		assertEquals(bBox.getCenterX(), item1.getCenter().getX(), 0.0001);
		assertEquals(bBox.getCenterY(), item1.getCenter().getY(), 0.0001);
		
		item1.setWidth(20);
		assertEquals(20, item1.getWidth(), 0.0001);
		item1.setHeight(20);
		assertEquals(20, item1.getHeight(), 0.0001);
		bBox = item1.getBoundingBox();
		assertEquals(bBox.getWidth(), item1.getWidth(), 0.0001);
		assertEquals(bBox.getHeight(), item1.getHeight(), 0.0001);
	}
	
	@Test
	public void testDying() {
		assertFalse(item1.isDead());
		item1.setDead(true);
		assertTrue(item1.isDead());
		item1.setDead(false);
		assertFalse(item1.isDead());
	}
	
	private class TestCollidableItem extends TestItem implements ICollidable{

		public TestCollidableItem(double xCenter, double yCenter, double xSize, double ySize) {
			super(xCenter, yCenter, xSize, ySize);
		}
	}
	
	private class TestItem extends GameItem {

		public TestItem(double xCenter, double yCenter, double xSize, double ySize) {
			super(xCenter, yCenter, xSize, ySize, null);
		}

		@Override
		public void collideWith(GameItem other) {}
		
	}
}
