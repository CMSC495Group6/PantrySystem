/** List adapter for handling arrays of IngredientItem objects.
 *  Author: Julian
 *  CMSC 495
 *  Group 6
 *  12/07/2014
 *  **********
 *  Converts the IngredientItem objects in an array into a view that can be
 *  displayed by a List View.
 */
package com.example.pantrysystem;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IngredientItemAdapter extends BaseAdapter {
	protected ArrayList<IngredientItem> items;
	
	protected LayoutInflater mInflater;
	
	public IngredientItemAdapter(Context context, ArrayList<IngredientItem> items) {
		this.items = items;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public IngredientItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/** Replaces the current list of items with a new one and triggers a redraw */
	public void updateItems(ArrayList<IngredientItem> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_view_row_ingredient_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.ingredient_item_name);
			holder.quantity = (TextView) convertView.findViewById(R.id.ingredient_item_quantity);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		IngredientItem item = new IngredientItem();
		item.setName(items.get(position).getName());
		item.setQuantity(items.get(position).getQuantity());
		
		holder.name.setText(item.getName());
		holder.quantity.setText(Integer.toString(item.getQuantity()));
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView name;
		TextView quantity;
	}

}
