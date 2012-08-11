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

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Manual extends Activity {
	private final String manualtext =
			"Hello, thank you for using PocketTool!\n" +
			"To begin, please download  your favorite Minecraft PE " +
			"skin/texture pack using your device's internet browser or " +
			"using a computer. If you use a computer, please transfer " +
			"your skin/texture pack to the \"download\" folder of your sdcard.\n\n" +
			
			"You must have Minecraft PE or Minecraft PE Demo installed in order for PocketTool to run correctly.\n" +
			"Launch PocketTool.\n" +
			"Install Downloaded Skins/Texture Packs:\n" +
			"Click \"Install Downloaded Content\"\n" +
			"You may install a skin by long pressing the skin you wish to install and click \"Install\"\n" +
			"You may install a texture pack by long pressing the texture pack name you wish to install and click \"Install\"\n" +
			"Return to the MainScreen (hit Back button)\n\n" +


			"Use Installed Skins/Texture Packs:\n" +
			"Click \"Change Textures/Skins\"\n" +
			"You may install a skin by long pressing the skin you wish to install and click \"Use\"\n" +
			"You may install a texture pack by long pressing the texture pack name you wish to install and click \"Use\"\n\n" +

			"Install:\n" +
			"Press your device's \"Menu\" button/key then click \"Apply Changes\"\n" +
			"Confirm the uninstall. Confirm the install.\n" +
			"Launch Minecraft PE (or Demo) and enjoy your new skin and texture pack!";
			
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual);
		TextView tv = (TextView)findViewById(R.id.manualText);
		tv.setText(manualtext);
		
	}
}
