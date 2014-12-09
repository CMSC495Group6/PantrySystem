/** FullItemAdapter class
 * 
 *  List adapter for handling arrays of FullItem objects.  Each item's
 *  information--its name, expiration date, and quantity--is displayed in a
 *  single row in the list view.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/01/2014
 *  Created ItemAdapter class and implemented all required functionality.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/03/2014
 *  Made items variable static.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/05/2014
 *  Changed items variable back to being non-static; added updateItems()
 *  method.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Renamed to FullItemAdapter to reflect the changes made to the Item class.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/09/2014
 *  Added proper date functionality using DateAssistentInterface, and added
 *  red highlighting for expired items.
 *  - Julian
 *  ***************************************************************************
 *  
 */

package com.example.pantrysystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FullItemAdapter extends BaseAdapter {
	protected ArrayList<FullItem> items;
	protected LayoutInflater mInflater;
	
	private DateAssistentInterface dateAssistent;
	
	public FullItemAdapter(Context context, ArrayList<FullItem> items) {
		this.items = items;
		mInflater = LayoutInflater.from(context);
		dateAssistent = DateAssistent.getInstance();
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public FullItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/** Replaces the current list of items with a new one and triggers a redraw */
	public void updateItems(ArrayList<FullItem> items) {
		this.items = items;
		notifyDataSetChanged();
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
		
		FullItem item = new FullItem();
		item.setName(items.get(position).getName());
		item.setExpiration_date(items.get(position).getExpiration_date());
		item.setQuantity(items.get(position).getQuantity());
		
		holder.name.setText(item.getName());
		holder.expiration_date.setText(dateAssistent.formatDate(item.getExpiration_date()));
		holder.quantity.setText(Integer.toString(item.getQuantity()));
		
		// Apply different colors based on item properties
		//TODO: define colors as styles and use those
		if (item.getQuantity() == 0) {
			// Darken lines with out-of-stock items
			convertView.setBackgroundColor(0x4F4F4F4F);
		} else if (item.getExpiration_date().before(dateAssistent.getCurrentDate())) {
			// Shade expired items red
			convertView.setBackgroundColor(0x7F4F0000);
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
