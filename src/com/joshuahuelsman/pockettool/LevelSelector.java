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
import java.io.IOException;
import java.util.ArrayList;

import org.spout.nbt.CompoundTag;
import org.spout.nbt.stream.NBTInputStream;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LevelSelector extends ListActivity implements OnItemClickListener {
	ArrayList<World> list;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_list);
		list = getWorldList(true);
		ArrayAdapter<World> aa = new ArrayAdapter<World>(this,
				R.layout.list_item, list) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {

					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = vi.inflate(R.layout.list_item, parent, false);
					convertView.setTag(parent);
				}

				TextView t;
				t = (TextView) convertView.findViewById(R.id.textView1);
				t.setText(list.get(position).name);
				return convertView;

			}
		};
		
		ListView lv = getListView();
		lv.setAdapter(aa);
		lv.setOnItemClickListener(this);
	}
	
	
	private ArrayList<World> getWorldList(boolean filter){
		ArrayList<World> returnList = new ArrayList<World>();
		
		File worldfold = new File(Environment.getExternalStorageDirectory(), "/games/com.mojang/minecraftWorlds/");

		File[] files = worldfold.listFiles();
		int i;
		for (i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				String name = file.getAbsolutePath().substring(
						worldfold.getAbsolutePath().length() + 1);
				String path = file.getAbsolutePath();
				World mWorld = new World(name, path, 1);
				returnList.add(mWorld);
			} else {

			}
		}
		
		return returnList;
	}


	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		World mWorld = getWorld(list.get(position).path + "/level.dat");
		//NBTUtility.buildWorld(mWorld);
		mWorld.path = list.get(position).path;
		LevelEditor.setWorld(mWorld);
		
		
		Intent i = new Intent(this, LevelEditor.class);
		startActivity(i);
	}
	
	
	public World getWorld(String path)
	{
		FileInputStream fs;
		BufferedInputStream bs;
		NBTInputStream stream;
		//World world = new World("","",0);
		CompoundTag leveldata = null;
		//CompoundMap tags;
		try {
			fs = new FileInputStream(new File(path));
			bs = new BufferedInputStream(fs);
			bs.skip(8);
			stream = new NBTInputStream(fs, false, true);
			leveldata = (CompoundTag)stream.readTag();
			stream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//tags = leveldata.getValue();
		//Log.d("tag", leveldata.toString() + " : "  + leveldata.getName() );
		//world.name = ((StringTag)tags.get(World.LEVELNAME)).getValue();
		//world.seed = ((LongTag)tags.get(World.RANDOMSEED)).getValue();
		//world.type = ((IntTag)tags.get(World.GAMETYPE)).getValue();
		return new World(leveldata);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    //MenuInflater inflater = getMenuInflater();
	    menu.add(R.string.about_nbt);
	    return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    if(item.getTitle().equals(this.getResources().getString(R.string.about_nbt))){
	    	Intent i = new Intent(this, AboutSimpleNBT.class);
	    	startActivity(i);
	    	return true;
	    }else{
	    	return super.onOptionsItemSelected(item);
	    }
		
	}
}
