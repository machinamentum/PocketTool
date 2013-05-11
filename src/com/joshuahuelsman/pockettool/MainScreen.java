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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import eu.chainfire.libsuperuser.Shell;

public class MainScreen extends Activity implements OnClickListener {
	public ProgressDialog dialog;
	boolean root = false;
	private Button toolkit;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button installdownload = (Button)findViewById(R.id.installdownloadbutton);
		Button links = (Button)findViewById(R.id.linksbutton);
		toolkit = (Button)findViewById(R.id.toolkitbutton);
		Button leveleditor = (Button)findViewById(R.id.leveleditorbutton);
		installdownload.setOnClickListener(this);
		links.setOnClickListener(this);
		toolkit.setOnClickListener(this);
		leveleditor.setOnClickListener(this);
		
		if (android.os.Build.VERSION.SDK_INT >= 16) {
			dialog = ProgressDialog.show(this, "", 
	                this.getResources().getString(R.string.find_root), true);
			
			Thread checkRoot = new Thread(new Runnable() {
				
				public void run() {
					root = Shell.SU.available();
					handler.sendEmptyMessage(0);
				}
				
			});
			checkRoot.start();
			
		}
	}

	private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            dialog.dismiss();
            if(!root) {
				toolkit.setEnabled(false);
				Toast.makeText(MainScreen.this, R.string.root_not_available, Toast.LENGTH_LONG).show();
			}
        }
    };

	public void onClick(View v) {
		if(v.getId() == R.id.installdownloadbutton){
			TexturesSkinsActivity.MODE_DOWNLOAD = 1;
			Intent i = new Intent(this, TexturesSkinsActivity.class);
			startActivity(i);
		}else if(v.getId() == R.id.linksbutton){
			Intent i = new Intent(this, Links.class);
			startActivity(i);
		}else if(v.getId() == R.id.toolkitbutton){
			Intent i = new Intent(this, ToolKit.class);
			startActivity(i);
		}else if(v.getId() == R.id.leveleditorbutton){
			Intent i = new Intent(this, LevelSelector.class);
			startActivity(i);
		}
		
	}
}
