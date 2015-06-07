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


package de.dakror.launcher.app;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import de.dakror.gamesetup.util.Helper;
import de.dakror.launcher.DakrorLauncher;
import de.dakror.launcher.settings.CFG;

/**
 * @author Dakror
 */
public class App {
	AppStatus status;
	String name;
	String desc;
	String bgFile;
	int id, state, date;
	long onlineVersion;
	
	public App(int id, int state, int date, String name, String bgFile, String desc, long onlineVersion) {
		this.id = id;
		this.state = state;
		this.date = date;
		this.name = name;
		this.bgFile = bgFile;
		this.desc = desc;
		this.onlineVersion = onlineVersion;
		
		updateStatus();
	}
	
	public void updateStatus() {
		File dir = new File(CFG.DIR, DakrorLauncher.userId + "/apps/" + name.replace(" ", "-"));
		File version = new File(dir, name.replace(" ", "-") + ".version");
		if (!dir.exists()) status = AppStatus.NOT_INSTALLED;
		else {
			try {
				long v = Long.parseLong(Helper.getFileContent(version).trim());
				if (v < onlineVersion) status = AppStatus.UPDATE;
				else status = AppStatus.OK;
			} catch (Exception e) {
				status = AppStatus.NOT_INSTALLED;
			}
		}
	}
	
	public void cacheBgFile() {
		try {
			File cache = new File(CFG.DIR, DakrorLauncher.userId + "/apps/" + name.replace(" ", "-"));
			cache.mkdirs();
			
			File f = new File(cache, bgFile);
			
			if (!f.exists() && DakrorLauncher.internet) Helper.copyInputStream(new URL("http://dakror.de/assets/img/app/" + bgFile).openStream(), new FileOutputStream(f));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AppStatus getStatus() {
		return status;
	}
	
	public App setStatus(AppStatus status) {
		this.status = status;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public String getBgFile() {
		return bgFile;
	}
	
	public int getId() {
		return id;
	}
	
	public int getState() {
		return state;
	}
	
	public int getDate() {
		return date;
	}
}
