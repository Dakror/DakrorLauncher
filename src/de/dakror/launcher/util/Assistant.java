package de.dakror.launcher.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.net.URI;

import javax.swing.JComponent;

import de.dakror.launcher.DakrorLauncher;

/**
 * @author Dakror
 */
public class Assistant
{
	public static Area getClipArea(int x, int y, int width, int height)
	{
		int rad = 25;
		rad = height == 400 || height == 401 || height == 399 ? 50 : rad; // AppPanel
		Area a = new Area(new RoundRectangle2D.Double(x, y, width - 1, height - 1, rad, rad));
		a.add(new Area(new Rectangle(x + width - rad - 1, y, rad, rad)));
		a.add(new Area(new Rectangle(x, y + height - rad - 1, rad, rad)));
		Polygon polygon = new Polygon();
		
		rad = height > 100 ? 50 : height / 3;
		rad = height == 400 || height == 401 || height == 399 ? 100 : rad; // AppPanel
		rad = height == 720 ? 34 : rad;
		polygon.addPoint(x + width - rad - 1, y + height - 1);
		polygon.addPoint(x + width - 1, y + height - rad - 1);
		polygon.addPoint(x + width - 1, y + height - 1);
		Area b = new Area(polygon);
		a.subtract(b);
		return a;
	}
	
	public static void addLinkBahaviour(final JComponent c, final String link)
	{
		final Color foreGround = c.getForeground();
		c.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				c.setForeground(Color.decode("#8888ff"));
				c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				DakrorLauncher.currentLauncher.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				c.setForeground(foreGround);
				c.setCursor(Cursor.getDefaultCursor());
				DakrorLauncher.currentLauncher.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI(link));
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
	}
}
