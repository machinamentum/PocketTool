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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScreen extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button installdownload = (Button)findViewById(R.id.installdownloadbutton);
		Button links = (Button)findViewById(R.id.linksbutton);
		Button toolkit = (Button)findViewById(R.id.toolkitbutton);
		Button leveleditor = (Button)findViewById(R.id.leveleditorbutton);
		installdownload.setOnClickListener(this);
		links.setOnClickListener(this);
		toolkit.setOnClickListener(this);
		leveleditor.setOnClickListener(this);
	}

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
