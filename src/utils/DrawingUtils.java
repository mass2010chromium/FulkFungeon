package utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.image.ImageObserver;

/**
 * For drawing things, like rotated images.
 * @author jcpen
 *
 */
public class DrawingUtils {
	
	/**
	 * Draw a rotated image.
	 * @param g : Graphics
	 * @param image : Image to draw
	 * @param dX : Destination x center
	 * @param dY : Destination y center
	 * @param scale : Image scaling
	 * @param sx1 : Source x start
	 * @param sy1 : Source y start
	 * @param sx2 : Source x end
	 * @param sy2 : Source y end
	 * @param angle : Rotation angle
	 * @param observer : ImageObserver for notification
	 */
	public static void drawRotatedImage(Graphics g, Image image, double dX, double dY, double scale, 
			int sx1, int sy1, int sx2, int sy2, double angle, ImageObserver observer) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(dX, dY);
		g2d.scale(scale, scale);
		g2d.rotate(angle);
		double halfWidth = ((sx2 - sx1) * scale / 2);
		double halfHeight = ((sy2 - sy1) * scale / 2);
		g2d.drawImage(image, (int) (-halfWidth), (int) (-halfHeight), 
				(int) (halfWidth), (int) (halfHeight), 
				sx1, sy1, sx2, sy2, observer);
		g2d.rotate(-angle);
		g2d.scale(1/scale, 1/scale);
		g2d.translate(-dX, -dY);
	}
	
	public static void drawLine(Graphics g, Line2D line) {
		g.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
	}
}
