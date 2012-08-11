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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.snowbound.pockettool.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PatchActivity extends ListActivity  {
	ArrayList<PTPatch> patches;
	APKManipulation apkm;
	File undofold = new File(APKManipulation.ptdir, "/Undo/");
	File old = new File(APKManipulation.ptdir, "old.so");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_list);
		apkm = new APKManipulation(this);
		undofold.mkdirs();
		if(!old.exists()){
			File f = new File(APKManipulation.ptdir, "/temp/lib/armeabi-v7a/libminecraftpe.so");
			try {
				ZipUtils.copy(new FileInputStream(f), new FileOutputStream(old));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		patches = getPTPatches();
		ArrayAdapter<PTPatch> aa = new ArrayAdapter<PTPatch>(this,
				R.layout.list_item, patches) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {

					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = vi.inflate(R.layout.list_item, parent, false);
					convertView.setTag(parent);
				}

				TextView t;
				t = (TextView) convertView.findViewById(R.id.textView1);
				t.setText(patches.get(position).name);
				return convertView;

			}

		};
		ListView lv = getListView();
		lv.setAdapter(aa);
		registerForContextMenu(lv);
	}


	public ArrayList<PTPatch> getPTPatches() {
		ArrayList<PTPatch> array = new ArrayList<PTPatch>();
		File patchfold = new File(APKManipulation.ptdir, "/Patches/");
		patchfold.mkdirs();
		File[] files = patchfold.listFiles();
		int i;
		for (i = 0; i < files.length; i++) {
			File file = files[i];
			if (!file.isDirectory()) {
				String name = file.getAbsolutePath().substring(
						patchfold.getAbsolutePath().length() + 1);
				if(name.startsWith("._")){
					file.delete();
					continue;
				}
				String path = file.getAbsolutePath();
				PTPatch patch = new PTPatch(this, path);
				patch.name = name;
				array.add(patch);
				Log.d("t", "added patch");
			} else {

			}
		}
		return array;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// AdapterView.AdapterContextMenuInfo info =
		// (AdapterView.AdapterContextMenuInfo)menuInfo;
		menu.setHeaderTitle("What would you like to do?");
		menu.add("Patch of course!");
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		File undo = new File(undofold, patches.get(info.position).name);
		if(undo.exists()){
			menu.add("Undo");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Here's how you can get the correct item in onContextItemSelected()
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		if (item.getTitle().equals("Patch of course!")) {
			File undo = new File(undofold, patches.get(info.position).name);
			File f = new File(APKManipulation.ptdir, "/temp/lib/armeabi-v7a/libminecraftpe.so");
			try {
				patches.get(info.position).loadPatch();
				patches.get(info.position).checkMagic();
				patches.get(info.position).checkMinecraftVersion();
				patches.get(info.position).applyPatch(f);
				Toast.makeText(this, "Done patching", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!undo.exists()){
				File orig = new File(APKManipulation.ptdir, "/Textures/originals/lib/armeabi-v7a/libminecraftpe.so");
				try {
					PTPatch.diff(f.getAbsolutePath(), old.getAbsolutePath(), undo);
					old.delete();
					ZipUtils.copy(new FileInputStream(f), new FileOutputStream(old));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return true;
		} else if (item.getTitle().equals("Undo")) {
			File undo = new File(undofold, patches.get(info.position).name);
			File f = new File(APKManipulation.ptdir, "/temp/lib/armeabi-v7a/libminecraftpe.so");

			PTPatch undop = new PTPatch(this, undo.getAbsolutePath());
			try {
				undop.loadPatch();
				undop.checkMagic();
				undop.checkMinecraftVersion();
				undop.applyPatch(f);
				old.delete();
				ZipUtils.copy(new FileInputStream(f), new FileOutputStream(old));
				undo.delete();
				Toast.makeText(this, "Done patching", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		} else {
			return super.onContextItemSelected(item);
		}
	}
	
	
	/*
	public void onClick(View v) {
		if (v.getId() == R.id.patchbutton) {
			Toast.makeText(this, "Loading patch", Toast.LENGTH_SHORT);
			PTPatch patch = new PTPatch(this, "/mnt/sdcard/patch.mod");
			File f = new File(APKManipulation.ptdir, "/temp/lib/armeabi-v7a/libminecraftpe.so");
			try {
				patch.loadPatch();
				patch.checkMagic();
				patch.checkMinecraftVersion();;
				patch.applyPatch(f);
				Toast.makeText(this, "Done patching", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    //MenuInflater inflater = getMenuInflater();
	    menu.add("Apply Changes");
	    menu.add("Manual");
	    menu.add("Settings");
	    return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    if(item.getTitle().equals("Apply Changes")){
	    	try {
				apkm.update();
				Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return true;
	    }else if(item.getTitle().equals("Settings")){
	    	Intent i = new Intent(this, Settings.class);
	    	startActivity(i);
	    	return true;
	    }else if(item.getTitle().equals("Manual")){
	    	Intent i = new Intent(this, Manual.class);
	    	startActivity(i);
	    	return true;
	    }else{
	    	return super.onOptionsItemSelected(item);
	    }
		
	}
}
