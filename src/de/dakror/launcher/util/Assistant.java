package de.dakror.launcher.util;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Dakror
 */
public class Assistant
{
	public static Area getClipArea(int x, int y, int width, int height)
	{
		int rad = 25;
		Area a = new Area(new RoundRectangle2D.Double(x, y, width - 1, height - 1, rad, rad));
		a.add(new Area(new Rectangle(x + width - rad - 1, y, rad, rad)));
		a.add(new Area(new Rectangle(x, y + height - rad - 1, rad, rad)));
		Polygon polygon = new Polygon();
		
		rad = height > 100 ? 50 : height / 3;
		polygon.addPoint(x + width - rad - 1, y + height - 1);
		polygon.addPoint(x + width - 1, y + height - rad - 1);
		polygon.addPoint(x + width - 1, y + height - 1);
		Area b = new Area(polygon);
		a.subtract(b);
		return a;
	}
}
