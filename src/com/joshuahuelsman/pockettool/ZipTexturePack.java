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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipTexturePack {
	
	static File assets;
	static File font;
	static File gui;
	static File badge;
	static File item;
	static File mob;
	static File lang;
	static File art;
	static File environment;
	static File armor;
	
	
	static File res;
	static File drawable;
	static File drawable_hdpi;
	static File drawable_ldpi;
	static File drawable_mdpi;
	static File drawable_xhdpi;
	
	
	public static void unzip(File src, File dest){
		assets = new File(dest, "assets/");
		res = new File(dest, "res/");
		
		font = new File(assets, "font/");
		gui = new File(assets, "gui/");
		badge = new File(gui, "badge/");
		item = new File(assets, "item/");
		mob = new File(assets, "mob/");
		lang = new File(assets, "lang/");
		art = new File(assets, "art/");
		environment = new File(assets, "environment/");
		armor = new File(assets, "armor/");
		
		drawable = new File(res, "drawable/");
		drawable_hdpi = new File(res, "drawable-hdpi/");
		drawable_ldpi = new File(res, "drawable-ldpi/");
		drawable_mdpi = new File(res, "drawable-mdpi/");
		drawable_xhdpi = new File(res, "drawable-xhdpi/");
		
		assets.mkdirs();
		font.mkdirs();
		gui.mkdirs();
		badge.mkdirs();
		item.mkdirs();
		mob.mkdirs();
		lang.mkdirs();
		art.mkdirs();
		environment.mkdirs();
		armor.mkdirs();
		
		res.mkdirs();
		drawable.mkdirs();
		drawable_hdpi.mkdirs();
		drawable_ldpi.mkdirs();
		drawable_mdpi.mkdirs();
		drawable_xhdpi.mkdirs();
		
		try {
			ZipFile zipfile = new ZipFile(src);
			for (Enumeration<? extends ZipEntry> e = zipfile.entries(); e.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) e.nextElement();
				unzipEntry(zipfile, entry, dest);
			}
			zipfile.close();
		} catch (Exception e) {

		}
	}
	
	private static void unzipEntry(ZipFile zipfile, ZipEntry entry, File outputDir)
			throws IOException {
		
		
		
		if (entry.isDirectory() || entry.getName().contains("__MACOSX")) {
			return;
		}
		
		
		File outputFile;
		if(entry.getName().contains("terrain.png")){
			outputFile = new File(assets, "terrain.png");
		}else if(entry.getName().contains("particles.png")){
			outputFile = new File(assets, "particles.png");
		}else if(entry.getName().contains("terrain (2).png")){
			outputFile = new File(assets, "terrain (2).png");
		}
		
		else if(entry.getName().contains("default.png")){
			outputFile = new File(font, "default.png");
		}else if(entry.getName().contains("default8.png")){
			outputFile = new File(font, "default8.png");
		}
		//lang
		else if(entry.getName().contains("en_US.lang")){
			outputFile = new File(lang, "en_US.lang");
		}
		//GUI
		else if(entry.getName().contains("background.png")){
			outputFile = new File(gui, "background.png");
		}else if(entry.getName().contains("bg32.png")){
			outputFile = new File(gui, "bg32.png");
		}else if(entry.getName().contains("default_world.png")){
			outputFile = new File(gui, "default_world.png");
		}else if(entry.getName().contains("touchgui.png")){
			outputFile = new File(gui, "touchgui.png");
		}else if(entry.getName().contains("gui.png")){
			outputFile = new File(gui, "gui.png");
		}else if(entry.getName().contains("gui2.png")){
			outputFile = new File(gui, "gui2.png");
		}else if(entry.getName().contains("gui_blocks.png")){
			outputFile = new File(gui, "gui_blocks.png");
		}else if(entry.getName().contains("gui_blocks - Copy.png")){
			outputFile = new File(gui, "gui_blocks - Copy.png");
		}else if(entry.getName().contains("icons.png")){
			outputFile = new File(gui, "icons.png");
		}else if(entry.getName().contains("itemframe.png")){
			outputFile = new File(gui, "itemframe.png");
		}else if(entry.getName().contains("items.png")){
			outputFile = new File(gui, "items.png");
		}else if(entry.getName().contains("title.png")){
			outputFile = new File(gui, "title.png");
		}else if(entry.getName().contains("spritesheet.png")){
			outputFile = new File(gui, "spritesheet.png");
		}
		
		else if(entry.getName().contains("minecon140.png")){
			outputFile = new File(badge, "minecon140.png");
		}
		
		else if(entry.getName().contains("camera.png")){
			outputFile = new File(item, "camera.png");
		}else if(entry.getName().contains("arrows.png")){
			outputFile = new File(item, "arrows.png");
		}else if(entry.getName().contains("chest.png")){
			outputFile = new File(item, "chest.png");
		}else if(entry.getName().contains("sign.png")){
			outputFile = new File(item, "sign.png");
		}
		
		else if(entry.getName().contains("kz.png")){
			outputFile = new File(art, "kz.png");
		}
		
		else if(entry.getName().contains("char.png")){
			outputFile = new File(mob, "char.png");
		}else if(entry.getName().contains("chicken.png")){
			outputFile = new File(mob, "chicken.png");
		}else if(entry.getName().contains("cow.png")){
			outputFile = new File(mob, "cow.png");
		}else if(entry.getName().contains("creeper.png")){
			outputFile = new File(mob, "creeper.png");
		}else if(entry.getName().contains("pig.png")){
			outputFile = new File(mob, "pig.png");
		}else if(entry.getName().contains("pigzombie.png")){
			outputFile = new File(mob, "pigzombie.png");
		}else if(entry.getName().contains("sheep.png")){
			outputFile = new File(mob, "sheep.png");
		}else if(entry.getName().contains("sheep_fur.png")){
			outputFile = new File(mob, "sheep_fur.png");
		}else if(entry.getName().contains("skeleton.png")){
			outputFile = new File(mob, "skeleton.png");
		}else if(entry.getName().contains("spider.png")){
			outputFile = new File(mob, "spider.png");
		}else if(entry.getName().contains("zombie.png")){
			outputFile = new File(mob, "zombie.png");
		}

		else if (entry.getName().contains("chain_1.png")) {
			outputFile = new File(armor, "chain_1.png");
		} else if (entry.getName().contains("chain_2.png")) {
			outputFile = new File(armor, "chain_2.png");
		} else if (entry.getName().contains("cloth_1.png")) {
			outputFile = new File(armor, "cloth_1.png");
		} else if (entry.getName().contains("cloth_2.png")) {
			outputFile = new File(armor, "cloth_2.png");
		} else if (entry.getName().contains("diamond_1.png")) {
			outputFile = new File(armor, "diamond_1.png");
		} else if (entry.getName().contains("diamond_2.png")) {
			outputFile = new File(armor, "diamond_2.png");
		} else if (entry.getName().contains("gold_1.png")) {
			outputFile = new File(armor, "gold_1.png");
		} else if (entry.getName().contains("gold_2.png")) {
			outputFile = new File(armor, "gold_2.png");
		} else if (entry.getName().contains("iron_1.png")) {
			outputFile = new File(armor, "iron_1.png");
		} else if (entry.getName().contains("iron_2.png")) {
			outputFile = new File(armor, "iron_2.png");
		}

		else if (entry.getName().contains("clouds.png")) {
			outputFile = new File(environment, "clouds.png");
		} 
		
		else if(entry.getName().contains("drawable/bg32.png")){
			outputFile = new File(drawable, "bg32.png");
		}else if(entry.getName().contains("drawable/iconx.png")){
			outputFile = new File(drawable, "iconx.png");
		}else if(entry.getName().contains("drawable-hdpi/icon.png")){
			outputFile = new File(drawable_hdpi, "icon.png");
		}else if(entry.getName().contains("drawable-hdpi/icon_demo.png")){
			outputFile = new File(drawable_hdpi, "icon_demo.png");
		}else if(entry.getName().contains("drawable-ldpi/icon.png")){
			outputFile = new File(drawable_ldpi, "icon.png");
		}else if(entry.getName().contains("drawable-ldpi/icon_demo.png")){
			outputFile = new File(drawable_ldpi, "icon_demo.png");
		}else if(entry.getName().contains("drawable-mdpi/icon.png")){
			outputFile = new File(drawable_mdpi, "icon.png");
		}else if(entry.getName().contains("drawable-mdpi/icon_demo.png")){
			outputFile = new File(drawable_mdpi, "icon_demo.png");
		}else if(entry.getName().contains("drawable-xhdpi/icon.png")){
			outputFile = new File(drawable_xhdpi, "icon.png");
		}else if(entry.getName().contains("drawable-xhdpi/icon_demo.png")){
			outputFile = new File(drawable_xhdpi, "icon_demo.png");
		}else{
			return;
		}
		
		/*
		if(outputFile.exists()){
			outputFile.delete();
		}
		if (!outputFile.getParentFile().exists()) {
			ZipUtils.createDir(outputFile.getParentFile());
		}*/

		BufferedInputStream inputStream = new BufferedInputStream(
				zipfile.getInputStream(entry));
		FileOutputStream outputStream = 
				new FileOutputStream(outputFile);

		try {
			copy(inputStream, outputStream);
		} finally {
			
			outputStream.close();
			inputStream.close();
		}
	}
	
	public static void copy(InputStream in, OutputStream out)
			throws IOException {
		if (in == null) {

		}
		if (out == null) {

		}

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		//out.close();
	}
}
