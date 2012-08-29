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
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ToolKit extends Activity implements OnClickListener {
	private List<PackageInfo> pinfo;
	private APKManipulation apkm;
	static ProgressDialog dialog;
	//private AdView adView;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toolkit);
		//pinfo = this.getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
		pinfo = this.getPackageManager().getInstalledPackages(0);
		try {
			checkForSettings();
			getSettings();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		RefreshThread rt = new RefreshThread(this);
		
		apkm = new APKManipulation(this);
		
		Button textskinbutton = (Button)findViewById(R.id.textskinbutton);
		
		
		Button patchmodbutton = (Button)findViewById(R.id.patchmodbutton);
		Button downloadmcskinbutton = (Button)findViewById(R.id.downloadmcnetskinbutton);
		Button ippatchbutton = (Button)findViewById(R.id.ippatchbutton);
		
		textskinbutton.setOnClickListener(this);
		patchmodbutton.setOnClickListener(this);
		
		ippatchbutton.setOnClickListener(this);
		downloadmcskinbutton.setOnClickListener(this);
		//Toast.makeText(this, "Minecraft Version Code: " + APKManipulation.minever, Toast.LENGTH_SHORT).show();
		 dialog = ProgressDialog.show(this, "", 
                this.getResources().getString(R.string.refreshing_resources), true);
		rt.start();
		//dialog.dismiss();
	}

	public void onClick(View v) {
		if(v.getId() == R.id.textskinbutton){
			Intent i = new Intent(this, LivePreview.class);
			startActivity(i);
		}else if(v.getId() == R.id.patchmodbutton){
			Intent i = new Intent(this, PatchActivity.class);
			startActivity(i);
		}else if(v.getId() == R.id.ippatchbutton){
			Intent i = new Intent(this, IPPatchActivity.class);
			startActivity(i);
		}else if(v.getId() == R.id.downloadmcnetskinbutton){
			Intent i = new Intent(this, DownloadMinecraftNetSkin.class);
			startActivity(i);
		}
		
	}
	
	public void checkForSettings() throws IOException{
		File settingf = new File(APKManipulation.ptdir, "settings.bin");
		if(!settingf.exists()){
			OutputStream os = new FileOutputStream(settingf);
			os.write(Settings.disableUninstall);
			
			int version = 0;
			//int hasSeenManual = 0;
			
			for(int i = 0; i < pinfo.size(); i++){
				if(pinfo.get(i).applicationInfo.publicSourceDir.contains("minecraftpe")){
					version = pinfo.get(i).versionCode;
					Log.d("PT", "v " + version);
				}
			}
			
			os.write(version);
			//os.write(hasSeenManual);
			os.close();
		} 
	}
	
	public void getSettings() throws IOException{
		File settingf = new File(APKManipulation.ptdir, "settings.bin");
		InputStream is = new FileInputStream(settingf);
		int disuninstall = is.read();
		int version = is.read();
		//int seenManual = is.read();
		Log.d("PT", "read: " + version);
		is.close();
		Settings.disableUninstall = disuninstall;
		APKManipulation.minever = version;
		
		for(int i = 0; i < pinfo.size(); i++){
			if(pinfo.get(i).applicationInfo.publicSourceDir.contains("minecraftpe")){
				if((byte)version != (byte)pinfo.get(i).versionCode){
					Log.d("PT", "Found new mcpe");
					File ptfold = APKManipulation.ptdir;
					File origfold = new File(ptfold, "Textures/originals/");
					File tempfold = new File(ptfold, "temp/");
					File backup = new File(ptfold, "minecraft.apk");
					
					ZipUtils.deleteDir(origfold);
					ZipUtils.deleteDir(tempfold);
					backup.delete();
					APKManipulation.minever = pinfo.get(i).versionCode;
					
					
				}
			}
		}
		Settings.saveSettings();
		
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		RefreshThread.CREATED_DEFAULTS = 0;
		APKManipulation.minever = 0;
		Settings.hasSeenManual = 1;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    //MenuInflater inflater = getMenuInflater();
	    menu.add(R.string.apply_changes);
	    menu.add(R.string.manual);
	    menu.add(R.string.settings);
	    return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    if(item.getTitle().equals(this.getResources().getString(R.string.apply_changes))){
	    	try {
				apkm.update();
				Toast.makeText(this, R.string.please_wait, Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return true;
	    }else if(item.getTitle().equals(this.getResources().getString(R.string.settings))){
	    	Intent i = new Intent(this, Settings.class);
	    	startActivity(i);
	    	return true;
	    }else if(item.getTitle().equals(this.getResources().getString(R.string.manual))){
	    	Intent i = new Intent(this, Manual.class);
	    	startActivity(i);
	    	return true;
	    }else{
	    	return super.onOptionsItemSelected(item);
	    }
		
	}
}
