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

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.joshuahuelsman.pockettool.World.Player.Inventory.Slot;

public class InventoryEditor extends ListActivity implements OnItemClickListener {
	private static World mWorld;
	private static ArrayAdapter<Slot> slots;
	private World.Player.Inventory inventory;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		inventory = mWorld.player.inventory;
		slots = new ArrayAdapter<Slot>(this,R.layout.list_item,inventory.slots){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {

					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = vi.inflate(R.layout.list_item, parent, false);
					convertView.setTag(parent);
				}

				TextView t;
				t = (TextView) convertView.findViewById(R.id.textView1);
				t.setText("slotid: " + inventory.slots.get(position).slotid 
						+ "; itemid: " + inventory.slots.get(position).itemid
						+ "; count: " + inventory.slots.get(position).count
						+ "; damage: " + inventory.slots.get(position).damage);
				t.setTextSize(16);
				
				//convertView.setClickable(true);
				return convertView;

			}
		};
		
		ListView lv = getListView();
		lv.setOnItemClickListener(this);
		lv.setAdapter(slots);
	}
	
	@Override
	public void onRestart()
	{
		refreshList();
		super.onRestart();
	}

	public static World getWorld() {
		return mWorld;
	}

	public static void setWorld(World mWorld) {
		InventoryEditor.mWorld = mWorld;
	}
	
	public static void refreshList()
	{
		slots.notifyDataSetChanged();
	}



	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		SlotEditor.setSlot(inventory.slots.get(position));
		Intent i = new Intent(this,SlotEditor.class);
		startActivity(i);
		
	}
	
}
