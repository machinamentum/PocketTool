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
import java.nio.ByteBuffer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IPPatchActivity extends Activity implements OnClickListener {
	public static byte[] defaultip = {
		(byte) 0xFF, 0x50, 0x54, 0x50, 0x00, 0x01, 0x00,
		0x00, 0x00, 0x0A, 0x00, 0x1E,  (byte)0x9B, (byte)0x83, 
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
	};
	private EditText IPText;
	File patchesdir;
	APKManipulation apkm;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ippatch);
		Button ippatchbutton = (Button)findViewById(R.id.ippatcherbutton);
		IPText = (EditText)findViewById(R.id.ipinputtext);
		ippatchbutton.setOnClickListener(this);
		apkm = new APKManipulation(this);
		patchesdir = new File(APKManipulation.ptdir, "Patches/");
		patchesdir.mkdirs();
		
	} 

	public void onClick(View v) {
		if(v.getId() == R.id.ippatcherbutton){
			try {
				generateIPPatch(IPText.getText().toString());
				PTPatch patch = new PTPatch(this, patchesdir.getAbsolutePath() + "/" + IPText.getText().toString() + ".mod");
				File f = new File(APKManipulation.ptdir, "/temp/lib/armeabi-v7a/libminecraftpe.so");
				patch.loadPatch();
				patch.checkMagic();
				patch.checkMinecraftVersion();
				patch.applyPatch(f);
				Toast.makeText(this, R.string.done_patching, Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void generateIPPatch(String ip) throws IOException{
		File patch = new File(APKManipulation.ptdir, "Patches/" + ip + ".mod");
		if(!patch.exists()){
			OutputStream out = new FileOutputStream(patch);
			ByteBuffer buf = ByteBuffer.wrap(defaultip);
			buf.position(14);
			buf.put(ip.getBytes());
			out.write(buf.array());
			out.close();
		}
	}
	
	//Supposedly finds the default IP in libminecraftpe.so and 
	//patches the new IP at that address
	
	public static void dynamicIPPatch(String ip) throws IOException {
		File f = new File(APKManipulation.ptdir, "/temp/lib/armeabi-v7a/libminecraftpe.so");
		byte[] barray = new byte[(int) f.length()];
		InputStream is = new FileInputStream(f);
		is.read(barray);
		is.close();
		ByteBuffer buf = ByteBuffer.wrap(barray);
		int index = bruteIndexOf(buf.array(), "255.255.255.255".getBytes("ASCII-US"));
		buf.position(index);
		buf.put(ip.getBytes("ASCII-US"), 0, ip.length());
		f.delete();
		OutputStream os = new FileOutputStream(f);
		os.write(buf.array());
		os.close();
	}
	
	public static int bruteIndexOf(byte[] haystack, byte[] needle) {
		haystackCheck: for(int i = 0; i < haystack.length; i++) {
			if(haystack[i] == needle[0]) {
				needleCheck: for(int i2 = 1; i2 < needle.length; i2++) {
					if(haystack[i + i2] == needle[i2]) {
						continue needleCheck;
					}else{
						continue haystackCheck;
					}
				}
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    //MenuInflater inflater = getMenuInflater();
	    menu.add(R.string.apply_changes);
	    menu.add(R.string.manual);
	    MenuItem settings = menu.add(R.string.settings);
	    settings.setIcon(R.drawable.cog);
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
