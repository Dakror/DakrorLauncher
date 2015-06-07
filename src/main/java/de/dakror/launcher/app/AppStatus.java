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

/**
 * @author Dakror
 */
public enum AppStatus {
	OK("", "Up to date"),
	UPDATE("Update", "Update to the latest version"),
	NOT_INSTALLED("Installation", "Install the application"),
	DOWNLOADING("Downloading", "Downloading data"),
	
	;
	
	private String name;
	private String description;
	
	private AppStatus(String name, String desc) {
		this.name = name;
		description = desc;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
