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

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import de.dakror.gamesetup.util.Helper;
import de.dakror.launcher.app.App;
import de.dakror.launcher.app.AppStatus;
import de.dakror.launcher.panel.AppPanel;
import de.dakror.launcher.settings.CFG;

/**
 * @author Dakror
 */
public class DownloadManager extends Thread {
	public static class Download {
		public URL url;
		public File dest;
		public long size;
		public App app;
		public AppStatus status;
		
		public Download(App app) {
			try {
				this.app = app;
				status = AppStatus.valueOf(app.getStatus().name());
				String escName = app.getName().replace(" ", "-");
				url = new URL("http://dakror.de/bin/" + escName + ".jar");
				dest = new File(CFG.DIR, DakrorLauncher.userId + "/apps/" + escName + "/" + escName + ".jar");
				URLConnection con = url.openConnection();
				size = con.getContentLengthLong();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void downloadVersionFile() throws Exception {
			String escName = app.getName().replace(" ", "-");
			Helper.copyInputStream(new URL("http://dakror.de/bin/" + escName + ".version").openStream(), new FileOutputStream(new File(CFG.DIR, DakrorLauncher.userId + "/apps/"
					+ escName + "/" + escName + ".version")));
		}
	}
	
	public static DownloadManager manager;
	
	private ArrayList<Download> queue;
	boolean cancel;
	long downloaded;
	
	public DownloadManager() {
		manager = this;
		queue = new ArrayList<Download>();
		setName("Download Thread");
		
		start();
	}
	
	@Override
	public void run() {
		while (true) {
			if (queue.size() > 0) {
				try {
					DakrorLauncher.currentLauncher.statusPanel.setComponentsVisible(true);
					downloaded = 0;
					Download d = queue.get(0);
					
					d.downloadVersionFile();
					
					DakrorLauncher.currentLauncher.statusPanel.progress.setValue(0);
					updateTitle(d);
					
					InputStream in = d.url.openStream();
					OutputStream out = new FileOutputStream(d.dest);
					byte[] buffer = new byte[1024];
					int len = in.read(buffer);
					while (len >= 0) {
						updateTitle(d);
						if (cancel) {
							cancel = false;
							break;
						}
						
						downloaded += len;
						
						DakrorLauncher.currentLauncher.statusPanel.progress.setValue(getProgress());
						
						out.write(buffer, 0, len);
						len = in.read(buffer);
					}
					in.close();
					out.close();
					
					for (Component c : DakrorLauncher.currentLauncher.alp.getComponents())
						if (c instanceof AppPanel && ((AppPanel) c).app.getId() == d.app.getId()) ((AppPanel) c).updateStatus();
					
					downloadFinished();
					
					if (queue.size() == 0) DakrorLauncher.currentLauncher.statusPanel.setComponentsVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateTitle(Download d) {
		DakrorLauncher.currentLauncher.statusPanel.info.setText(d.status.getName() + ": " + d.app.getName() + " (" + Helper.formatBinarySize(d.size, 2) + ")"
				+ (queue.size() > 1 ? " and " + (queue.size() - 1) + " more." : ""));
	}
	
	public int getProgress() {
		return (int) (downloaded / (double) getActiveDownload().size * 100);
	}
	
	public void addDownload(Download d) {
		queue.add(d);
	}
	
	public Download getActiveDownload() {
		if (queue.size() == 0) return null;
		return queue.get(0);
	}
	
	public void cancelDownload() {
		cancel = true;
	}
	
	public void downloadFinished() {
		if (queue.size() > 0) queue.remove(0);
	}
}
