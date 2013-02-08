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

import java.io.IOException;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class RefreshThread extends Thread {
	public static int CREATED_DEFAULTS = 0;
	private Activity act;
	public RefreshThread(Activity a){
		act = a;
	}
	
	@Override
	public void run(){
		try {
			APKManipulation.refresh();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(CREATED_DEFAULTS == 1){
			Log.d("PT", "Created Defaults");
		}
		handler.sendEmptyMessage(0);
	}
	private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            ToolKit.dialog.dismiss();
        }
    };
}
