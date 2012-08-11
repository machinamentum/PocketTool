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

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TexturesView extends ListActivity {

	ArrayList<TexturePack> textures;
	APKManipulation apkm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_list);
		apkm = new APKManipulation(this);
		if (TexturesSkinsActivity.MODE_DOWNLOAD == 1) {
			textures = getDownloadedTextures();
		} else {
			//textures = getTexturePacks();
		}

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
		menu.setHeaderTitle("What would you like to do?");
		if (TexturesSkinsActivity.MODE_DOWNLOAD == 1) {
			menu.add("Install");
		} else {
			menu.add("Use");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Here's how you can get the correct item in onContextItemSelected()
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		if (item.getTitle().equals("Use")) {
			try {
				apkm.addTexture(new File(textures.get(info.position).path));
			} catch (IOException e) {

				e.printStackTrace();
			}
			Toast.makeText(getApplicationContext(),
					"Using " + textures.get(info.position).name,
					Toast.LENGTH_SHORT).show();
			return true;
		} else if (item.getTitle().equals("Install")) {
			String dname = textures.get(info.position).name;
			String fname = dname.substring(0, dname.length() - 4);
			File src = new File(textures.get(info.position).path);
			File dest = new File(APKManipulation.ptdir, "/Textures/" + fname);
			ZipTexturePack.unzip(src, dest);
			Toast.makeText(getApplicationContext(), "Installed" + fname,
					Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return super.onContextItemSelected(item);
		}
	}
}
