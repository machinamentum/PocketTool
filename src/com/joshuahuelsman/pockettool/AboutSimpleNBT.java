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
import android.os.Bundle;
import android.widget.TextView;

public class AboutSimpleNBT extends Activity {
	public static final String abouttext =
			"PocketTool uses the SimpleNBT (formally SpoutNBT) library developed by the SpoutDev team..\n" +
			"SimpleNBT is licensed under the SpoutDev License Version 1.\n" +
			"For the full licensing information for SimpleNBT please visit https://github.com/SpoutDev/SimpleNBT/blob/master/LICENSE.txt";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual);
		TextView tv = (TextView)findViewById(R.id.manualText);
		tv.setText(abouttext);
	}
}
