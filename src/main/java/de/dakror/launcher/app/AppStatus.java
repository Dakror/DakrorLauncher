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
