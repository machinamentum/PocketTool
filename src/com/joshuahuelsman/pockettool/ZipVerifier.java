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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A simple verification process to avoid installing error -24 APK's.
 * @author josh
 *
 */
public class ZipVerifier {
	
	private static final String[] key_items = {
		"classes.dex", "assets/terrain.png", "lib/armeabi-v7a/libminecraftpe.so"
	};

	private static final HashMap<String, Boolean> map = new HashMap<String, Boolean>();
	
	public static boolean verify(ZipFile zipfile) {
		//This isnt completely necessary...
		if(!map.isEmpty()) {
			map.clear();
		}
		for(String s : key_items) {
			map.put(s, false);
		}
		for (Enumeration<? extends ZipEntry> e = zipfile.entries(); e.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) e.nextElement();
			if(map.containsKey(entry.getName())) {
				map.put(entry.getName(), true);
			}
			System.err.println(entry.getName());
		}
		boolean ret = allTrue(map);
		System.err.println(ret);
		return ret;
	}
	
	private static boolean allTrue(HashMap<? extends Object, Boolean> map) {
		for(Boolean b : map.values()) {
			if(b.equals(false)) {
				return false;
			}
		}
		return true;
	}
	

}
