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

public class AboutSimpleNBT extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual);
		TextView tv = (TextView)findViewById(R.id.manualText);
		tv.setText(R.string.about_nbt_text);
	}
}
