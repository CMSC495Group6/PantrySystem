package com.example.pantrysystem;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** List adapter for handling arrays of Item objects. */
public class ItemAdapter extends BaseAdapter {
	private static ArrayList<Item> items;
	
	private LayoutInflater mInflater;
	
	public ItemAdapter(Context context, ArrayList<Item> items) {
		this.items = items;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Item getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.inventory_list_row_view, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.item_name);
			holder.expiration_date = (TextView) convertView.findViewById(R.id.item_expiration_date);
			holder.quantity = (TextView) convertView.findViewById(R.id.item_quantity);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Item item = new Item();
		item.setName(items.get(position).getName());
		item.setExpiration_date(items.get(position).getExpiration_date());
		item.setQuantity(items.get(position).getQuantity());
		
		holder.name.setText(item.getName());
		holder.expiration_date.setText(item.getExpiration_date().toString());
		holder.quantity.setText(Integer.toString(item.getQuantity()));
		
		// Apply different colors based on item properties
		//TODO: define colors as styles and use those
		if (item.getQuantity() == 0) {
			// Quantity is 0
			convertView.setBackgroundColor(0x7F4F4F4F);
		} else {
			// Otherwise use default color
			convertView.setBackgroundColor(0x00000000);
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView name;
		TextView expiration_date;
		TextView quantity;
	}

}
