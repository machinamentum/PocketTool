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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DownloadMinecraftNetSkin extends Activity implements OnClickListener {
	EditText editText;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloadmcskin);
		
		Button downloadbutton = (Button)findViewById(R.id.downloadmcnetskin);
		downloadbutton.setOnClickListener(this);
		editText = (EditText)findViewById(R.id.editText1);
	}

	public void onClick(View v) {
		if(v.getId() == R.id.downloadmcnetskin){
			new Thread(new Runnable() {
				public void run() {
					try {
						URL murl = new URL("http://www.minecraft.net/skin/"
								+ editText.getText() + ".png");
						File out = new File(APKManipulation.ptdir, "Skins/" 
								+ editText.getText() + ".png");
				
						InputStream in = murl.openStream();
						OutputStream os = new FileOutputStream(out);
				
						ZipUtils.copy(in, os);
						in.close();
						os.close();
						DownloadMinecraftNetSkin.this.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(DownloadMinecraftNetSkin.this, DownloadMinecraftNetSkin.this.getResources().getString(R.string.skin_downloaded) + 
									" " + editText.getText() + ".png", Toast.LENGTH_SHORT).show();
							}
						});
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						DownloadMinecraftNetSkin.this.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(DownloadMinecraftNetSkin.this, R.string.skin_download_error, Toast.LENGTH_SHORT).show();
							}
						});
						e.printStackTrace();
					}
				}
			}).start();
			
		}
		
	}
}
