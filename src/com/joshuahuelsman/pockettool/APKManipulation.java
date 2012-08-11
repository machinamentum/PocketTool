/*******************************************************************************
 * Copyright (c) 2012 Joshua Huelsman.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Joshua Huelsman - Initial implementation.
 *******************************************************************************/
package com.joshuahuelsman.pockettool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Environment;

public class APKManipulation {
	private static String MINECRAFT_APK_PATH;
	// private String MINECRAFT_DEMO_APK_PATH;
	//private int resume = 0;
	public static int minever;
	public static int USE_DEMO = 0;
	//private static ZipUtils zipu;
	//private Context context;
	public final static File ptdir = new File(Environment.getExternalStorageDirectory(),
			"/Android/data/com.joshuahuelsman.pockettool/");
	private static Activity main;
	public APKManipulation(Activity maina) {
		//zipu = new ZipUtils();
		main = maina;
	}
	
	public void uninstall() {
		Uri packageURI;
		if (USE_DEMO > 0) {
			packageURI = Uri.parse("package:com.mojang.minecraftpe.demo");
		} else {
			packageURI = Uri.parse("package:com.mojang.minecraftpe");
		}
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		main.startActivityForResult(uninstallIntent, 0);
	}

	public void install() throws IOException {
		
		File napk = new File(ptdir, "minecraft-cs.apk");
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(napk),
				"application/vnd.android.package-archive");
		main.startActivityForResult(intent, 1);
	}
	
	public void update() throws IOException{
		applyChanges();
		
	}
	
	public void addChar(File chrf) throws IOException {
		
		File tchrf = new File(ptdir, "/temp/assets/mob/char.png");
		tchrf.delete();
		InputStream is = new FileInputStream(chrf);
		byte[] bytes = new byte[(int) chrf.length()];
		is.read(bytes);
		is.close();

		OutputStream os = new FileOutputStream(tchrf);
		os.write(bytes);
		os.close();

	}
	
	public void addTexture(File text) throws IOException {
		File out = new File(ptdir, "/Textures/" + text.getName());
		File temp = new File(ptdir, "/temp");
		ZipUtils.copyFolder(out, temp); 
		
	}
	
	public static void refresh() throws IOException{
		if(checkForMinecraft() == 2){
			//Toast.makeText(main, "error", Toast.LENGTH_SHORT).show();
		}else{
			makeDefaultDirectories();
			int backup = checkForBackup();
			RefreshThread.CREATED_DEFAULTS = backup;
			checkForOriginals();
			
			
			File origfold = new File(ptdir, "Textures/originals/");
			File tempfold = new File(ptdir, "temp/");
			if(!tempfold.exists()){
				ZipUtils.copyFolder(origfold, tempfold);
			}
		}
		
	}

	public void applyChanges() {

		//File origfold = new File(ptfold, "Textures/originals/");
		File tempfold = new File(ptdir, "temp/");
		//File apk;
		File newapk = new File(ptdir, "minecraft-c.apk");
		File signapk = new File(ptdir, "minecraft-cs.apk");
		/*if (minever == 0) {
			apk = new File(ptfold, "minecraft.apk");
		} else {
			apk = new File(ptfold, "minecraft-demo.apk");
		}*/
		
		
		ZipThread zt = new ZipThread(this, signapk, tempfold, newapk);
		zt.start();
		
	}
	
	public static void makeDefaultDirectories() {
	
		File textures = new File(ptdir, "Textures/");
		File skins = new File(ptdir, "Skins/");
		textures.mkdirs();
		skins.mkdirs();
	}

	public static void makeOriginals() {
		File origfold = new File(ptdir, "Textures/originals/");
		File apk;
		//if (minever == 0) {
			apk = new File(ptdir, "minecraft.apk");
		//} else {
		//	apk = new File(ptfold, "minecraft-demo.apk");
		//}
		origfold.mkdir();
		ZipUtils.unzipArchive(apk, origfold);
	}

	public static int checkForOriginals() {
		if (hasOriginals() != true) {
			makeOriginals();
		} else {
		}
		return 0;
	}

	public static boolean hasOriginals() {
		File origfold = new File(ptdir, "/Textures/originals/");
		if (origfold.exists() != true) {
			return false;
		}

		return true;

	}

	public static int checkForBackup() throws IOException {
		if (!hasBackup()) {
			backup(MINECRAFT_APK_PATH);
			return 1;
		}

		return 0;
	}

	public static int checkForMinecraft() {
		if (hasMinecraft() != true) {
			if (hasMinecraftDemo() != true) {
				return 2;
			} else {
			}
		} else {
			return 1;
		}
		return 0;
	}

	public static boolean hasBackup() {
		File backup;
		//if (minever == 0) {
			backup = new File(ptdir, "minecraft.apk");
		/*} else {
			backup = new File(Environment.getExternalStorageDirectory()
					+ "/PocketTool/minecraft-demo.apk");
		}*/
		return backup.exists();
	}

	public static boolean hasMinecraft() {
		List<ApplicationInfo> applications = main.getApplication().getPackageManager()
				.getInstalledApplications(0);
		for (int n = 0; n < applications.size(); n++) {
			if ((applications.get(n).flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
				// This is System application
			} else {
				if (applications.get(n).publicSourceDir
						.contains("com.mojang.minecraftpe-")) {
					MINECRAFT_APK_PATH = applications.get(n).publicSourceDir;
					//minever = 0;
					return true;
				}
			}
		}
		return false;
	}

	public static boolean hasMinecraftDemo() {
		List<ApplicationInfo> applications = main.getApplicationContext().getPackageManager()
				.getInstalledApplications(0);
		for (int n = 0; n < applications.size(); n++) {
			if ((applications.get(n).flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
				//Log.d("APK", applications.get(n).publicSourceDir);
			} else {
				
				if (applications.get(n).publicSourceDir
						.contains("com.mojang.minecraftpe.demo")) {
					MINECRAFT_APK_PATH = applications.get(n).publicSourceDir;
					//minever = 1;
					USE_DEMO= 1;
					return true;
				}
				
			}
		}
		return false;
	}

	public static void copy(String input, String output) throws IOException {
		File inf = new File(input);
		File outf = new File(output);
		InputStream is = new FileInputStream(inf);
		OutputStream os = new FileOutputStream(outf);
		int length = (int) inf.length();
		byte[] data = new byte[length];
		is.read(data);
		is.close();
		os.write(data);
		os.close();
	}

	public static void backup(String apk) throws IOException {
		File napk = new File(ptdir, "minecraft.apk");
		copy(apk, napk.getAbsolutePath());
	}
}
