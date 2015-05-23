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
 

package de.dakror.launcher.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Dakror
 */
public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public JProgressBar progress;
	public JLabel info;
	
	public StatusPanel() {
		setSize(1200, 32);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		progress = new JProgressBar();
		progress.setStringPainted(true);
		progress.setValue(0);
		progress.setPreferredSize(new Dimension(500, 22));
		progress.setVisible(false);
		add(progress);
		
		info = new JLabel("");
		info.setFont(info.getFont().deriveFont(16f));
		info.setVisible(false);
		add(info);
	}
	
	public void setComponentsVisible(boolean b) {
		for (Component c : getComponents())
			c.setVisible(b);
	}
}
