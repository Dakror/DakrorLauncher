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

import java.awt.FlowLayout;

import javax.swing.JPanel;

/**
 * @author Dakror
 */
public class AppListPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public AppListPanel() {
		setSize(1200, 900);
		setLayout(new FlowLayout(FlowLayout.CENTER, 15, 50));
		setOpaque(false);
	}
}
