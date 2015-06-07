/*******************************************************************************
 * Copyright 2015 Maximilian Stark | Dakror <mail@dakror.de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.dakror.launcher;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import de.dakror.gamesetup.GameFrame;

/**
 * @author Dakror
 */
public class Game extends GameFrame {
	@Override
	public void initGame() {}
	
	@Override
	public void draw(Graphics2D g) {}
	
	@Override
	public BufferedImage loadImage(String p) {
		try {
			BufferedImage i = p.contains(":/") ? ImageIO.read(new File(p)) : ImageIO.read(Game.class.getResource((p.startsWith("/") ? "" : "/img/") + p));
			
			return i;
		} catch (Exception e) {
			return null;
		}
	}
}
