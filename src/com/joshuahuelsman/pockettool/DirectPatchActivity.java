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

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DirectPatchActivity extends ListActivity {

	ArrayList<TexturePack> textures;
	APKManipulation apkm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_list_with_warning);
		apkm = new APKManipulation(this);
		textures = getDownloadedTextures();

		ArrayAdapter<TexturePack> aa = new ArrayAdapter<TexturePack>(this,
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
		ListView lv = getListView();
		lv.setAdapter(aa);
		registerForContextMenu(lv);
		TextView warningText = (TextView) findViewById(R.id.text_list_warning);
		warningText.setText(R.string.direct_patch_warning);
	}

	public ArrayList<TexturePack> getDownloadedTextures() {
		ArrayList<TexturePack> textarray = new ArrayList<TexturePack>();

		File textfold = new File(Environment.getExternalStorageDirectory(),
				"/download/");
		if (!textfold.exists()) {
			textfold = new File(Environment.getExternalStorageDirectory(),
					"/Download/");
		}
		File[] files = textfold.listFiles();
		int i;
		for (i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {

			} else {
				if (file.getName().contains(".zip")) {
					String name = file.getAbsolutePath().substring(
							textfold.getAbsolutePath().length() + 1);
					String path = file.getAbsolutePath();
					TexturePack text = new TexturePack(name, path, true);
					textarray.add(text);
				}
			}
		}
		return textarray;
	}

	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// AdapterView.AdapterContextMenuInfo info =
		// (AdapterView.AdapterContextMenuInfo)menuInfo;
		menu.setHeaderTitle(R.string.actions_prompt);
		menu.add(R.string.use_menuitem);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		if (item.getTitle().equals(this.getResources().getString(R.string.use_menuitem))) {
			ZipUtils.unzipArchive(new File(textures.get(info.position).path), 
					new File(APKManipulation.ptdir, "temp"));
			Toast.makeText(getApplicationContext(),
					this.getResources().getString(R.string.using_texture) + textures.get(info.position).name,
					Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
