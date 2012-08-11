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
import java.io.IOException;
import java.util.ArrayList;

import com.snowbound.pockettool.R;

import sge.engine.realism.SGEActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LivePreview extends SGEActivity {
	ArrayList<TexturePack> textures;
	ArrayList<Skin> skinarray;
	ArrayAdapter<TexturePack> ta;
	ArrayAdapter<Skin> ia;
	private APKManipulation apkm;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.live_preview);
		LinearLayout rl = (LinearLayout)findViewById(R.id.LinearLayout1);
		
		
		
		rl.addView(view);
		apkm = new APKManipulation(this);
		textures = getTexturePacks();
		skinarray = getSkinsList();
		ta = new ArrayAdapter<TexturePack>(this,
				R.layout.list_item, textures) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {

					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = vi.inflate(R.layout.list_item, parent, false);
					convertView.setTag(parent);
				}

				TextView t;
				t = (TextView) convertView.findViewById(R.id.textView1);
				t.setText(textures.get(position).name);
				return convertView;

			}

		};
		
		ia = new ArrayAdapter<Skin>(this,
				R.layout.image_view, skinarray) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {

					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = vi.inflate(R.layout.image_view, parent, false);
					convertView.setTag(parent);
				}
				ImageView imageView = (ImageView)convertView;
				Bitmap bMap = BitmapFactory.decodeFile(skinarray.get(position).path);
		        imageView.setImageBitmap(bMap);
				return convertView;

			}

		};
		
		
		ListView lv = (ListView)findViewById(R.id.listView1);
		ListView lv2 = (ListView)findViewById(R.id.listView2);
		lv.setAdapter(ia);
		lv2.setAdapter(ta);
		registerForContextMenu(lv);
		registerForContextMenu(lv2);
	}
	
	public ArrayList<TexturePack> getTexturePacks() {
		ArrayList<TexturePack> array = new ArrayList<TexturePack>();
		File textfold = new File(APKManipulation.ptdir, "/Textures/");

		File[] files = textfold.listFiles();
		int i; 
		for (i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				String name = file.getAbsolutePath().substring(
						textfold.getAbsolutePath().length() + 1);
				String path = file.getAbsolutePath();
				TexturePack text = new TexturePack(name, path, false);
				array.add(text);
			} else {

			}
		}
		return array;
	}
	
	public ArrayList<Skin> getSkinsList(){
		ArrayList<Skin> skinsarray = new ArrayList<Skin>();
		
		File skinfold = new File(APKManipulation.ptdir, "/Skins/");
		
		File[] files = skinfold.listFiles();
		int i;
		for(i = 0; i < files.length; i++){
			File file = files[i];
			if(file.isDirectory()){
				
			}else{
				String name = file.getAbsolutePath().substring(skinfold.getAbsolutePath().length() + 1);
				String path = file.getAbsolutePath();
				Skin skin = new Skin(name, path, false);
				skinsarray.add(skin);
			}
		}
		return skinsarray;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	    //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.setHeaderTitle("What would you like to do?");
	    if(v.getId() == R.id.listView1){
	    	menu.add("Use Skin");
	    	menu.add("Uninstall Skin");
	    }else if(v.getId() == R.id.listView2){
	    	menu.add("Use Texture");
	    }
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    // Here's how you can get the correct item in onContextItemSelected()
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    if(item.getTitle().equals("Use Skin")){
	    	try {
				apkm.addChar(new File(skinarray.get(info.position).path));
				this.useNewSkin();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Toast.makeText(getApplicationContext(),"Using " +skinarray.get(info.position).name, Toast.LENGTH_SHORT).show();
	    	return true;
	    }else if(item.getTitle().equals("Uninstall Skin")){
	    	File skin = new File(skinarray.get(info.position).path);
	    	skin.delete();
	    	skinarray.remove(info.position);
	    	ia.notifyDataSetChanged();
	    	return true;
	    }else if(item.getTitle().equals("Use Texture")){
	    	try {
				apkm.addTexture(new File(textures.get(info.position).path));
				this.useNewSkin();
			} catch (IOException e) {

				e.printStackTrace();
			}
			Toast.makeText(getApplicationContext(),
					"Using " + textures.get(info.position).name,
					Toast.LENGTH_SHORT).show();
			return true;
	    }else{
	    
	    	return super.onContextItemSelected(item);
	    }
	}
	
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
