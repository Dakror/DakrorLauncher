package de.dakror.launcher.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.border.AbstractBorder;

import de.dakror.launcher.util.Assistant;

public class AreaBorder extends AbstractBorder {
	private static final long serialVersionUID = 1L;
	Color color;
	
	public AreaBorder(Color c) {
		color = c;
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.draw(Assistant.getClipArea(x, y, width, height));
	}
}
