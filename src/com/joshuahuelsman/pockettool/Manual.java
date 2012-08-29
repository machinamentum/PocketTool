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
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual);
		TextView tv = (TextView)findViewById(R.id.manualText);
		tv.setText(this.getResources().getString(R.string.manual_text_part_1) + 
			this.getResources().getString(R.string.manual_text_part_2) + 
			this.getResources().getString(R.string.manual_text_part_3) + 
			this.getResources().getString(R.string.manual_text_part_4));
		
	}
}
