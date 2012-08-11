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
import java.util.ArrayList;

import com.snowbound.pockettool.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.GridView;
import android.widget.Toast;

public class SkinsView extends Activity {
	ArrayList<Skin> skinarray;
	APKManipulation apkm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skinsgrid);
		apkm= new APKManipulation(this);
		if(TexturesSkinsActivity.MODE_DOWNLOAD == 1){
			skinarray = getDownloadedSkins();
		}else{
			//skinarray = getSkinsList();
		}
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this, skinarray));
	    registerForContextMenu(gridview);
	    
	}
	
	
	
	public ArrayList<Skin> getDownloadedSkins(){
		ArrayList<Skin> skinsarray = new ArrayList<Skin>();
		
		File skinfold = new File(Environment.getExternalStorageDirectory(),"/download/");
		if(!skinfold.exists()){
			skinfold = new File(Environment.getExternalStorageDirectory(), "/Download/");
			if(!skinfold.exists()){
				skinfold = new File(Environment.getExternalStorageDirectory(), "/downloads/");
				if(!skinfold.exists()){
					skinfold = new File(Environment.getExternalStorageDirectory(), "/Downloads/");
				}
			}
		}
		File[] files = skinfold.listFiles();
		int i;
		for(i = 0; i < files.length; i++){
			File file = files[i];
			if(file.isDirectory()){
				
			}else{
					if(file.getName().contains(".png")){
					String name = file.getAbsolutePath().substring(skinfold.getAbsolutePath().length() + 1);
					String path = file.getAbsolutePath();
					Skin skin = new Skin(name, path, true);
					skinsarray.add(skin);
				}
			}
		}
		return skinsarray;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	    //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.setHeaderTitle("What would you like to do?");
	    
	    if(TexturesSkinsActivity.MODE_DOWNLOAD == 1){
	    	menu.add("Install");
	    }else{
	    	menu.add("Use");
	    }
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    // Here's how you can get the correct item in onContextItemSelected()
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    if(item.getTitle().equals("Use")){
	    	try {
				apkm.addChar(new File(skinarray.get(info.position).path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(getApplicationContext(),"Using " +skinarray.get(info.position).name, Toast.LENGTH_SHORT).show();
	    	return true;
	    }else if(item.getTitle().equals("Install")){
	    	File skin = new File(skinarray.get(info.position).path);
	    	File skinsf = new File(APKManipulation.ptdir, "/Skins/");
	    	File dest = new File(skinsf, skinarray.get(info.position).name);
	    	try {
				InputStream in = new FileInputStream(skin);
				OutputStream os = new FileOutputStream(dest);
				ZipUtils.copy(in, os);
				in.close();
		    	os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	return true;
	    }else{
	    	return super.onContextItemSelected(item);
	    }
	}
	
	
}
