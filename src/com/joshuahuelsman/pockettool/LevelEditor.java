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

import com.snowbound.pockettool.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class LevelEditor extends Activity {
	private static World mWorld;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_editor);
		TextView NameText = (TextView)findViewById(R.id.LevelNameTextView);
		TextView GameTypeText = (TextView)findViewById(R.id.LevelGameTypeTextView);
		TextView LastPlayedText = (TextView)findViewById(R.id.LevelLastPlayedTextView);
		TextView PlatformText = (TextView)findViewById(R.id.LevelPlatformTextView);
		TextView SeedText = (TextView)findViewById(R.id.LevelSeedTextView);
		TextView SizeOnDiskText = (TextView)findViewById(R.id.LevelSizeOnDiskTextView);
		
		GameTypeText.setText("GameType: " + mWorld.gameType);
		LastPlayedText.setText("LastPlayed: " + mWorld.lastPlayed);
		PlatformText.setText("Platform: " + mWorld.platform);
		NameText.setText("LevelName: " + mWorld.levelName);
		SeedText.setText("RandomSeed: " + mWorld.randomSeed);
		SizeOnDiskText.setText("SizeOnDisk: " + mWorld.sizeOnDisk);
		
		
		EditText SpawnXEdit = (EditText)findViewById(R.id.LevelSpawnXEdit);
		EditText SpawnYEdit = (EditText)findViewById(R.id.LevelSpawnYEdit);
		EditText SpawnZEdit = (EditText)findViewById(R.id.LevelSpawnZEdit);
		
		SpawnXEdit.setText("" + mWorld.spawnX);
		SpawnYEdit.setText("" + mWorld.spawnY);
		SpawnZEdit.setText("" + mWorld.spawnZ);
	}
	
	@Override
	public void onDestroy(){
		mWorld.spawnX = Integer.parseInt(((EditText)findViewById(R.id.LevelSpawnXEdit)).getText().toString());
		mWorld.spawnY = Integer.parseInt(((EditText)findViewById(R.id.LevelSpawnYEdit)).getText().toString());
		mWorld.spawnZ = Integer.parseInt(((EditText)findViewById(R.id.LevelSpawnZEdit)).getText().toString());
		mWorld.save();
		mWorld = null;
		super.onDestroy();
	}
	
	public static World getWorld(){
		return mWorld;
	}
	
	public static void setWorld(World world){
		mWorld = world;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    //MenuInflater inflater = getMenuInflater();
	    menu.add("About");
	    return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    if(item.getTitle().equals("About")){
	    	Intent i = new Intent(this, AboutSimpleNBT.class);
	    	startActivity(i);
	    	return true;
	    }else{
	    	return super.onOptionsItemSelected(item);
	    }
		
	}
}
