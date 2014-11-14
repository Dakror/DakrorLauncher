package de.dakror.launcher.settings;

public class UIStateChange {
	public static enum UIState {
		LOGIN,
		MAIN
	}
	
	public UIState from, to;
	
	public UIStateChange(UIState from, UIState to) {
		this.from = from;
		this.to = to;
	}
	
	@Override
	public int hashCode() {
		return from.ordinal() * to.ordinal();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UIStateChange) return from == ((UIStateChange) obj).from && to == ((UIStateChange) obj).to;
		return false;
	}
}
