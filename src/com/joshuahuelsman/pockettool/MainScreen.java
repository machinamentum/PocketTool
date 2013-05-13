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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScreen extends Activity implements OnClickListener {
	public ProgressDialog dialog;
	boolean root = false;
	private Button toolkit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button installdownload = (Button) findViewById(R.id.installdownloadbutton);
		Button links = (Button) findViewById(R.id.linksbutton);
		toolkit = (Button) findViewById(R.id.toolkitbutton);
		Button leveleditor = (Button) findViewById(R.id.leveleditorbutton);
		installdownload.setOnClickListener(this);
		links.setOnClickListener(this);
		toolkit.setOnClickListener(this);
		leveleditor.setOnClickListener(this);

		if (android.os.Build.VERSION.SDK_INT >= 16) {
			File jelly = new File(APKManipulation.ptdir, "jellybean");
			if (!jelly.exists()) {
				try {
					File parent = jelly.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					jelly.createNewFile();
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setPositiveButton(R.string.goto_settings,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Intent intent = new Intent(MainScreen.this,
											Settings.class);
									startActivity(intent);
								}
							});
					builder.setNegativeButton(R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});
					builder.setMessage(R.string.jellybean_warning);
					AlertDialog dialog = builder.create();
					dialog.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void onClick(View v) {
		if (v.getId() == R.id.installdownloadbutton) {
			TexturesSkinsActivity.MODE_DOWNLOAD = 1;
			Intent i = new Intent(this, TexturesSkinsActivity.class);
			startActivity(i);
		} else if (v.getId() == R.id.linksbutton) {
			Intent i = new Intent(this, Links.class);
			startActivity(i);
		} else if (v.getId() == R.id.toolkitbutton) {
			Intent i = new Intent(this, ToolKit.class);
			startActivity(i);
		} else if (v.getId() == R.id.leveleditorbutton) {
			Intent i = new Intent(this, LevelSelector.class);
			startActivity(i);
		}

	}
}
