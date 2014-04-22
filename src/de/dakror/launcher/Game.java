package de.dakror.launcher;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import de.dakror.gamesetup.GameFrame;

/**
 * @author Dakror
 */
public class Game extends GameFrame
{
	@Override
	public void initGame()
	{}
	
	@Override
	public void draw(Graphics2D g)
	{}
	
	@Override
	public BufferedImage loadImage(String p)
	{
		try
		{
			BufferedImage i = p.contains(":/") ? ImageIO.read(new File(p)) : ImageIO.read(Game.class.getResource((p.startsWith("/") ? "" : "/img/") + p));
			
			return i;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
