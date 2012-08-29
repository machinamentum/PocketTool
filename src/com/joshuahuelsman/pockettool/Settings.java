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
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class Settings extends Activity {
	public static int disableUninstall;
	public static int hasSeenManual;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		CheckBox cb = (CheckBox)findViewById(R.id.checkBox1);
		cb.setText(R.string.disable_uninstall_checkbox_text);
		if(disableUninstall == 1){
			cb.setChecked(true);
		}else{
			cb.setChecked(false);
		}
		
	}
	
	public void onCheckboxClicked(View v) {
	    // Perform action on clicks, depending on whether it's now checked
	    if (((CheckBox) v).isChecked()) {
	    	disableUninstall = 1;
	        Toast.makeText(this, R.string.disabling_uninstall, Toast.LENGTH_SHORT).show();
	    } else {
	    	disableUninstall = 0;
	        Toast.makeText(this, R.string.enabling_uninstall, Toast.LENGTH_SHORT).show();
	    }
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		try {
			saveSettings();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void  saveSettings() throws IOException{
		File settingf = new File(APKManipulation.ptdir, "settings.bin");
		OutputStream os = new FileOutputStream(settingf);
		os.write(Settings.disableUninstall);
		os.write(APKManipulation.minever);
		//os.write(Settings.hasSeenManual);
		os.close();
	}
}
