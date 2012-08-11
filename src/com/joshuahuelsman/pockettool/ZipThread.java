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
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;

import kellinwood.security.zipsigner.ZipSigner;

public class ZipThread extends Thread {
	File signapk;
	File tempfold;
	File newapk;
	APKManipulation apkm;
	
	public ZipThread(APKManipulation apkma, File s, File t, File n){
		signapk = s;
		tempfold = t;
		newapk = n;
		apkm = apkma;
	}
	
	@Override
	public void run(){
		try {
			//zipu.copyFolder(origfold, tempfold);
			
			//addChar(ptfold);
			ZipUtils.deleteDir(new File(tempfold, "META-INF/"));
			ZipOutputStream zipFolder = new ZipOutputStream(
					new FileOutputStream(newapk));
			ZipUtils.zip(tempfold, tempfold, zipFolder);
			zipFolder.close();
			ZipSigner zipSigner = new ZipSigner();
			// t.append("\n" + zipSigner.getKeymode());
			zipSigner.setKeymode("testkey");
			zipSigner.signZip(newapk.toString(), signapk.toString());
			apkm.install();
			if(Settings.disableUninstall != 1){
				apkm.uninstall();
			}
		} catch (Throwable t) {
		
			
		}
		
		
	}
}
