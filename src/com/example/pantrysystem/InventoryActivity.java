package com.example.pantrysystem;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class InventoryActivity extends ActionBarActivity {
	private ListView listView;
	private ItemAdapter adapter;
	private Dialog dialog;
	private InventoryAccess inventoryAccess;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		
		// Initialize database access object.
		inventoryAccess = new InventoryAccessTestImpl();
		
		// Set up list view element
		listView = (ListView) findViewById(R.id.inventory_list);
		adapter = new ItemAdapter(this, inventoryAccess.getInventory());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new InventoryItemClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class InventoryItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// Instantiate item edit dialog
			dialog = new ItemContextDialog(InventoryActivity.this, (Item) listView.getItemAtPosition(position), inventoryAccess);
			// Display dialog
			dialog.show();
		}
	}
	
	class ItemContextDialog extends Dialog {
		private Item item;
		private InventoryAccess inventoryAccess;
		private Button addButton;
		private Button removeButton;
		private Button modifyButton;
		private Button deleteButton;
		private Button cancelButton;
		
		public ItemContextDialog(Context context, Item item, InventoryAccess inventoryAccess) {
			super(context);
			this.item = item;
			this.inventoryAccess = inventoryAccess;
			
			this.setContentView(R.layout.inventory_item_selected_dialog);
			this.setTitle(item.getName());
			// Set up button listeners
			addButton = (Button) this.findViewById(R.id.inventory_item_selected_add_button);
			addButton.setOnClickListener(new AddItemButtonListener());
			removeButton = (Button) this.findViewById(R.id.inventory_item_selected_remove_button);
			removeButton.setOnClickListener(new RemoveItemButtonListener());
			modifyButton = (Button) this.findViewById(R.id.inventory_item_selected_modify_button);
			modifyButton.setOnClickListener(new ModifyItemButtonListener());
			deleteButton = (Button) this.findViewById(R.id.inventory_item_selected_delete_button);
			deleteButton.setOnClickListener(new DeleteItemButtonListener());
			cancelButton = (Button) this.findViewById(R.id.inventory_item_selected_cancel_button);
			cancelButton.setOnClickListener(new CancelButtonListener());
		}

		/** Listener for the "Add" button. */
		class AddItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				inventoryAccess.addItem(item);
				adapter.notifyDataSetChanged();
				ItemContextDialog.this.dismiss();
			}
		}
		
		/** Listener for the "Remove" button. */
		class RemoveItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				inventoryAccess.removeItem(item);
				adapter.notifyDataSetChanged();
				ItemContextDialog.this.dismiss();
			}
		}
		
		/** Listener for the "Modify" button. */
		class ModifyItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				inventoryAccess.modifyItem(item);
				adapter.notifyDataSetChanged();
				ItemContextDialog.this.dismiss();
			}
		}
		
		/** Listener for the "Delete" button. */
		class DeleteItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				inventoryAccess.deleteItem(item);
				adapter.notifyDataSetChanged();
				ItemContextDialog.this.dismiss();
			}
		}
		
		/** Listener for the "Cancel" button. */
		class CancelButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				ItemContextDialog.this.dismiss();
			}
		}
	}
}
