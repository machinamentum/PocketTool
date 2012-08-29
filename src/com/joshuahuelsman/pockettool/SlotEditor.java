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
import android.widget.EditText;
import android.widget.TextView;

import com.joshuahuelsman.pockettool.World.Player.Inventory.Slot;

public class SlotEditor extends Activity {
	private static Slot mSlot;
	private EditText damage;
	private EditText count;
	private EditText itemid;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slot_editor);
		TextView slotID = (TextView)findViewById(R.id.slotidText);
		itemid = (EditText)findViewById(R.id.itemidEdit);
		count = (EditText)findViewById(R.id.countEdit);
		damage = (EditText)findViewById(R.id.damageEdit);
		
		slotID.setText(this.getResources().getString(R.string.item_slotid) + ": " + mSlot.slotid);
		itemid.setText("" + mSlot.itemid);
		count.setText("" + mSlot.count);
		damage.setText("" + mSlot.damage);
		
	}
	
	@Override
	public void onDestroy()
	{
		mSlot.itemid = Short.parseShort(itemid.getText().toString());
		mSlot.count = Byte.parseByte(count.getText().toString());
		mSlot.damage = Short.parseShort(damage.getText().toString());
		InventoryEditor.refreshList();
		super.onDestroy();
	}
	
	public static Slot getSlot() {
		return mSlot;
	}
	public static void setSlot(Slot slot) {
		mSlot = slot;
	}
}
