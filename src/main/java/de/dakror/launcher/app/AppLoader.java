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
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import de.dakror.gamesetup.util.Helper;
import de.dakror.launcher.DakrorLauncher;
import de.dakror.launcher.settings.CFG;

/**
 * @author Dakror
 */
public class AppLoader {
	public static ArrayList<App> apps = new ArrayList<>();
	
	public static void getApps() {
		try {
			apps.clear();
			if (DakrorLauncher.internet) {
				JSONArray a = new JSONArray(Helper.getURLContent(new URL("http://dakror.de/app/get")));
				for (int i = 0; i < a.length(); i++) {
					JSONObject o = a.getJSONObject(i);
					if (o.getInt("TYPE") == 0) {
						apps.add(new App(o.getInt("ID"), o.getInt("STATE"), o.getInt("DATE"), o.getString("NAME"), o.getString("IMAGE"), o.getString("DESCRIPTION"), o.getLong("VERSION")));
					}
				}
			} else {
				for (File f : new File(CFG.DIR, DakrorLauncher.getLastLogin()[1] + "/apps/").listFiles()) {
					apps.add(new App(0, 0, 0, f.getName(), f.getName().toLowerCase() + ".png", "", 1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
