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

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private ArrayList<Skin> array;
	private Context mContext;
	public ImageAdapter(Context c, ArrayList<Skin> list){
		mContext = c;
		array = list;
	}
	
	
	public int getCount() {
		if(array != null){
			return array.size();
		}
		return 0;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(0, 0, 0, 0);
            
        } else {
            imageView = (ImageView) convertView;
        }
        Bitmap bMap = BitmapFactory.decodeFile(array.get(position).path);
        imageView.setImageBitmap(bMap);
		return imageView;
	}

}
