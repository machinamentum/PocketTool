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

import java.io.IOException;
import java.util.ArrayList;

import com.snowbound.pockettool.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class TexturesSkinsActivity extends TabActivity {
	/** Called when the activity is first created. */
	public static int MODE_DOWNLOAD = 0;
	ArrayList<TexturePack> textarray;
	APKManipulation apkm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		
		
		apkm = new APKManipulation(this);
		
		TabHost tabHost = getTabHost();
		//tabHost.setup();
		
		TabSpec spec1 = tabHost.newTabSpec("Tab 1");
		Intent intent = new Intent(this, SkinsView.class);
		spec1.setContent(intent);
		spec1.setIndicator("Skins");
		TabSpec spec2 = tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("Textures");
		intent = new Intent(this, TexturesView.class);
		spec2.setContent(intent);
		TabSpec spec3 = tabHost.newTabSpec("Tab 3");
		spec3.setIndicator("Tab 3");
		//spec3.setContent(R.id.tab3);
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		//tabHost.addTab(spec3);
		//tabHost.setCurrentTab(0);
		
		//GridView gridview = (GridView) findViewById(R.id.gridView1);
	    //gridview.setAdapter(new ImageAdapter(this, skinarray));
		//registerForContextMenu(gridview);

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