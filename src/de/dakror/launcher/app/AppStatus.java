package de.dakror.launcher.app;

/**
 * @author Dakror
 */
public enum AppStatus
{
	OK,
	UPDATE,
	MISSING,
	NOT_INSTALLED
	
	;
	
	private String description;
	
	private AppStatus()
	{}
}
