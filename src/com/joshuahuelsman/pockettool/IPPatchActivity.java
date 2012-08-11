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
		(byte) 0xFF, 0x50, 0x54, 0x50, (byte) 0x00, 0x01, 0x00,
		0x00, 0x00, 0x0A, 0x00, 0x1B,  (byte) 0x9B, (byte)0xBC, 
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
				Toast.makeText(this, "Done patching", Toast.LENGTH_SHORT).show();
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    //MenuInflater inflater = getMenuInflater();
	    menu.add("Apply Changes");
	    menu.add("Manual");
	    menu.add("Settings");
	    return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    if(item.getTitle().equals("Apply Changes")){
	    	try {
				apkm.update();
				Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return true;
	    }else if(item.getTitle().equals("Settings")){
	    	Intent i = new Intent(this, Settings.class);
	    	startActivity(i);
	    	return true;
	    }else if(item.getTitle().equals("Manual")){
	    	Intent i = new Intent(this, Manual.class);
	    	startActivity(i);
	    	return true;
	    }else{
	    	return super.onOptionsItemSelected(item);
	    }
		
	}
}
