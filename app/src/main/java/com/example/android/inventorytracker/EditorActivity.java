package com.example.android.inventorytracker;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.android.inventorytracker.data.InventoryContract;

import static com.example.android.inventorytracker.R.id.fab;

public class EditorActivity extends AppCompatActivity {
    private EditText mItemEditText;
    private EditText mQuantityEditText;
    private EditText mPriceEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mItemEditText = (EditText) findViewById(R.id.edit_item_name);
        mQuantityEditText = (EditText) findViewById(R.id.edit_quantity);
        mPriceEditText = (EditText) findViewById(R.id.edit_price);


    }

    private void saveItem() {
        String itemEditTextValue = mItemEditText.getText().toString();
        String quantityEditTextValue = mQuantityEditText.getText().toString();
        String priceEditTextValue = mPriceEditText.getText().toString();

        ContentValues values = new ContentValues();

        values.put(InventoryContract.InventoryEntry.COLUMN_ITEM_NAME, itemEditTextValue);
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, quantityEditTextValue);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, priceEditTextValue);
        Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.saveItem:
                saveItem();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
