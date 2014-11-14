package de.dakror.launcher.settings;

import java.io.File;
import java.util.Arrays;

public class CFG {
	public static final File DIR = new File(System.getProperty("user.home") + "/.dakror/Launcher");
	static long time;
	
	static {
		DIR.mkdirs();
	}
	
	public static void u() {
		if (time == 0) time = System.currentTimeMillis();
		else {
			p(System.currentTimeMillis() - time);
			time = 0;
		}
	}
	
	public static void p(Object... p) {
		if (p.length == 1) System.out.println(p[0]);
		else System.out.println(Arrays.toString(p));
	}
	
	public static void e(Object... p) {
		if (p.length == 1) System.err.println(p[0]);
		else System.err.println(Arrays.toString(p));
	}
}
