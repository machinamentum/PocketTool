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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {


	public static void unzipArchive(File archive, File outputDir) {
		try {
			JarFile zipfile = new JarFile(archive);
			for (Enumeration<? extends JarEntry> e = zipfile.entries(); e.hasMoreElements();) {
				JarEntry entry = (JarEntry) e.nextElement();
				unzipEntry(zipfile, entry, outputDir);
			}
			zipfile.close();
		} catch (Exception e) {

		}
	}

	private static void unzipEntry(JarFile zipfile, JarEntry entry, File outputDir)
			throws IOException {

		if (entry.isDirectory()) {
			createDir(new File(outputDir, entry.getName()));
			return;
		}

		File outputFile = new File(outputDir, entry.getName());
		if(outputFile.exists()){
			outputFile.delete();
		}
		if (!outputFile.getParentFile().exists()) {
			createDir(outputFile.getParentFile());
		}

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

	static void createDir(File dir) {
		if (!dir.mkdirs())
			throw new RuntimeException("Can not create dir " + dir);
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
		//in.close();
		//out.close();
	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {
			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}

	public static void zip(File orig, File folder, ZipOutputStream zipFolder) throws IOException{
		File[] files = folder.listFiles();
		int i;
		for(i = 0; i < files.length; i++){
			File file = files[i];
			if(file.isDirectory()){
				zip(orig, file, zipFolder);
				continue;
			}
			String name = file.getAbsolutePath().substring(orig.getAbsolutePath().toString().length() +1);
			if(name.equals("") != true){
				ZipEntry entry = new ZipEntry(name);
				zipFolder.putNextEntry(entry);
				if(file.isDirectory() != true){
					copy(new FileInputStream(file), zipFolder);
				}
			}
			zipFolder.closeEntry();
			
		}
	}
	
	public static  boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}

}
