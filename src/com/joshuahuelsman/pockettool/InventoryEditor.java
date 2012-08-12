package com.joshuahuelsman.pockettool;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.joshuahuelsman.pockettool.World.Player.Inventory.Slot;

public class InventoryEditor extends ListActivity implements OnItemClickListener {
	private static World mWorld;
	private World.Player.Inventory inventory;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		inventory = mWorld.player.inventory;
		ArrayAdapter<Slot> slots = new ArrayAdapter<Slot>(this,R.layout.list_item,inventory.slots){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {

					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = vi.inflate(R.layout.list_item, parent, false);
					convertView.setTag(parent);
				}

				TextView t;
				t = (TextView) convertView.findViewById(R.id.textView1);
				t.setText("slotid: " + inventory.slots.get(position).slotid);
				
				convertView.setClickable(true);
				return convertView;

			}
		};
		
		ListView lv = getListView();
		lv.setOnItemClickListener(this);
		lv.setAdapter(slots);
	}
	
	

	public static World getWorld() {
		return mWorld;
	}

	public static void setWorld(World mWorld) {
		InventoryEditor.mWorld = mWorld;
	}
	
	public void refreshList()
	{
		this.getListView().invalidate();
	}



	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
	}
	
}
